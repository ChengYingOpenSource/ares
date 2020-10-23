package com.cy.ares.spcp.client.network.netty;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import com.cy.ares.spcp.client.NodeInfo;
import com.cy.ares.spcp.client.PoolManager;
import com.cy.ares.spcp.client.Receiver;
import com.cy.ares.spcp.client.errors.PoolException;
import com.cy.ares.spcp.context.SpcpConfig;

import io.netty.channel.Channel;

/**
 * 先释放，再 newClient;
 *
 * @author maoxq
 * @version V1.0
 * @Description
 * @date 2019年5月5日 上午10:56:50
 */
public class NettyPoolManager implements PoolManager {

    private static final Logger logger = LoggerFactory.getLogger(NettyPoolManager.class);

    private Map<NodeInfo, NettyClient> clientPoolMap = new ConcurrentHashMap<>();

    // 当前只能连一个server node;
    private NodeInfo currentNode;
    private int index;
    private volatile int failedCount;

    private SpcpConfig conf;
    private volatile boolean init = false;
    private volatile boolean close = false;

    private Receiver receiver;

    public NettyPoolManager(SpcpConfig conf, Receiver receiver) {
        this.receiver = receiver;
        this.conf = conf;
    }

    @Override
    public synchronized void init(){
        if(init){
            return ;
        }
        refreshClient();
        init = true;
    }

    /**
     * 由外部指定 连接的server node;
     *
     * @param node
     * @return
     */
    @Override
    public synchronized boolean refreshClient(NodeInfo node) {
        if (close) {
            return false;
        }
        releaseClient();
        NettyClient nt = new NettyClient(conf, receiver);
        try {
            nt.init(node);
            clientPoolMap.put(node, nt);
            currentNode = node;
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            if (nt != null) {
                nt.close();
            }
        }
    }

    /**
     * 内部重新指定下一个连接的 server node
     *
     * @return
     */
    @Override
    public synchronized boolean refreshClient() {
        if (close) {
            return false;
        }
        List<NodeInfo> serverConfNode = this.conf.getConfNodes();
        int size = serverConfNode.size();
        int tryCount = this.conf.getMaxPoolTry();
        boolean newSuccess = false;
        failedCount = 0;
        while (!newSuccess) {
            releaseClient();
            newSuccess = newClient();
            if (newSuccess) {
                break;
            } else {
                failedCount++;
                newSuccess = false;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    logger.warn("Thread Interrupted!", e);
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
                if (failedCount >= size || failedCount >= tryCount) {
                    logger.warn("newClient failedCount={} > tryCount={}", failedCount, tryCount);
                    break;
                }
            }
        }
        if (!newSuccess && (failedCount >= size || failedCount >= tryCount)) {
            throw new PoolException(String.format("pool init failed,count=%d", failedCount));
        }
        logger.info("newClient success!");
        boolean cf = checkClient();
        logger.info("checkClient ={}!", cf);
        return cf;
    }

    private boolean checkClient() {
        NettyClient nt = clientPoolMap.get(currentNode);
        if (nt == null) {
            return false;
        }
        Channel ch = nt.getCh();
        if (ch == null) {
            return false;
        }
        nt.release(ch);
        // return ch.isActive(); 不一定立马active了
        logger.info("check channel ,active={}", ch.isActive());
        return true;
    }

    @Override
    public synchronized boolean newClient() {
        switchNode();
        NettyClient nt = clientPoolMap.get(currentNode);
        if (nt != null) {
            return true;
        }
        nt = new NettyClient(conf, receiver);
        String currentNodeInfo = JSON.toJSONString(currentNode);
        boolean f = false;
        try {
            nt.init(currentNode);
            // 尝试获取active channel
            clientPoolMap.put(currentNode, nt);
            logger.info("newClient created!,currentNode={}", currentNodeInfo);
            f = checkClient();
            return f;
        } catch (Exception e) {
            logger.error("newClient()" + e.getMessage(), e);
            logger.error("newClient failed!,currentNode={}", currentNodeInfo);
            f = false;
            return false;
        } finally {
            if (nt != null && !f) {
                nt.close();
                logger.info("newClient closed!,currentNode={}", currentNodeInfo);
            }
        }
    }

    @Override
    public synchronized void releaseClient() {
        if (currentNode == null) {
            return;
        }
        NettyClient nt = clientPoolMap.get(currentNode);
        if (nt != null) {
            clientPoolMap.remove(currentNode);
            nt.close();
        }
    }

    @Override
    public Channel channel() {
        if (close) {
            logger.info("pool manager has closed!");
            return null;
        }
        NettyClient nt = clientPoolMap.get(currentNode);
        if (nt == null) {
            return null;
        }
        return nt.getCh();
    }

    @Override
    public void releaseChannel(Channel ch) {
        NettyClient nt = clientPoolMap.get(currentNode);
        if (nt == null) {
            return;
        }
        nt.release(ch);
    }

    @Override
    public synchronized void close() {
        if (close) {
            logger.info("pool manager has closed!");
            return;
        }
        close = true;
        logger.info("netty pool manager to close!currentNode={}", JSON.toJSONString(currentNode));
        if (clientPoolMap.isEmpty()) {
            return;
        }
        for (Entry<NodeInfo, NettyClient> en : clientPoolMap.entrySet()) {
            en.getValue().close();
        }
    }

    @Override
    public boolean isClose() {
        return close;
    }

    /**
     * 切换node
     */
    private void switchNode() {
        if (currentNode == null) {
            currentNode = chooseNodeRandom();
            return;
        }
        currentNode = nextNode();
    }

    private NodeInfo nextNode() {
        List<NodeInfo> serverConfNode = this.conf.getConfNodes();
        int size = serverConfNode.size();
        int in = (index + 1) % size;
        this.index = in;
        return serverConfNode.get(in);
    }

    /**
     * 随机选择node
     *
     * @return
     */
    private NodeInfo chooseNodeRandom() {
        List<NodeInfo> serverConfNode = this.conf.getConfNodes();
        int size = serverConfNode.size();
        int i = Math.abs(ThreadLocalRandom.current().nextInt(size));
        this.index = i;
        return serverConfNode.get(i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(JSON.toJSONString(currentNode));
        sb.append(String.format("pool size=%d", this.clientPoolMap.size()));
        return sb.toString();

    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

}
