package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.common.query.Ares2AppQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2AppExtMapper;
import com.cy.ares.dao.core.manager.Ares2AppManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Manager for Ares2App.
 */

@Component
public class Ares2AppManagerImpl implements Ares2AppManager{
    
    @Autowired
    protected Ares2AppExtMapper ares2AppExtMapper;
    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2AppQuery query){
        return ares2AppExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2AppQuery query){
        return ares2AppExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2AppDO record){
        return ares2AppExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2AppDO record){
        return ares2AppExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2AppDO> selectByQuery(Ares2AppQuery query){
        return ares2AppExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2AppDO selectOneByQuery(Ares2AppQuery query){
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2AppDO> topList = selectByQuery(query);
        if(topList != null && topList.size()>0 ){
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2AppDO> selectByQuery(Ares2AppQuery query,int size){
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
    */
    @Override
    public PageResult<Ares2AppDO> selectByQueryWithPage(Ares2AppQuery query) {
        PageResult<Ares2AppDO> result = new PageResult<Ares2AppDO>();
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
    public Ares2AppDO selectByPrimaryKey(Long id){
        return ares2AppExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective( Ares2AppDO record,  Ares2AppQuery query){
        return ares2AppExtMapper.updateByQuerySelective(record,  query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery( Ares2AppDO record,  Ares2AppQuery query){

        return ares2AppExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2AppDO record){
        return ares2AppExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteApp(Ares2AppDO record) {
        return ares2AppExtMapper.deleteApp(record);
    }
}