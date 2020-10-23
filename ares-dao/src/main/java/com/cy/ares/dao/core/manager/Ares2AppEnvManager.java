package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.common.model.Ares2AppEnvDO;
import com.cy.ares.dao.common.query.Ares2AppEnvQuery;
import com.cy.ares.dao.util.PageResult;

import java.util.List;

/**
 * Ares2AppEnvManager
 *
 * @author: tirion.yy
 * @date: 2020/10/9
 * @description:
 */
public interface Ares2AppEnvManager {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2AppEnvQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2AppEnvQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2AppEnvDO record);

    /**
     * insert selective.
     */
    long insertSelective(Ares2AppEnvDO record);

    /**
     * select by query condition.
     */
    List<Ares2AppEnvDO> selectByQuery(Ares2AppEnvQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2AppEnvDO selectOneByQuery(Ares2AppEnvQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2AppEnvDO> selectByQuery(Ares2AppEnvQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2AppEnvDO> selectByQueryWithPage(Ares2AppEnvQuery query);

    /**
     * select by primary key.
     */
    Ares2AppEnvDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2AppEnvDO record, Ares2AppEnvQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2AppEnvDO record, Ares2AppEnvQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2AppEnvDO record);

    /**
     * 是否存在应用环境
     *
     * @param namespaceCode
     * @param appCode
     * @param envCode
     * @return
     */
    int exist(String namespaceCode, String appCode, String envCode);
}