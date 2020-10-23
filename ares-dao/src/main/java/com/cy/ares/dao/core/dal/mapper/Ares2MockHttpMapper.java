package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2MockHttpDO;
import com.cy.ares.dao.common.query.Ares2MockHttpQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2MockHttp.
 */
public interface Ares2MockHttpMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2MockHttpQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2MockHttpQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2MockHttpDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2MockHttpDO record);

    /**
     * select by query condition.
     */
    List<Ares2MockHttpDO> selectByQuery(Ares2MockHttpQuery query);

    /**
     * select by primary key.
     */
    Ares2MockHttpDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2MockHttpDO record, @Param("query") Ares2MockHttpQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2MockHttpDO record, @Param("query") Ares2MockHttpQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2MockHttpDO record);
}