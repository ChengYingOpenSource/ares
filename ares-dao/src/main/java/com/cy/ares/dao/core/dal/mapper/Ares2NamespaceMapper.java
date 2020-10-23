package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2NamespaceDO;
import com.cy.ares.dao.common.query.Ares2NamespaceQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2Namespace.
 */
public interface Ares2NamespaceMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2NamespaceQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2NamespaceQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2NamespaceDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2NamespaceDO record);

    /**
     * select by query condition.
     */
    List<Ares2NamespaceDO> selectByQuery(Ares2NamespaceQuery query);

    /**
     * select by primary key.
     */
    Ares2NamespaceDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2NamespaceDO record, @Param("query") Ares2NamespaceQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2NamespaceDO record, @Param("query") Ares2NamespaceQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2NamespaceDO record);
}