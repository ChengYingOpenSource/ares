package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2EnvDO;
import com.cy.ares.dao.common.query.Ares2EnvQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2Env.
 */
public interface Ares2EnvMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2EnvQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2EnvQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2EnvDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2EnvDO record);

    /**
     * select by query condition.
     */
    List<Ares2EnvDO> selectByQuery(Ares2EnvQuery query);

    /**
     * select by primary key.
     */
    Ares2EnvDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2EnvDO record, @Param("query") Ares2EnvQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2EnvDO record, @Param("query") Ares2EnvQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2EnvDO record);
}