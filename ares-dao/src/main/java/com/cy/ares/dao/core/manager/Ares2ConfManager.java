package com.cy.ares.dao.core.manager;

import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.common.query.Ares2ConfQuery;

import java.util.Date;
import java.util.List;


/**
 * Manager for Ares2Conf.
 */
public interface Ares2ConfManager {
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
    long insertSelective(Ares2ConfDO record);

    /**
     * select by query condition.
     */
    List<Ares2ConfDO> selectByQuery(Ares2ConfQuery query);

    /**
     * select by query condition and top 1.
     */
    Ares2ConfDO selectOneByQuery(Ares2ConfQuery query);

    /**
     * select by query condition and top size.
     */
    List<Ares2ConfDO> selectByQuery(Ares2ConfQuery query, int size);

    /**
     * select by query condition with page.
     */
    PageResult<Ares2ConfDO> selectByQueryWithPage(Ares2ConfQuery query);

    /**
     * select by primary key.
     */
    Ares2ConfDO selectByPrimaryKey(Long id);

    /**
     * update by query condition selective.
     */
    int updateByQuerySelective(Ares2ConfDO record, Ares2ConfQuery query);

    /**
     * update by query condition.
     */
    int updateByQuery(Ares2ConfDO record, Ares2ConfQuery query);

    /**
     * update by primary key selective.
     */
    int updateByPrimaryKeySelective(Ares2ConfDO record);

    int insertBatch(List<Ares2ConfDO> records);

    int replaceConf(List<Ares2ConfDO> list);

    int updateContent(Ares2ConfDO conf);

    List<Ares2ConfDO> findDataItemsWithSize(ConfPath groupPath);

    int hasNotEqualRecord(ConfPath clusterPath,Date gmtModified);

    List<Ares2ConfDO> findByKeyDataIds(ConfPath clusterPath,List<String> dataIds);

    List<Ares2ConfDO> findByGroupKey(ConfPath groupPath);
}