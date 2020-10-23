package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2EnvDO;
import com.cy.ares.dao.common.query.Ares2EnvQuery;

import java.util.List;

/**
 * Manager for Ares2Env.
 */
public interface Ares2EnvManager {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2EnvQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2EnvQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2EnvDO record);

    /**
     * insert selective.
     */
    long insertSelective(Ares2EnvDO record);

    /**
     * select by query condition.
     */
    List<Ares2EnvDO> selectByQuery(Ares2EnvQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2EnvDO selectOneByQuery(Ares2EnvQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2EnvDO> selectByQuery(Ares2EnvQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2EnvDO> selectByQueryWithPage(Ares2EnvQuery query);

    /**
     * select by primary key.
     */
    Ares2EnvDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2EnvDO record, Ares2EnvQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2EnvDO record, Ares2EnvQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2EnvDO record);

    int deleteEnv(String namespaceCode, String envCode);

    List<Ares2EnvDO> listUnderApp(String namespaceCode, String appCode);

    int exist(String namespaceCode,String envCode);
}