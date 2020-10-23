package com.cy.ares.dao.repo.conf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author maoxq
 */
public class CacheUtil {

    private static CacheFactory           cacheFactory;

    public static void init(CacheFactory cacheFactoryP) {
        cacheFactory = cacheFactoryP;
    }

    public static void put(String cacheName, Object key, Object value) {
        if (key == null || StringUtils.isBlank(cacheName)) {
            return;
        }
        Cache cacheMem = cacheFactory.memCache(cacheName);
        if (cacheMem == null) {
            Cache remoteCache = cacheFactory.remoteCache(cacheName);
            if (remoteCache == null) {
                throw new IllegalStateException(String.format("cache=%s not exist!", cacheName));
            }
            remoteCache.put(key, value);
        } else {
            cacheMem.put(key, value);
        }
    }

    public static <K,V> List<V> get(String cacheName, List<K> key, Class<V> vclass, Function<K,?> notCacheHandle) {
        List<V> lv = new ArrayList<>();
        for (K k : key) {
            V v = (V)get(cacheName,k);
            if(v != null){
                lv.add(v);
            }else{
                notCacheHandle.apply(k);
            }
        }
        return lv;
    }


    public static Object get(String cacheName, Object key) {
        if (key == null || StringUtils.isBlank(cacheName)) {
            return null;
        }
        Cache cacheMem = cacheFactory.memCache(cacheName);
        if (cacheMem == null) {
            Cache remoteCache = cacheFactory.remoteCache(cacheName);
            if (remoteCache == null) {
                throw new IllegalStateException(String.format("cache=%s not exist!", cacheName));
            }
            ValueWrapper vw = remoteCache.get(key);
            if (vw != null) {
                return vw.get();
            }
            return vw;
        } else {
            ValueWrapper vw = cacheMem.get(key);
            if (vw != null) {
                return vw.get();
            }
            return vw;
        }
    }

    public static <T> T get(String cacheName, Object key, Class<T> type) {
        if (key == null || StringUtils.isBlank(cacheName)) {
            return null;
        }
        Cache cacheMem = cacheFactory.memCache(cacheName);
        if (cacheMem == null) {
            Cache remoteCache = cacheFactory.remoteCache(cacheName);
            if (remoteCache == null) {
                throw new IllegalStateException(String.format("cache=%s not exist!", cacheName));
            }
            return remoteCache.get(key, type);
        } else {
            return cacheMem.get(key, type);
        }
    }
    
}
