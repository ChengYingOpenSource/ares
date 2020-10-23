package com.cy.ares.spcp.actor.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cy.ares.spcp.cst.DataIdActionCst;
import com.cy.ares.spcp.protocol.DataItem;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * cache缓存 线上的数据，同时定时进行与线上compare
 * 
 * @author maoxq
 * 
 * @Description
 * 
 * @date 2019年5月5日 下午7:42:28
 * @version V1.0
 */
public class DataConfCache {

    /***
     *
     * note :if the kye is delete form server end ,sync this event
     * */

    /***
     *  the larger cache pool
     * */
    private Cache<String, DataItem> confCache = CacheBuilder.newBuilder().maximumSize(50000)
            .build();

    private Cache<String, DataItem> confCache4Empty = CacheBuilder.newBuilder().maximumSize(3000)
            .build();
    
    public DataConfCache() {
        
    }

    private static final DataItem di = new DataItem();

    /**
     * putEmpty & put并发问题，所有内部需要进行二次判断
     * @param key
     */
    public synchronized void putEmpty(String key){
        if(this.find(key) == null){
            confCache4Empty.put(key,di);
        }
    }

    public boolean confNotExist(String key){
        return confCache4Empty.getIfPresent(key)!=null;
    }

    private boolean notEquals(DataItem e1,DataItem e2){
        return e1 == null || e1.getLastTimeModify() <= e2.getLastTimeModify() || !e1.getDigest().equals(e2.getDigest());
    }
    
    public synchronized boolean put(DataItem item) {
        String key = DataItem.keyWithGroup(item.getDataId(), item.getGroup());
        if(DataIdActionCst.DEL.equals(item.getAction())){
            this.confCache.invalidate(key);
            this.confCache4Empty.invalidate(key);
            return true;
        }
        this.confCache4Empty.invalidate(key);
        DataItem e = confCache.getIfPresent(key);
        if (notEquals(e,item)) {
            item.setSignTime(System.currentTimeMillis());
            confCache.put(key, item);
            return true;
        }
        return false;
    }
    
    public synchronized void putAll(List<DataItem> itemList) {
        if (itemList == null) {
            return;
        }
        itemList.forEach(item -> {
            String key = DataItem.keyWithGroup(item.getDataId(), item.getGroup());
            this.confCache4Empty.invalidate(key);
            if(DataIdActionCst.DEL.equals(item.getAction())){
                this.confCache.invalidate(key);
            }else{
                DataItem e = confCache.getIfPresent(key);
                if (notEquals(e,item)) {
                    item.setSignTime(System.currentTimeMillis());
                    confCache.put(key, item);
                }
            }
        });
    }
    
    public void clear() {
        this.confCache.cleanUp();
        this.confCache4Empty.cleanUp();
    }
    
    public DataItem find(String key) {
        return confCache.getIfPresent(key);
    }
    
}
