package com.cy.ares.dao.core.dal.mapper.ext;

import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.core.dal.mapper.Ares2AppMapper;

/**
 * MyBatis Ext Mapper for Ares2App.
 */
public interface Ares2AppExtMapper extends Ares2AppMapper {

    int deleteApp(Ares2AppDO ares2AppDO);
}