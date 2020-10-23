package com.cy.ares.dao.repo.conf;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

/**
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年09月26日 上午9:53:26
 * @version V1.0
 */
@Component
public class CacheFactory implements InitializingBean {


    @Autowired
    private EhCacheCacheManager memCacheManager;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        CacheUtil.init(this);
    }

    public Cache memCache(String cacheName) {
        Cache ehcache = memCacheManager.getCache(cacheName);
        return ehcache;
    }

    public Cache remoteCache(String cacheName) {
        return memCacheManager.getCache(cacheName);
    }

}
