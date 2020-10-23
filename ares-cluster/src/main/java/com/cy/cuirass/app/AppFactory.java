package com.cy.cuirass.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.cuirass.cluster.AkCluster;
import com.cy.cuirass.cluster.AkNode;
import com.cy.cuirass.cluster.ClusterListener;
import com.cy.cuirass.cluster.DefClusterListener;
import com.cy.cuirass.error.AkClusterStartException;
import com.cy.cuirass.net.InetUtils;
import com.cy.cuirass.net.InetUtils.HostInfo;
import com.cy.cuirass.raft.DefRaftLeaderListener;
import com.cy.onepush.dcommon.async.Async;
import com.cy.onepush.dcommon.async.Await;

import akka.dispatch.affinity.ThrowOnOverflowRejectionHandler;

@SuppressWarnings("all")
public class AppFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(AppFactory.class);
    
    private static final Await await  = new Await();
    
    /**
     * 建议异步创建
     * @param appInfo
     */
    public static AppContext creatApp(AppInfo appInfo) {
        AppContext acontext = new AppContext();
        // 本地node
        HostInfo info = InetUtils.getFirstNonLoopbackHostInfo();
        int port = appInfo.getPort();
        AkNode akNode = new AkNode();
        akNode.setIp(info.getIpAddress());
        akNode.setPort(port);
        akNode.setHostName(info.getHostname());
        akNode.setHostNameEnable(appInfo.isHostNameEnable());
        logger.info("localAkNode ={}",JSON.toJSONString(akNode));
        
        // cluster config
        String seedNodes = appInfo.getSeedNodes();
        if(StringUtils.isBlank(seedNodes)){            
            throw new IllegalStateException("seedNodes配置不能为空！");
        }
        // important!
        List<AkNode> asNodes = seedNode(akNode,appInfo);
        
        ClusterListener listener = new DefClusterListener();
        AkCluster cluster = new AkCluster();
        // 执行器
        Async async = new Async();
        String clusterName = appInfo.getClusterName();
        cluster.init(clusterName, akNode, async, listener,asNodes);
        
        // 默认等待join时间 ; 正常应该是 cluster启动成功之后init raft; 
        // node num 不能全部被发现
        if(appInfo.isRaftEnable()){
            // 进行种子节点
            int len = asNodes.size();
            try {
                // 3-5
                Random random = new Random();
                int l = Math.abs(random.nextInt(appInfo.getRaftWaitIncr()));
                await.await(l+appInfo.getRaftWaitTime(), new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        try { Thread.sleep(500); } catch (InterruptedException e) { }
                        int num = cluster.clusterNum();
                        return num >= len;
                    }
                });
            } catch (Exception e) {
                logger.warn("cluster get member num is timeout!,exception msg={}:",e.getMessage());
                if(appInfo.isSeekNumCheck()){
                    cluster.close();
                    // 抛出异常
                    throw new AkClusterStartException(e);
                }
            } finally {
            }
            // 创建raftNodeActor & setListener
            cluster.raftNode(appInfo.getLeaderListeners()==null?new DefRaftLeaderListener():appInfo.getLeaderListeners());
            cluster.raftInit();
        }
        
        acontext.setInfo(appInfo);
        acontext.setCluster(cluster);
        return acontext;
    }
    
    /**
     * Note: seed node 第一个节点的ip [第一次cluster成功启动前]必须真实存在一个服务
     * @param appInfo
     * @param ecp
     * @return
     */
   private static List<AkNode> seedNode(AkNode localNode,AppInfo appInfo){
        
        List<AkNode> listR = new ArrayList<>();
        String seedNodes = appInfo.getSeedNodes();
        boolean f = appInfo.isHostNameEnable();
        String[] pStrings = seedNodes.split(",");
        for(String one:pStrings){
            String[] t = one.split(":");
            AkNode ak = new AkNode();
            ak.setHostNameEnable(f);
            if(f){
                ak.setHostName(t[0].trim());
            }else{
                ak.setIp(t[0].trim());
            }
            if(t.length == 1){
                ak.setPort(80);
            }else{
                ak.setPort(Integer.valueOf(t[1].trim()));
            }
            listR.add(ak);
        }
        return listR;
    }
    
    
    

}
