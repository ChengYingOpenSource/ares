package com.cy.ares.dao.core.dal.mapper;

import com.cy.ares.dao.common.model.Ares2AppEnvDO;
import com.cy.ares.dao.common.query.Ares2AppEnvQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Mapper for Ares2AppEnv.
 */
public interface Ares2AppEnvMapper {
    /**
     * query count by query condition.
     */
    int countByQuery(Ares2AppEnvQuery query);

    /**
     * delete by query condition.
     */
    int deleteByQuery(Ares2AppEnvQuery query);

    /**
     * delete by primary key.
     */
    int deleteByPrimaryKey(Ares2AppEnvDO record);

    /**
     * insert selective.
     */
    int insertSelective(Ares2AppEnvDO record);

    /**
     * select by query condition.
     */
    List<Ares2AppEnvDO> selectByQuery(Ares2AppEnvQuery query);

    /**
     * select by primary key.
     */
    Ares2AppEnvDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(@Param("record") Ares2AppEnvDO record, @Param("query") Ares2AppEnvQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(@Param("record") Ares2AppEnvDO record, @Param("query") Ares2AppEnvQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2AppEnvDO record);
}