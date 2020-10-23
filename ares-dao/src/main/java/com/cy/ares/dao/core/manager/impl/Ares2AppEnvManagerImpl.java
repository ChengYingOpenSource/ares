package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.common.model.Ares2AppEnvDO;
import com.cy.ares.dao.common.query.Ares2AppEnvQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2AppEnvExtMapper;
import com.cy.ares.dao.core.manager.Ares2AppEnvManager;

import com.cy.ares.dao.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Manager for Ares2AppEnv.
 */

@Component
public class Ares2AppEnvManagerImpl implements Ares2AppEnvManager {

    @Autowired
    protected Ares2AppEnvExtMapper ares2AppEnvExtMapper;

    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2AppEnvQuery query) {
        return ares2AppEnvExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2AppEnvQuery query) {
        return ares2AppEnvExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2AppEnvDO record) {
        return ares2AppEnvExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2AppEnvDO record) {
        return ares2AppEnvExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2AppEnvDO> selectByQuery(Ares2AppEnvQuery query) {
        return ares2AppEnvExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2AppEnvDO selectOneByQuery(Ares2AppEnvQuery query) {
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2AppEnvDO> topList = selectByQuery(query);
        if (topList != null && topList.size() > 0) {
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2AppEnvDO> selectByQuery(Ares2AppEnvQuery query, int size) {
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
     */
    @Override
    public PageResult<Ares2AppEnvDO> selectByQueryWithPage(Ares2AppEnvQuery query) {
        PageResult<Ares2AppEnvDO> result = new PageResult<Ares2AppEnvDO>();
        result.setPageSize(query.getPageSize());
        result.setPageNo(query.getPageNo());
        result.setTotalCount(this.countByQuery(query));
        result.setResult(this.selectByQuery(query));
        return result;
    }

    /**
     * select by primary key.
     */
    @Override
    public Ares2AppEnvDO selectByPrimaryKey(Long id) {
        return ares2AppEnvExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective(Ares2AppEnvDO record, Ares2AppEnvQuery query) {
        return ares2AppEnvExtMapper.updateByQuerySelective(record, query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery(Ares2AppEnvDO record, Ares2AppEnvQuery query) {

        return ares2AppEnvExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2AppEnvDO record) {
        return ares2AppEnvExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int exist(String namespaceCode, String appCode, String envCode) {
        Ares2AppEnvQuery query = new Ares2AppEnvQuery();
        Ares2AppEnvQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(namespaceCode)) {
            criteria.andNamespaceCodeEqualTo(namespaceCode);
        }
        if (StringUtils.isNotBlank(appCode)) {
            criteria.andAppCodeEqualTo(appCode);
        }
        if (StringUtils.isNotBlank(envCode)) {
            criteria.andEnvCodeEqualTo(envCode);
        }
        query.setOrderByClause(" id desc");
        Ares2AppEnvDO ares2AppEnvDO = selectOneByQuery(query);
        if (ares2AppEnvDO != null) {
            return Integer.valueOf(String.valueOf(ares2AppEnvDO.getId()));
        }
        return 0;
    }
}