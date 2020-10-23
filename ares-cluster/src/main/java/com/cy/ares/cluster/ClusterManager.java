package com.cy.ares.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.ares.cluster.client.instance.ClientChannelCenter;
import com.cy.ares.cluster.conf.ConfCoordinator;
import com.cy.ares.cluster.conf.ConfPersistence;
import com.cy.ares.cluster.conf.DataConfCache;
import com.cy.ares.cluster.error.ServerBootstarpException;
import com.cy.ares.cluster.eventbus.EventBus;
import com.cy.ares.cluster.notify.ClusterNotifyHandler;
import com.cy.ares.cluster.notify.ServerCluster;
import com.cy.ares.cluster.registe.ServiceCoordinator;
import com.cy.ares.cluster.server.NettyServer;
import com.cy.ares.cluster.server.NettyWebSocketServer;
import com.cy.ares.spcp.protocol.ProtocolRegister;
import com.cy.cuirass.app.AppContext;
import com.cy.cuirass.app.AppFactory;
import com.cy.cuirass.app.AppInfo;
import com.cy.onepush.dcommon.event.LocalSubscriberRegistry;

public class ClusterManager {

    private static final Logger logger = LoggerFactory.getLogger(ClusterManager.class);
    
    // 全局设置一个缓存
    private static DataConfCache dCache ;
    private static EventBus eventBus;
    
    private ServerCluster cluster;
    
    public void readyResource() {
        
    }
    
    /**
     * clusterConf
     * confPersistence 集群可以通过内存数据库启动;
     * @thrown ServerBootstarpException  ServerBootstarpException
     * @param clusterConf
     */
    public synchronized void create(ClusterConfig clusterConf,ConfPersistence confPersistence) {
        NettyServer ns = null;
        NettyWebSocketServer wsNs = null;
        AppContext acontext = null;
        boolean flag = false;
        ProtocolRegister.registe();
        try {
            logger.info("start to create clusterManager!");
            // 1. 资源准备
            // 1.1 Cache & EventBus
            if(dCache == null)
                dCache = new DataConfCache(clusterConf.getCacheSize(), clusterConf.getCompressThreshold());
            if(eventBus == null)
                eventBus = new EventBus("ares-eventbus",new LocalSubscriberRegistry());
            
            // 创建协调者
            ClientChannelCenter ccc = new ClientChannelCenter();
            ConfCoordinator cconf = new ConfCoordinator(confPersistence,ccc);
            ServiceCoordinator scd = new ServiceCoordinator(ccc);
            eventBus.getPubSubRegister().registe(ccc);
            eventBus.getPubSubRegister().registe(cconf);
            eventBus.getPubSubRegister().registe(scd);
            
            // 2. silvery 启动, 用于集群发现 ， 集群的名字规则默认 = [name_product_env]
            AppInfo appInfo = clusterConf.getClusterInnerInfo();
            acontext = AppFactory.creatApp(appInfo);
            LocalSubscriberRegistry clusterRegistry = acontext.getCluster().getRegistry();
            // 3. create ServerCluster 
            ClusterNotifyHandler cn = new ClusterNotifyHandler(acontext,eventBus,confPersistence);
            clusterRegistry.registe(cn);
            cluster =  new ServerCluster(cn,acontext);
            
            // 4. netty server 启动
            ns = new NettyServer(clusterConf);
            ns.start(eventBus);
            // 5. netty web socket 启动
            wsNs = new NettyWebSocketServer(clusterConf);
            wsNs.start(eventBus);
            
            flag = true;
        } catch (RuntimeException e) {
            RuntimeException e2 = e instanceof ServerBootstarpException ? e : new ServerBootstarpException(e);
            throw e2;
        } catch (Exception e) {
            throw new ServerBootstarpException(e);
        } finally {
            if (!flag) {
                logger.info("cluster failed,with conf={}", JSON.toJSONString(clusterConf));
                if (ns != null)
                    ns.close();
                if  (wsNs != null) 
                    wsNs.close();
                if (acontext != null)
                    acontext.getCluster().close();
            }
        }
    }

    public static DataConfCache getdCache() {
        return dCache;
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    public ServerCluster getCluster() {
        return cluster;
    }
    
}
