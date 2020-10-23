package com.cy.onepush.dcommon.raft.election;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.onepush.dcommon.raft.NetNode;
import com.cy.onepush.dcommon.raft.RaftContainer;
import com.cy.onepush.dcommon.raft.message.AppendEntry;

/**
 * heart send heart check heart listener
 *
 * @author maoxq
 * @version V1.0
 * @Description
 * @date 2018年5月26日 下午4:46:49
 */
public class HeartbeatExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatExecutor.class);

    // 心跳间隔
    private long interval;

    // 上一次接受心跳的时间
    private long lastHeartbeat;
    private long heartbeatTimeout = 10000;

    private volatile boolean isSendStop = true;
    private Object sendObj = new Object();

    private volatile boolean heartCheck = false;
    private Object hbtCheckObj = new Object();

    private RaftContainer raftContainer;

    private HbtSender hbtSender;
    private HbtCheck hbtCheck;

    public HeartbeatExecutor(RaftContainer container) {
        this.raftContainer = container;
        this.interval = container.getConfig().getHeartbeatIntervalMs();
        if (this.interval <= 1000) {
            this.interval = 1000;
        }

        hbtSender = new HbtSender();
        hbtSender.start();
        hbtCheck = new HbtCheck();
        hbtCheck.start();
    }

    /**
     * 发送心跳
     */
    public void toHeart() {
        isSendStop = false;

        synchronized (this) {
            sendObj.notifyAll();
        }
    }

    public void stopHeart() {
        isSendStop = true;
    }

    public void startHeartCheck() {

        heartCheck = true;
        synchronized (this) {
            hbtCheckObj.notifyAll();
        }
    }

    public void stopHeartCheck() {

        heartCheck = false;

    }

    public void receiveHeartbeat(AppendEntry append) {

        NetNode node = append.getLeader();
        if (node != null) {
            this.raftContainer.updateLeader(node);
        }

        lastHeartbeat = System.currentTimeMillis();
    }

    public class HbtSender extends Thread {

        @Override
        public void run() {
            while (true) {

                try {
                    if (isSendStop) {
                        synchronized (this) {
                            sendObj.wait(5000);
                        }
                    } else {
                        // TODO 发送心跳。告诉other node leader的状态并更新
                        lastHeartbeat = System.currentTimeMillis();

                        Thread.sleep(interval - 300);
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }

            }
        }

    }

    public class HbtCheck extends Thread {

        @Override
        public void run() {
            while (true) {

                try {
                    if (!heartCheck) {
                        synchronized (this) {
                            hbtCheckObj.wait(5000);
                        }
                    } else {
                        long nowTime = System.currentTimeMillis();

                        if ((nowTime - lastHeartbeat) > heartbeatTimeout) {
                            NetNode leader = raftContainer.getRfCluster().getLeader();
                            if (leader != null) {
                                raftContainer.updateLeader(null);
                                // 更新心跳之后， election term 之前需要停止 hbtCheck
                            }
                        }

                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }

            }
        }
    }

}
