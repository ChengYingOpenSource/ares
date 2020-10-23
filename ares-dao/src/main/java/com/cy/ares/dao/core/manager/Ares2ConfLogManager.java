package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfLogDO;
import com.cy.ares.dao.common.query.Ares2ConfLogQuery;

import java.util.List;


/**
 * Manager for Ares2ConfLog.
 */
public interface Ares2ConfLogManager {
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
    long insertSelective(Ares2ConfLogDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfLogDO> selectByQuery(Ares2ConfLogQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2ConfLogDO selectOneByQuery(Ares2ConfLogQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2ConfLogDO> selectByQuery(Ares2ConfLogQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2ConfLogDO> selectByQueryWithPage(Ares2ConfLogQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfLogDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2ConfLogDO record, Ares2ConfLogQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2ConfLogDO record, Ares2ConfLogQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfLogDO record);

    int insertBatch(List<Ares2ConfLogDO> records);
}