package com.cy.ares.dao.core.dal.mapper.ext;

import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.core.dal.mapper.Ares2ConfGroupMapper;

/**
 * MyBatis Ext Mapper for Ares2ConfGroup.
 */
public interface Ares2ConfGroupExtMapper extends Ares2ConfGroupMapper {

    int deleteConfGroup(Ares2ConfGroupDO record);
}