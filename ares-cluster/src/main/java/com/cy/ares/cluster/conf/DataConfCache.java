package com.cy.ares.cluster.conf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.common.utils.CompressUtil;
import com.cy.ares.spcp.cst.CompressCst;
import com.cy.ares.spcp.protocol.DataItem;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * cache缓存 线上的数据，同时定时进行与db compare
 * 
 * @author maoxq
 * 
 * @Description
 * 
 * @date 2019年5月5日 下午7:42:28
 * @version V1.0
 */
public class DataConfCache {

    private static final Logger logger = LoggerFactory.getLogger(DataConfCache.class);

    // 进行压缩的阀值
    private static final int compress_threshold = 1024;

    // 1k*1000*300 = 300m
    private Cache<String, DataItem> confCache;
    
    private  int compressThreshold = 1024;
    
    private ConcurrentSkipListSet<DataItemIndex> skipSet;

    public DataConfCache(int cacheSize, int compressThreshold) {
        compressThreshold = compressThreshold <= compress_threshold ? compress_threshold : compressThreshold;
        cacheSize = cacheSize <= 5000?5000:cacheSize;
        this.compressThreshold = compressThreshold;
        
        confCache = CacheBuilder.newBuilder().maximumSize(cacheSize) // 较大的cache
                .weakValues().weakKeys().removalListener(new RemovalListener<String, DataItem>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, DataItem> notification) {
                        DataItem item = notification.getValue();
                        String key = notification.getKey();
                        skipSet.remove(new DataItemIndex(key, item.getDigest(), item.getLastTimeModify(), item.getSignTime()));
                    }
                }).build();
        skipSet = new ConcurrentSkipListSet<>(new Comparator<DataItemIndex>() {
            public int compare(DataItemIndex o1, DataItemIndex o2) {
                long t = o1.getModifyTime() - o2.getModifyTime();
                if (t > 0) {
                    return 1;
                } else if (t == 0) {
                    return 0;
                } else {
                    return -1;
                }
            };
        });
    }

    public synchronized void put(DataItem item) {
        putOne(item);
    }

    private void putOne(DataItem item) {
        String key = item.key();
        DataItem e = confCache.getIfPresent(key);
        if (e == null || e.getLastTimeModify() <= item.getLastTimeModify()) {
            item.setSignTime(System.currentTimeMillis());
            long mtime = item.getLastTimeModify();
            String content = item.getContent();
            int size = content.getBytes().length;
            if (size >= compressThreshold) {
                try {
                    byte[] b = CompressUtil.compress(content.getBytes());
                    item.setContent(null);
                    item.setCompress(CompressCst.compress);
                    item.setCompressContent(b);
                } catch (Exception e1) {
                    logger.error("key={} compress failed!error msg = {}", key, e1.getMessage());
                }
            } else {
                item.setCompress(CompressCst.uncompress);
                item.setCompressContent(null);
            }
            confCache.put(key, item);
            skipSet.add(new DataItemIndex(key, item.getDigest(), mtime, item.getSignTime()));
        }
    }

    public synchronized void putAll(List<DataItem> itemList) {
        if (itemList == null) {
            return;
        }
        itemList.forEach(item -> {
            putOne(item);
        });
    }

    public void delete(String key) {
        DataItem e = confCache.getIfPresent(key);
        if (e == null) {
            return;
        }
        confCache.invalidate(key);
        skipSet.remove(new DataItemIndex(key, e.getDigest(), e.getLastTimeModify(), e.getSignTime()));
    }

    public void clear() {
        confCache.cleanUp();
        skipSet.clear();
    }

    public DataItem find(String key) {
        return confCache.getIfPresent(key);
    }

    /**
     * 从某个时间开始，去N个缓存元素
     * 
     * @param signTimeBefore
     * @param modifyTimeStart
     * @param num
     * @return
     */
    public List<DataItemIndex> subSet(long signTimeBefore, long modifyTimeStart, int num) {
        // 取的策略: 去N个, 记录最后一个的时间
        NavigableSet<DataItemIndex> t = skipSet.subSet(new DataItemIndex(modifyTimeStart), new DataItemIndex(System.currentTimeMillis()));
        List<DataItemIndex> lresult = new ArrayList<>();
        if (t.isEmpty()) {
            return lresult;
        }
        int i = 0;
        for (DataItemIndex di : t) {
            if (i > num) {
                break;
            }
            // 放入缓存的时间 在 signTimeBefore 之前才进行返回
            if (di.getSignTime() <= signTimeBefore) {
                lresult.add(di);
            }
            i++;
        }
        return lresult;
    }

}
