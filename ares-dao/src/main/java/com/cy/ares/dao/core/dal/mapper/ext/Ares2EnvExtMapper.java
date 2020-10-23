package com.cy.ares.dao.core.dal.mapper.ext;

import java.util.List;

import com.cy.ares.dao.common.model.Ares2EnvDO;
import com.cy.ares.dao.core.dal.mapper.Ares2EnvMapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Ext Mapper for Ares2Env.
 */
public interface Ares2EnvExtMapper extends Ares2EnvMapper {

    int deleteEnv(@Param("namespaceCode")String namespaceCode,@Param("envCode")String envCode);

    List<Ares2EnvDO> listUnderApp(@Param("namespaceCode")String namespaceCode,@Param("appCode")String appCode);

}