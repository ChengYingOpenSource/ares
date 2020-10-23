package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.common.query.Ares2ConfQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2Conf.
 */
public interface Ares2ConfMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2ConfQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2ConfQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2ConfDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2ConfDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfDO> selectByQuery(Ares2ConfQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2ConfDO record, @Param("query") Ares2ConfQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2ConfDO record, @Param("query") Ares2ConfQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfDO record);
}