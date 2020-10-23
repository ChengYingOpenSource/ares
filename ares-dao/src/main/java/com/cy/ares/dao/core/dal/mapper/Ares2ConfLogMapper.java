package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2ConfLogDO;
import com.cy.ares.dao.common.query.Ares2ConfLogQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2ConfLog.
 */
public interface Ares2ConfLogMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2ConfLogQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2ConfLogQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2ConfLogDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2ConfLogDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfLogDO> selectByQuery(Ares2ConfLogQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfLogDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2ConfLogDO record, @Param("query") Ares2ConfLogQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2ConfLogDO record, @Param("query") Ares2ConfLogQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfLogDO record);
}