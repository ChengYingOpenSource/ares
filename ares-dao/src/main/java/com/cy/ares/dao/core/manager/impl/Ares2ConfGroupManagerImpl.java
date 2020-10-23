package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.common.query.Ares2ConfGroupQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2ConfGroupExtMapper;
import com.cy.ares.dao.core.manager.Ares2ConfGroupManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Manager for Ares2ConfGroup.
 */

@Component
public class Ares2ConfGroupManagerImpl implements Ares2ConfGroupManager{


    
    @Autowired
    protected Ares2ConfGroupExtMapper ares2ConfGroupExtMapper;
    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2ConfGroupQuery query){
        return ares2ConfGroupExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2ConfGroupQuery query){
        return ares2ConfGroupExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2ConfGroupDO record){
        return ares2ConfGroupExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2ConfGroupDO record){
        return ares2ConfGroupExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2ConfGroupDO> selectByQuery(Ares2ConfGroupQuery query){
        return ares2ConfGroupExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2ConfGroupDO selectOneByQuery(Ares2ConfGroupQuery query){
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2ConfGroupDO> topList = selectByQuery(query);
        if(topList != null && topList.size()>0 ){
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2ConfGroupDO> selectByQuery(Ares2ConfGroupQuery query,int size){
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
    */
    @Override
    public PageResult<Ares2ConfGroupDO> selectByQueryWithPage(Ares2ConfGroupQuery query) {
        PageResult<Ares2ConfGroupDO> result = new PageResult<Ares2ConfGroupDO>();
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
    public Ares2ConfGroupDO selectByPrimaryKey(Long id){
        return ares2ConfGroupExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective( Ares2ConfGroupDO record,  Ares2ConfGroupQuery query){
        return ares2ConfGroupExtMapper.updateByQuerySelective(record,  query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery( Ares2ConfGroupDO record,  Ares2ConfGroupQuery query){

        return ares2ConfGroupExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2ConfGroupDO record){
        return ares2ConfGroupExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int exist(Ares2ConfGroupDO record) {
        Ares2ConfGroupQuery query = new Ares2ConfGroupQuery();
        Ares2ConfGroupQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(record.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(record.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(record.getAppCode())) {
            criteria.andAppCodeEqualTo(record.getAppCode());
        }
        if (StringUtils.isNotBlank(record.getEnvCode())) {
            criteria.andEnvCodeEqualTo(record.getEnvCode());
        }
        if (StringUtils.isNotBlank(record.getClusterCode())) {
            criteria.andClusterCodeEqualTo(record.getClusterCode());
        }
        if (StringUtils.isNotBlank(record.getGroup())) {
            criteria.andGroupEqualTo(record.getGroup());
        }
        if (StringUtils.isNotBlank(record.getGroupUid())) {
            criteria.andGroupUidEqualTo(record.getGroupUid());
        }
        query.setOrderByClause(" id desc");
        Ares2ConfGroupDO ares2ConfGroupDO = selectOneByQuery(query);
        if (ares2ConfGroupDO != null) {
            return Integer.valueOf(String.valueOf(ares2ConfGroupDO.getId()));
        }
        return 0;
    }

    @Override
    public int deleteConfGroup(Ares2ConfGroupDO record) {
        return ares2ConfGroupExtMapper.deleteConfGroup(record);
    }
}