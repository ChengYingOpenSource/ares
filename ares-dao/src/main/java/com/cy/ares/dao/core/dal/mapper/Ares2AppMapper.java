package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.common.query.Ares2AppQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2App.
 */
public interface Ares2AppMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2AppQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2AppQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2AppDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2AppDO record);

    /**
     * select by query condition.
     */
    List<Ares2AppDO> selectByQuery(Ares2AppQuery query);

    /**
     * select by primary key.
     */
    Ares2AppDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2AppDO record, @Param("query") Ares2AppQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2AppDO record, @Param("query") Ares2AppQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2AppDO record);
}