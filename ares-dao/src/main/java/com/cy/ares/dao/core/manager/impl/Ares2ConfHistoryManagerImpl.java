package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2ConfHistoryDO;
import com.cy.ares.dao.common.query.Ares2ConfHistoryQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2ConfHistoryExtMapper;
import com.cy.ares.dao.core.manager.Ares2ConfHistoryManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Manager for Ares2ConfHistory.
 */

@Component
public class Ares2ConfHistoryManagerImpl implements Ares2ConfHistoryManager{


    
    @Autowired
    protected Ares2ConfHistoryExtMapper ares2ConfHistoryExtMapper;
    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2ConfHistoryQuery query){
        return ares2ConfHistoryExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2ConfHistoryQuery query){
        return ares2ConfHistoryExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2ConfHistoryDO record){
        return ares2ConfHistoryExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2ConfHistoryDO record){
        return ares2ConfHistoryExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2ConfHistoryDO> selectByQuery(Ares2ConfHistoryQuery query){
        return ares2ConfHistoryExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2ConfHistoryDO selectOneByQuery(Ares2ConfHistoryQuery query){
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2ConfHistoryDO> topList = selectByQuery(query);
        if(topList != null && topList.size()>0 ){
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2ConfHistoryDO> selectByQuery(Ares2ConfHistoryQuery query,int size){
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
    */
    @Override
    public PageResult<Ares2ConfHistoryDO> selectByQueryWithPage(Ares2ConfHistoryQuery query) {
        PageResult<Ares2ConfHistoryDO> result = new PageResult<Ares2ConfHistoryDO>();
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
    public Ares2ConfHistoryDO selectByPrimaryKey(Long id){
        return ares2ConfHistoryExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective( Ares2ConfHistoryDO record,  Ares2ConfHistoryQuery query){
        return ares2ConfHistoryExtMapper.updateByQuerySelective(record,  query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery( Ares2ConfHistoryDO record,  Ares2ConfHistoryQuery query){

        return ares2ConfHistoryExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2ConfHistoryDO record){
        return ares2ConfHistoryExtMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertBatch(List<Ares2ConfHistoryDO> records) {
        return ares2ConfHistoryExtMapper.insertBatch(records);
    }
}