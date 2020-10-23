package com.cy.ares.cluster.notify;

import com.cy.ares.spcp.cst.DataIdActionCst;
import com.cy.ares.spcp.protocol.DataGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.cy.ares.spcp.protocol.DataItem;
import com.cy.cuirass.app.AppContext;

import java.util.List;


public class ServerCluster {
    
    private static final Logger logger = LoggerFactory.getLogger(ServerCluster.class);
    
    private ClusterNotifyHandler clusterNotify;
    
    private AppContext appContext;
    
    public static final int MAX_PUB_SIZE = 128;
    
    public ServerCluster(ClusterNotifyHandler cn, AppContext appContext){
        this.clusterNotify = cn;
        this.appContext = appContext;
    }

    public ClusterNotifyHandler getClusterNotify() {
        return clusterNotify;
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public boolean pubBatch(DataGroupKey dataGroupKey,List<DataItem> diList,ICallback callback){
        NotifyEvent ne = new NotifyEvent();
        ne.setDataItemList(diList);
        ne.setBatch(true);
        ne.setHappenTime(System.currentTimeMillis());
        return pub0(dataGroupKey,ne,callback);
    }

    public boolean pub(DataItem di,ICallback callback) {
        NotifyEvent ne = new NotifyEvent();
        ne.setDataItem(di);
        ne.setBatch(false);
        ne.setHappenTime(System.currentTimeMillis());
        if (di.getContentSize() > MAX_PUB_SIZE) {
            ne.setNoContent(true);
            di.setContent(null);
            di.setDigest(null);
        }
        return pub0(di.groupKey(),ne,callback);
    }

    private boolean pub0(DataGroupKey dataGroupKey,NotifyEvent ne, ICallback callback){
        String logKey = null;
        if(!ne.isBatch()){
            logKey = ne.getDataItem().key();
        }else{
            logKey = ne.getDataItemList().get(0).key();
        }
        boolean f = clusterNotify.pub(ne, 3);
        if(!f){
            // retry
            logger.warn("clusterNotify first failed!,then retry!,dataKey={}",logKey);
            f = clusterNotify.pub(ne, 5);
        }
        if(f){
            // 插入数据库 commit
            NotifyCommitEvent event = new NotifyCommitEvent();
            if(callback != null){
                Boolean cr = (Boolean)callback.success(ne);
                if(cr != null && cr.equals(Boolean.FALSE))
                    event.setReload(Boolean.TRUE);
            }
            // commit event 
            logger.info("clusterNotify start commit! dataKey={}",logKey);
            if(!ne.isBatch()){
                DataItem di = ne.getDataItem();
                event.setDataGroup(dataGroupKey);
                event.setDataId(di.getDataId());
                event.setDataIdAction(di.getAction());
            }else{
                event.setDataGroup(dataGroupKey);
                event.setBatch(true);
                event.setDataIdAction(DataIdActionCst.MERGE);
            }
            event.setHappenTime(System.currentTimeMillis());
            event.setNotifyEventId(ne.getEventId());
            clusterNotify.pub(event);
        }else{
            logger.warn("clusterNotify pub failed! dataKey={}",logKey);
            if(callback != null)
                callback.failed(ne, null);
        }
        return f;
    }
    
    
}
