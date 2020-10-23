package com.cy.ares.dao.repo.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author maoxq
 */
@Configuration
public class EhCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(EhCacheConfig.class);

    @Bean(name= CacheConfig.CacheManagerName.EHCACHE_CACHE_MAANGER)
    public org.springframework.cache.CacheManager ehCacheCacheManager() {
        Resource location = new ClassPathResource("ehcahce.xml");
        try {
            net.sf.ehcache.CacheManager cm = EhCacheManagerUtils.buildCacheManager(location);
            return new EhCacheCacheManager(cm);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
       
    }
}
