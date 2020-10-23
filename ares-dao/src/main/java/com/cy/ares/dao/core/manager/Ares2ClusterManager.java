package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ClusterDO;
import com.cy.ares.dao.common.query.Ares2ClusterQuery;

import java.util.List;


/**
 * Manager for Ares2Cluster.
 */
public interface Ares2ClusterManager {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2ClusterQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2ClusterQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2ClusterDO record);

    /**
     * insert selective.
     */
    long insertSelective(Ares2ClusterDO record);

    /**
     * select by query condition.
     */
    List<Ares2ClusterDO> selectByQuery(Ares2ClusterQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2ClusterDO selectOneByQuery(Ares2ClusterQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2ClusterDO> selectByQuery(Ares2ClusterQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2ClusterDO> selectByQueryWithPage(Ares2ClusterQuery query);

    /**
     * select by primary key.
     */
    Ares2ClusterDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2ClusterDO record, Ares2ClusterQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2ClusterDO record, Ares2ClusterQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ClusterDO record);

    int exist(Ares2ClusterDO record);
}