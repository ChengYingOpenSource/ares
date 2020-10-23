package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.common.query.Ares2ConfGroupQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2ConfGroup.
 */
public interface Ares2ConfGroupMapper {
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
    int insertSelective(Ares2ConfGroupDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfGroupDO> selectByQuery(Ares2ConfGroupQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfGroupDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2ConfGroupDO record, @Param("query") Ares2ConfGroupQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2ConfGroupDO record, @Param("query") Ares2ConfGroupQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfGroupDO record);
}