package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfHistoryDO;
import com.cy.ares.dao.common.query.Ares2ConfHistoryQuery;

import java.util.List;


/**
 * Manager for Ares2ConfHistory.
 */
public interface Ares2ConfHistoryManager {
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
    long insertSelective(Ares2ConfHistoryDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfHistoryDO> selectByQuery(Ares2ConfHistoryQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2ConfHistoryDO selectOneByQuery(Ares2ConfHistoryQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2ConfHistoryDO> selectByQuery(Ares2ConfHistoryQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2ConfHistoryDO> selectByQueryWithPage(Ares2ConfHistoryQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfHistoryDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2ConfHistoryDO record, Ares2ConfHistoryQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2ConfHistoryDO record, Ares2ConfHistoryQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfHistoryDO record);

    int insertBatch(List<Ares2ConfHistoryDO> records);
}