package com.cy.ares.dao.core.dal.mapper.ext;

import java.util.List;

import com.cy.ares.dao.common.model.Ares2ConfLogDO;
import com.cy.ares.dao.core.dal.mapper.Ares2ConfLogMapper;

/**
 * MyBatis Ext Mapper for Ares2ConfLog.
 */
public interface Ares2ConfLogExtMapper extends Ares2ConfLogMapper {

    int insertBatch(List<Ares2ConfLogDO> records);
}