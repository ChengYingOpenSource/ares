package com.cy.ares.dao.repo;

import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.common.query.Ares2AppQuery;
import com.cy.ares.dao.core.manager.Ares2AppEnvManager;
import com.cy.ares.dao.core.manager.Ares2AppManager;
import com.cy.ares.dao.core.manager.Ares2EnvManager;
import com.cy.ares.dao.core.manager.Ares2NamespaceManager;
import com.cy.ares.dao.repo.conf.CacheConfig;
import com.cy.ares.dao.repo.conf.CacheCst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/6 10:59
 */
@Component
public class ConfContextRepo {

    @Autowired
    private Ares2AppManager ares2AppManager;

    @Autowired
    private Ares2NamespaceManager ares2NamespaceManager;

    @Autowired
    private Ares2EnvManager ares2EnvManager;

    @Autowired
    private Ares2AppEnvManager ares2AppEnvManager;

    @Cacheable(value = CacheCst.NAMESPACE, key = "#p0 == null?'_key_':'key_'+#p0", unless="#result == 0",cacheManager = CacheConfig.CacheManagerName.EHCACHE_CACHE_MAANGER)
    public int nsExist(String namespaceCode) {
        if (StringUtils.isBlank(namespaceCode)) {
            return 0;
        }
        int u = ares2NamespaceManager.exist(namespaceCode);
        return u;
    }

    @Cacheable(value = CacheCst.ENV, key = "#p0+'_'+#p1", unless="#result == 0",cacheManager = CacheConfig.CacheManagerName.EHCACHE_CACHE_MAANGER)
    public int envExist(String namespaceCode,String envCode) {
        if (StringUtils.isBlank(namespaceCode)) {
            return 0;
        }
        int u = ares2EnvManager.exist(namespaceCode,envCode);
        return u;
    }

    @Cacheable(value = CacheCst.APP, key = "#p0+'_'+#p1", unless="#result == null",cacheManager = CacheConfig.CacheManagerName.EHCACHE_CACHE_MAANGER)
    public Ares2AppDO appFindByCode(String namespaceCode,String appCode) {
        if (StringUtils.isAnyBlank(namespaceCode,appCode)) {
            return null;
        }
        Ares2AppQuery query = new Ares2AppQuery();
        Ares2AppQuery.Criteria criteria = query.createCriteria();
            criteria.andNamespaceCodeEqualTo(namespaceCode);
            criteria.andAppCodeEqualTo(appCode);
        Ares2AppDO ares2AppDO = ares2AppManager.selectOneByQuery(query);
        return ares2AppDO;
    }

    @Cacheable(value = CacheCst.APP_ENV, key = "#p0+'_'+#p1+'_'+#p2", unless="#result == 0",cacheManager = CacheConfig.CacheManagerName.EHCACHE_CACHE_MAANGER)
    public int appEnvExist(String namespaceCode,String appCode,String envCode) {
        if (StringUtils.isAnyBlank(namespaceCode,appCode,envCode)) {
            return 0;
        }
        int u = ares2AppEnvManager.exist(namespaceCode,appCode,envCode);
        return u;
    }

}
