package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2NamespaceDO;
import com.cy.ares.dao.common.query.Ares2NamespaceQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2NamespaceExtMapper;
import com.cy.ares.dao.core.manager.Ares2NamespaceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Manager for Ares2Namespace.
 */

@Component
public class Ares2NamespaceManagerImpl implements Ares2NamespaceManager {

    @Autowired
    protected Ares2NamespaceExtMapper ares2NamespaceExtMapper;

    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2NamespaceQuery query) {
        return ares2NamespaceExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2NamespaceQuery query) {
        return ares2NamespaceExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2NamespaceDO record) {
        return ares2NamespaceExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2NamespaceDO record) {
        return ares2NamespaceExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2NamespaceDO> selectByQuery(Ares2NamespaceQuery query) {
        return ares2NamespaceExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2NamespaceDO selectOneByQuery(Ares2NamespaceQuery query) {
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2NamespaceDO> topList = selectByQuery(query);
        if (topList != null && topList.size() > 0) {
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2NamespaceDO> selectByQuery(Ares2NamespaceQuery query, int size) {
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
     */
    @Override
    public PageResult<Ares2NamespaceDO> selectByQueryWithPage(Ares2NamespaceQuery query) {
        PageResult<Ares2NamespaceDO> result = new PageResult<Ares2NamespaceDO>();
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
    public Ares2NamespaceDO selectByPrimaryKey(Long id) {
        return ares2NamespaceExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective(Ares2NamespaceDO record, Ares2NamespaceQuery query) {
        return ares2NamespaceExtMapper.updateByQuerySelective(record, query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery(Ares2NamespaceDO record, Ares2NamespaceQuery query) {

        return ares2NamespaceExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2NamespaceDO record) {
        return ares2NamespaceExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteNamespace(String namespaceCode) {
        return ares2NamespaceExtMapper.deleteNamespace(namespaceCode);
    }

    @Override
    public int exist(String namespaceCode) {
        Ares2NamespaceQuery query = new Ares2NamespaceQuery();
        query.createCriteria().andNamespaceCodeEqualTo(namespaceCode);
        return selectOneByQuery(query) == null ? 0 : 1;
    }
}