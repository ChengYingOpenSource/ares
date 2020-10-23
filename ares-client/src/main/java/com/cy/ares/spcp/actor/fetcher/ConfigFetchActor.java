package com.cy.ares.spcp.actor.fetcher;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.cy.ares.spcp.cst.DataIdActionCst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.actor.cache.DataConfCache;
import com.cy.ares.spcp.client.ReceiverFuture;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.cst.ConfigCst;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.DataGroupKey;
import com.cy.ares.spcp.protocol.DataItem;
import com.cy.ares.spcp.protocol.FetchReq;
import com.cy.ares.spcp.protocol.FetchResp;
import com.cy.ares.spcp.protocol.PushAck;
import com.cy.ares.spcp.protocol.PushResp;

public class ConfigFetchActor {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFetchActor.class);

    private SpcpContext ctx;
    private DataConfCache cache;
    private Listener defListener;
    private Listener logListener = new LogListener();

    private Map<String, Listener> listenerMap = new ConcurrentHashMap<>();
    private Map<String, Listener> listenerPrefixMatchMap = new ConcurrentHashMap<>();
    private Listener globalListener;

    public static final String semicolon = ";";

    public ConfigFetchActor(SpcpContext ctx,DataConfCache confCache) {
        this.ctx = ctx;
        this.cache = confCache;
    }

    private void log(DataItem item) {
        try {
            if (logListener != null) {
                logListener.receive(item);
            } else {
                synchronized (this) {
                    logger.info("create logListener!");
                    logListener = new LogListener();
                }
                logListener.receive(item);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean fetch(boolean isListenerInvoke,int readTimeout) {
        FetchReq fr = new FetchReq();
        fr.setGroupKey(instanceDataGroup());
        FetchResp resp = fetch(fr,readTimeout);
        if (resp == null) {
            return false;
        }
        List<DataItem> itemList = resp.getItemList();
        if (itemList == null || itemList.isEmpty()) {
            logger.warn("fetch data item is empty!");
            return true;
        }
        logger.info("fetch data size={}", itemList.size());
        for (DataItem item : itemList) {
            log(item);
            // 更新到缓存
            boolean f = cache.put(item);
            if (!f) {
                continue;
            }
            if (isListenerInvoke) {
                invokerListener(item);
            }
        }
        return true;

    }

    /**
     * 注册的key, 全部获取一遍;
     *
     * @param isListenerInvoke 当与本地缓存不一致的情况下，是否调用Listener
     * @return
     */
    public boolean fetch(boolean isListenerInvoke) {
        int readTimeout = ctx.getConfig().getReadTimeout();
        return fetch(isListenerInvoke,readTimeout);
    }

    public DataGroupKey instanceDataGroup() {
        DataGroupKey groupKey = new DataGroupKey();
        ClientInstanceInfo instanceInfo = this.ctx.getInstanceInfo();
        groupKey.setAppCode(instanceInfo.getAppCode());
        groupKey.setEnvCode(instanceInfo.getEnvCode());
        groupKey.setClusterCode(instanceInfo.getClusterCode());
        groupKey.setNamespaceCode(instanceInfo.getNamespaceCode());
        return groupKey;
    }

    private FetchResp fetch(FetchReq fr,int readTimeout) {
        ReceiverFuture rf = ctx.getSender().send(fr, readTimeout);
        Event event = null;
        try {
            event = rf.get(readTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        FetchResp resp = (FetchResp) MessageConvert.toResp(event);
        return resp;
    }

    private FetchResp fetch(FetchReq fr) {
        int readTimeout = ctx.getConfig().getReadTimeout();
        return fetch(fr,readTimeout);
    }

    public DataItem getConfig(String dataId) {
        return getConfig(dataId, ConfigCst.GROUP_DEF, 3000);
    }

    public DataItem getConfigCache(String dataId, String group) {
        String k = DataItem.keyWithGroup(dataId, group);
        // 从缓存获取
        DataItem di = this.cache.find(k);
        if (di != null) {
            // copy值返回，不然会被上层修改
            return di.copy();
        }
        return null;
    }

    public DataItem getConfigCache(String dataId) {
        return getConfigCache(dataId, ConfigCst.GROUP_DEF);
    }

    /**
     * 返回 DataItem的副本
     * 
     * @param dataId
     * @param group
     * @param timeout
     * @return
     */
    public DataItem getConfig(String dataId, String group, int timeout) {
        String k = DataItem.keyWithGroup(dataId, group);
        // 从缓存获取
        DataItem di = this.cache.find(k);
        if (di != null) {
            // copy值返回，不然会被上层修改
            return di.copy();
        }
        boolean notExist = this.cache.confNotExist(k);
        if(notExist){
            return null;
        }
        Set<String> keys = new HashSet<>();
        keys.add(k);
        FetchReq fr = new FetchReq();
        fr.setKeys(keys);
        DataGroupKey dgk = instanceDataGroup();
        dgk.setGroup(group);
        fr.setGroupKey(dgk);
        FetchResp resp = fetch(fr);
        if (resp == null) {
            logger.warn("dataId={},group={},not found return null!", dataId, group);
            return null;
        }
        List<DataItem> itemList = resp.getItemList();
        if (itemList == null || itemList.isEmpty()) {
            this.cache.putEmpty(k);
            logger.warn("fetch data item is empty!dataId={},group={}", dataId, group);
            return null;
        }
        DataItem dir = itemList.get(0);
        log(dir);
        this.cache.put(dir);
        return dir.copy();
    }

    private void pushAck(PushResp resp) {
        try {
            PushAck ack = new PushAck();
            ack.setEventId(resp.getEventId());
            ctx.getSender().sendAck(ack);
        } catch (Exception e) {
            logger.error("pushAck req failed! " + e.getMessage(), e);
        }
    }

    /**
     * callback调用
     * @param resp
     */
    void trigger(PushResp resp) {
        // 触发listener ;
        pushAck(resp);
        List<DataItem> items = resp.getItems();
        if (items == null) {
            logger.warn("server pushResp dataItem is null!");
            return;
        }
        for (DataItem item : items) {
            log(item);
            // 更新到缓存
            boolean f = cache.put(item);
            if (!f) {
                continue;
            }
            invokerListener(item);
        }
    }
    
    private void invokerListener(DataItem item){
        if(DataIdActionCst.DEL.equals(item.getAction())){
            return;
        }
        String dataId = item.getDataId();
        String group = item.getGroup();
        // 优先匹配 key 
        String mapKey = DataItem.keyWithGroup(dataId, group);
        Listener listener = listenerMap.get(mapKey);
        if (listener != null) {
            listener.receive(item);
            return;
        }
        // 其次匹配前缀, 前缀匹配如果满足多个 -> 只调用最长前缀的那个
        int max = 0;
        for(String prefixKey:this.listenerPrefixMatchMap.keySet()){
            if(mapKey.startsWith(prefixKey)){
                if(max<prefixKey.length()){
                    listener = this.listenerPrefixMatchMap.get(prefixKey);
                    max = prefixKey.length();
                }
            }
        }
        listener = listener == null?globalListener:listener;
        if (listener != null) {
            listener.receive(item);
        }
    }
    
    public void setPrefixListener(String prefixMatch,Listener listener){
        this.listenerPrefixMatchMap.put(DataItem.keyWithGroup(prefixMatch, ConfigCst.GROUP_DEF), listener);
    }
    
    public void setPrefixListener(String prefixMatch,String group,Listener listener){
        this.listenerPrefixMatchMap.put(DataItem.keyWithGroup(prefixMatch, group), listener);
    }
    
    /**
     * 如果key没有注册 listener,则使用 globalListener
     * @param listener
     */
    public void setGlobalListener(Listener listener){
        this.globalListener = listener;
    }
    

    /**
     * 设置默认 listener, 主要是简化 addListener 传参的过程;
     * 如果 addListener 没有输入 Listener, 则使用默认的listener
     * 
     * @param listener
     */
    public void setDefListener(Listener listener) {
        this.defListener = listener;
    }

    /**
     * 不传listener , 则使用默认的listener;
     * 
     * @param dataId
     */
    public void addListener(String dataId) {
        listenerMap.put(DataItem.keyWithGroup(dataId, ConfigCst.GROUP_DEF), this.defListener);
    }

    /**
     * 不传listener , 则使用默认的listener;
     * 
     * @param dataId
     * @param group
     */
    public void addListener(String dataId, String group) {
        listenerMap.put(DataItem.keyWithGroup(dataId, group), this.defListener);
    }

    public void addListener(String dataId, Listener listener) {
        listenerMap.put(DataItem.keyWithGroup(dataId, ConfigCst.GROUP_DEF), listener);
    }

    public void addListener(String dataId, String group, Listener listener) {
        listenerMap.put(DataItem.keyWithGroup(dataId, group), listener);
    }

    public void removeListener(String dataId) {
        listenerMap.remove(DataItem.keyWithGroup(dataId, ConfigCst.GROUP_DEF));
    }

    public void removeListener(String dataId, String group) {
        listenerMap.remove(DataItem.keyWithGroup(dataId, group));
    }

}
