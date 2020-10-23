package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2ConfHistoryDO;
import com.cy.ares.dao.common.query.Ares2ConfHistoryQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2ConfHistory.
 */
public interface Ares2ConfHistoryMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2ConfHistoryQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2ConfHistoryQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2ConfHistoryDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2ConfHistoryDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfHistoryDO> selectByQuery(Ares2ConfHistoryQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfHistoryDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2ConfHistoryDO record, @Param("query") Ares2ConfHistoryQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2ConfHistoryDO record, @Param("query") Ares2ConfHistoryQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfHistoryDO record);
}