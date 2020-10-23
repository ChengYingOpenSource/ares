package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2ClusterDO;
import com.cy.ares.dao.common.query.Ares2ClusterQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2Cluster.
 */
public interface Ares2ClusterMapper {
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
    int insertSelective(Ares2ClusterDO record);

    /**
     * select by query condition.
     */
    List<Ares2ClusterDO> selectByQuery(Ares2ClusterQuery query);

    /**
     * select by primary key.
     */
    Ares2ClusterDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2ClusterDO record, @Param("query") Ares2ClusterQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2ClusterDO record, @Param("query") Ares2ClusterQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ClusterDO record);
}