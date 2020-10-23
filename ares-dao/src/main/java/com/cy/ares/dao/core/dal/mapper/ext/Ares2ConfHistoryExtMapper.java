package com.cy.ares.dao.core.dal.mapper.ext;

import java.util.List;

import com.cy.ares.dao.common.model.Ares2ConfHistoryDO;
import com.cy.ares.dao.core.dal.mapper.Ares2ConfHistoryMapper;

/**
 * MyBatis Ext Mapper for Ares2ConfHistory.
 */
public interface Ares2ConfHistoryExtMapper extends Ares2ConfHistoryMapper {

    int insertBatch(List<Ares2ConfHistoryDO> records);
}