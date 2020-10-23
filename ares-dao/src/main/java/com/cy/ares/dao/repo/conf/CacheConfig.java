package com.cy.ares.dao.repo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoxq
 */
@Configuration
@ConditionalOnBean(EhCacheCacheManager.class)
public class CacheConfig extends CachingConfigurerSupport {

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    /**
     * 重写这个方法，目的是用以提供默认的cacheManager
     * 
     */
    @Override
    public CacheManager cacheManager() {
        return ehCacheCacheManager;
    }

    /**
     * cacheManager名字
     */
    public static class CacheManagerName {

        /**
         * redis
         */
        public static final String REDIS_CACHE_MANAGER   = "redisCacheManager";

        /**
         * ehCache
         */
        public static final String EHCACHE_CACHE_MAANGER = "ehCacheCacheManager";
    }

}
