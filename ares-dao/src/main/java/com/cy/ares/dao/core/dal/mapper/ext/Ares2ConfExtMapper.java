package com.cy.ares.dao.core.dal.mapper.ext;

import java.util.List;

import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.core.dal.mapper.Ares2ConfMapper;
import com.cy.ares.dao.model.dto.ConfPath;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis Ext Mapper for Ares2Conf.
 */
public interface Ares2ConfExtMapper extends Ares2ConfMapper {

    int insertBatch(List<Ares2ConfDO> records);

    int replaceConf(List<Ares2ConfDO> list);

    int updateContent(Ares2ConfDO conf);

    List<Ares2ConfDO> findDataItemsWithSize(ConfPath groupPath);

    List<Ares2ConfDO> findByKeyDataIds(@Param("path")ConfPath clusterPath,@Param("list")List<String> dataIds);
}