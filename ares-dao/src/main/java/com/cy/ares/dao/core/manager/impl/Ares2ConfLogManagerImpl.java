package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfLogDO;
import com.cy.ares.dao.common.query.Ares2ConfLogQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2ConfLogExtMapper;
import com.cy.ares.dao.core.manager.Ares2ConfLogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Manager for Ares2ConfLog.
 */

@Component
public class Ares2ConfLogManagerImpl implements Ares2ConfLogManager{


    
    @Autowired
    protected Ares2ConfLogExtMapper ares2ConfLogExtMapper;
    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2ConfLogQuery query){
        return ares2ConfLogExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2ConfLogQuery query){
        return ares2ConfLogExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2ConfLogDO record){
        return ares2ConfLogExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2ConfLogDO record){
        return ares2ConfLogExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2ConfLogDO> selectByQuery(Ares2ConfLogQuery query){
        return ares2ConfLogExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2ConfLogDO selectOneByQuery(Ares2ConfLogQuery query){
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2ConfLogDO> topList = selectByQuery(query);
        if(topList != null && topList.size()>0 ){
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2ConfLogDO> selectByQuery(Ares2ConfLogQuery query,int size){
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
    */
    @Override
    public PageResult<Ares2ConfLogDO> selectByQueryWithPage(Ares2ConfLogQuery query) {
        PageResult<Ares2ConfLogDO> result = new PageResult<Ares2ConfLogDO>();
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
    public Ares2ConfLogDO selectByPrimaryKey(Long id){
        return ares2ConfLogExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective( Ares2ConfLogDO record,  Ares2ConfLogQuery query){
        return ares2ConfLogExtMapper.updateByQuerySelective(record,  query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery( Ares2ConfLogDO record,  Ares2ConfLogQuery query){

        return ares2ConfLogExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2ConfLogDO record){
        return ares2ConfLogExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertBatch(List<Ares2ConfLogDO> records) {
        return ares2ConfLogExtMapper.insertBatch(records);
    }
}