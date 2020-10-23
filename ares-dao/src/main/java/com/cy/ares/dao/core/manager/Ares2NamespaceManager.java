package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2NamespaceDO;
import com.cy.ares.dao.common.query.Ares2NamespaceQuery;

import java.util.List;


/**
 * Manager for Ares2Namespace.
 */
public interface Ares2NamespaceManager {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2NamespaceQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2NamespaceQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2NamespaceDO record);

    /**
     * insert selective.
     */
    long insertSelective(Ares2NamespaceDO record);

    /**
     * select by query condition.
     */
    List<Ares2NamespaceDO> selectByQuery(Ares2NamespaceQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2NamespaceDO selectOneByQuery(Ares2NamespaceQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2NamespaceDO> selectByQuery(Ares2NamespaceQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2NamespaceDO> selectByQueryWithPage(Ares2NamespaceQuery query);

    /**
     * select by primary key.
     */
    Ares2NamespaceDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2NamespaceDO record, Ares2NamespaceQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2NamespaceDO record, Ares2NamespaceQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2NamespaceDO record);

    int deleteNamespace(String namespaceCode);

    int exist(String namespaceCode);
}