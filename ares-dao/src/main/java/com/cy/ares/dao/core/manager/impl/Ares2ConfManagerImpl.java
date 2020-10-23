package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.common.query.Ares2ConfQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2ConfExtMapper;
import com.cy.ares.dao.core.manager.Ares2ConfManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Manager for Ares2Conf.
 */

@Component
public class Ares2ConfManagerImpl implements Ares2ConfManager {

    @Autowired
    protected Ares2ConfExtMapper ares2ConfExtMapper;

    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2ConfQuery query) {
        return ares2ConfExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2ConfQuery query) {
        return ares2ConfExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2ConfDO record) {
        return ares2ConfExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2ConfDO record) {
        return ares2ConfExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2ConfDO> selectByQuery(Ares2ConfQuery query) {
        return ares2ConfExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2ConfDO selectOneByQuery(Ares2ConfQuery query) {
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2ConfDO> topList = selectByQuery(query);
        if (topList != null && topList.size() > 0) {
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2ConfDO> selectByQuery(Ares2ConfQuery query, int size) {
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
     */
    @Override
    public PageResult<Ares2ConfDO> selectByQueryWithPage(Ares2ConfQuery query) {
        PageResult<Ares2ConfDO> result = new PageResult<Ares2ConfDO>();
        result.setPageSize(query.getPageSize());
        result.setPageNo(query.getPageNo());
        result.setTotalCount(this.countByQuery(query));
        result.setResult(this.selectByQuery(query));
        return result;
    }

    /**
     * select by primary key.
     */
    @Override
    public Ares2ConfDO selectByPrimaryKey(Long id) {
        return ares2ConfExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective(Ares2ConfDO record, Ares2ConfQuery query) {
        return ares2ConfExtMapper.updateByQuerySelective(record, query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery(Ares2ConfDO record, Ares2ConfQuery query) {

        return ares2ConfExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2ConfDO record) {
        return ares2ConfExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertBatch(List<Ares2ConfDO> records) {
        return ares2ConfExtMapper.insertBatch(records);
    }

    @Override
    public int replaceConf(List<Ares2ConfDO> list) {
        return ares2ConfExtMapper.replaceConf(list);
    }

    @Override
    public int updateContent(Ares2ConfDO conf) {
        return ares2ConfExtMapper.updateContent(conf);
    }

    @Override
    public List<Ares2ConfDO> findDataItemsWithSize(ConfPath groupPath) {
        return ares2ConfExtMapper.findDataItemsWithSize(groupPath);
    }

    @Override
    public int hasNotEqualRecord(ConfPath clusterPath, Date gmtModified) {
        Ares2ConfQuery query = new Ares2ConfQuery();
        Ares2ConfQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(clusterPath.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(clusterPath.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(clusterPath.getAppCode())) {
            criteria.andAppCodeEqualTo(clusterPath.getAppCode());
        }
        if (StringUtils.isNotBlank(clusterPath.getEnvCode())) {
            criteria.andEnvCodeEqualTo(clusterPath.getEnvCode());
        }
        if (StringUtils.isNotBlank(clusterPath.getClusterCode())) {
            criteria.andClusterCodeEqualTo(clusterPath.getClusterCode());
        }
        if (StringUtils.isNotBlank(clusterPath.getGroup())) {
            criteria.andGroupEqualTo(clusterPath.getGroup());
        }
        if (StringUtils.isNotBlank(clusterPath.getDataId())) {
            criteria.andDataIdEqualTo(clusterPath.getDataId());
        }
        criteria.andGmtModifiedNotEqualTo(gmtModified);
        query.setOrderByClause(" id desc");
        Ares2ConfDO ares2ConfDO = selectOneByQuery(query);
        if (ares2ConfDO == null) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<Ares2ConfDO> findByKeyDataIds(ConfPath clusterPath, List<String> dataIds) {
        return ares2ConfExtMapper.findByKeyDataIds(clusterPath, dataIds);
    }

    @Override
    public List<Ares2ConfDO> findByGroupKey(ConfPath groupPath) {
        Ares2ConfQuery query = new Ares2ConfQuery();
        Ares2ConfQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(groupPath.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(groupPath.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(groupPath.getAppCode())) {
            criteria.andAppCodeEqualTo(groupPath.getAppCode());
        }
        if (StringUtils.isNotBlank(groupPath.getEnvCode())) {
            criteria.andEnvCodeEqualTo(groupPath.getEnvCode());
        }
        if (StringUtils.isNotBlank(groupPath.getClusterCode())) {
            criteria.andClusterCodeEqualTo(groupPath.getClusterCode());
        }
        if (StringUtils.isNotBlank(groupPath.getGroup())) {
            criteria.andGroupEqualTo(groupPath.getGroup());
        }
        return selectByQuery(query);
    }
}