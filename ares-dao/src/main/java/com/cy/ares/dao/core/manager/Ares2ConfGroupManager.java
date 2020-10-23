package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.common.query.Ares2ConfGroupQuery;

import java.util.List;


/**
 * Manager for Ares2ConfGroup.
 */
public interface Ares2ConfGroupManager {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2ConfGroupQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2ConfGroupQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2ConfGroupDO record);

    /**
     * insert selective.
     */
    long insertSelective(Ares2ConfGroupDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfGroupDO> selectByQuery(Ares2ConfGroupQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2ConfGroupDO selectOneByQuery(Ares2ConfGroupQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2ConfGroupDO> selectByQuery(Ares2ConfGroupQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2ConfGroupDO> selectByQueryWithPage(Ares2ConfGroupQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfGroupDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2ConfGroupDO record, Ares2ConfGroupQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2ConfGroupDO record, Ares2ConfGroupQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfGroupDO record);

    int exist(Ares2ConfGroupDO record);

    int deleteConfGroup(Ares2ConfGroupDO record);
}