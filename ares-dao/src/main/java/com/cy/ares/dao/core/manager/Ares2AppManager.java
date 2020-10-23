package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.common.query.Ares2AppQuery;

import java.util.List;


/**
 * Manager for Ares2App.
 */
public interface Ares2AppManager {
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
    long insertSelective(Ares2AppDO record);

    /**
     * select by query condition.
     */
    List<Ares2AppDO> selectByQuery(Ares2AppQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2AppDO selectOneByQuery(Ares2AppQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2AppDO> selectByQuery(Ares2AppQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2AppDO> selectByQueryWithPage(Ares2AppQuery query);

    /**
     * select by primary key.
     */
    Ares2AppDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2AppDO record, Ares2AppQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2AppDO record, Ares2AppQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2AppDO record);

    int deleteApp(Ares2AppDO record);
}