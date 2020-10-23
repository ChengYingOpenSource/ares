package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ClusterDO;
import com.cy.ares.dao.common.query.Ares2ClusterQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2ClusterExtMapper;
import com.cy.ares.dao.core.manager.Ares2ClusterManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Manager for Ares2Cluster.
 */

@Component
public class Ares2ClusterManagerImpl implements Ares2ClusterManager {

    @Autowired
    protected Ares2ClusterExtMapper ares2ClusterExtMapper;

    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2ClusterQuery query) {
        return ares2ClusterExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2ClusterQuery query) {
        return ares2ClusterExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2ClusterDO record) {
        return ares2ClusterExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2ClusterDO record) {
        return ares2ClusterExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2ClusterDO> selectByQuery(Ares2ClusterQuery query) {
        return ares2ClusterExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2ClusterDO selectOneByQuery(Ares2ClusterQuery query) {
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2ClusterDO> topList = selectByQuery(query);
        if (topList != null && topList.size() > 0) {
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2ClusterDO> selectByQuery(Ares2ClusterQuery query, int size) {
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
     */
    @Override
    public PageResult<Ares2ClusterDO> selectByQueryWithPage(Ares2ClusterQuery query) {
        PageResult<Ares2ClusterDO> result = new PageResult<Ares2ClusterDO>();
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
    public Ares2ClusterDO selectByPrimaryKey(Long id) {
        return ares2ClusterExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective(Ares2ClusterDO record, Ares2ClusterQuery query) {
        return ares2ClusterExtMapper.updateByQuerySelective(record, query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery(Ares2ClusterDO record, Ares2ClusterQuery query) {

        return ares2ClusterExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2ClusterDO record) {
        return ares2ClusterExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int exist(Ares2ClusterDO record) {
        Ares2ClusterQuery query = new Ares2ClusterQuery();
        Ares2ClusterQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(record.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(record.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(record.getAppCode())) {
            criteria.andAppCodeEqualTo(record.getAppCode());
        }
        if (StringUtils.isNotBlank(record.getEnvCode())) {
            criteria.andEnvCodeEqualTo(record.getEnvCode());
        }
        if (StringUtils.isNotBlank(record.getClusterCode())) {
            criteria.andClusterCodeEqualTo(record.getClusterCode());
        }
        query.setOrderByClause(" id desc");
        Ares2ClusterDO ares2ClusterDO = selectOneByQuery(query);
        if (ares2ClusterDO != null) {
            return Integer.valueOf(String.valueOf(ares2ClusterDO.getId()));
        }
        return 0;
    }
}