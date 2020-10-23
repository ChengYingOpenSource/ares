package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2EnvDO;
import com.cy.ares.dao.common.query.Ares2EnvQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2EnvExtMapper;
import com.cy.ares.dao.core.manager.Ares2EnvManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Manager for Ares2Env.
 */

@Component
public class Ares2EnvManagerImpl implements Ares2EnvManager {

    @Autowired
    protected Ares2EnvExtMapper ares2EnvExtMapper;

    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2EnvQuery query) {
        return ares2EnvExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2EnvQuery query) {
        return ares2EnvExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2EnvDO record) {
        return ares2EnvExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2EnvDO record) {
        return ares2EnvExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2EnvDO> selectByQuery(Ares2EnvQuery query) {
        return ares2EnvExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2EnvDO selectOneByQuery(Ares2EnvQuery query) {
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2EnvDO> topList = selectByQuery(query);
        if (topList != null && topList.size() > 0) {
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2EnvDO> selectByQuery(Ares2EnvQuery query, int size) {
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
     */
    @Override
    public PageResult<Ares2EnvDO> selectByQueryWithPage(Ares2EnvQuery query) {
        PageResult<Ares2EnvDO> result = new PageResult<Ares2EnvDO>();
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
    public Ares2EnvDO selectByPrimaryKey(Long id) {
        return ares2EnvExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective(Ares2EnvDO record, Ares2EnvQuery query) {
        return ares2EnvExtMapper.updateByQuerySelective(record, query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery(Ares2EnvDO record, Ares2EnvQuery query) {

        return ares2EnvExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2EnvDO record) {
        return ares2EnvExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteEnv(String namespaceCode, String envCode) {
        return ares2EnvExtMapper.deleteEnv(namespaceCode, envCode);
    }

    @Override
    public List<Ares2EnvDO> listUnderApp(String namespaceCode, String appCode) {
        return ares2EnvExtMapper.listUnderApp(namespaceCode, appCode);
    }

    @Override
    public int exist(String namespaceCode, String envCode) {
        Ares2EnvQuery query = new Ares2EnvQuery();
        query.createCriteria().andNamespaceCodeEqualTo(namespaceCode).andEnvCodeEqualTo(envCode);
        Ares2EnvDO ares2EnvDO = selectOneByQuery(query);
        return ares2EnvDO == null ? 0 : 1;
    }
}