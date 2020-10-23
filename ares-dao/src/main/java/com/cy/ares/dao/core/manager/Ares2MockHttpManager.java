package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2MockHttpDO;
import com.cy.ares.dao.common.query.Ares2MockHttpQuery;

import java.util.List;


/**
 * Manager for Ares2MockHttp.
 */
public interface Ares2MockHttpManager {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2MockHttpQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2MockHttpQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2MockHttpDO record);

    /**
     * insert selective.
     */
    long insertSelective(Ares2MockHttpDO record);

    /**
     * select by query condition.
     */
    List<Ares2MockHttpDO> selectByQuery(Ares2MockHttpQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2MockHttpDO selectOneByQuery(Ares2MockHttpQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2MockHttpDO> selectByQuery(Ares2MockHttpQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2MockHttpDO> selectByQueryWithPage(Ares2MockHttpQuery query);

    /**
     * select by primary key.
     */
    Ares2MockHttpDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2MockHttpDO record, Ares2MockHttpQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2MockHttpDO record, Ares2MockHttpQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2MockHttpDO record);
}