package com.cy.ares.dao.core.dal.mapper.ext;

import com.cy.ares.dao.core.dal.mapper.Ares2NamespaceMapper;

/**
 * MyBatis Ext Mapper for Ares2Namespace.
 */
public interface Ares2NamespaceExtMapper extends Ares2NamespaceMapper {

    int deleteNamespace(String namespaceCode);
}