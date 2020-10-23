package com.cy.ares.dao.core.manager.impl;

import com.cy.ares.dao.util.PageResult;
import com.cy.ares.dao.common.model.Ares2MockHttpDO;
import com.cy.ares.dao.common.query.Ares2MockHttpQuery;
import com.cy.ares.dao.core.dal.mapper.ext.Ares2MockHttpExtMapper;
import com.cy.ares.dao.core.manager.Ares2MockHttpManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Manager for Ares2MockHttp.
 */

@Component
public class Ares2MockHttpManagerImpl implements Ares2MockHttpManager{


    
    @Autowired
    protected Ares2MockHttpExtMapper ares2MockHttpExtMapper;
    /**
     * query count by query condition.
     */
    @Override
    public int countByQuery(Ares2MockHttpQuery query){
        return ares2MockHttpExtMapper.countByQuery(query);
    }

    /**
     * delete by query condition.
     */
    @Override
    public int deleteByQuery(Ares2MockHttpQuery query){
        return ares2MockHttpExtMapper.deleteByQuery(query);
    }

    /**
     * delete by primary key.
     */
    @Override
    public int deleteByPrimaryKey(Ares2MockHttpDO record){
        return ares2MockHttpExtMapper.deleteByPrimaryKey(record);
    }

    /**
     * insert selective.
     */
    @Override
    public long insertSelective(Ares2MockHttpDO record){
        return ares2MockHttpExtMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<Ares2MockHttpDO> selectByQuery(Ares2MockHttpQuery query){
        return ares2MockHttpExtMapper.selectByQuery(query);
    }

    /**
     * select by query condition and top 1.
     */
    @Override
    public Ares2MockHttpDO selectOneByQuery(Ares2MockHttpQuery query){
        query.setPageNo(1);
        query.setPageSize(1);
        List<Ares2MockHttpDO> topList = selectByQuery(query);
        if(topList != null && topList.size()>0 ){
            return topList.get(0);
        }
        return null;
    }

    /**
     * select by query condition and top size.
     */
    @Override
    public List<Ares2MockHttpDO> selectByQuery(Ares2MockHttpQuery query,int size){
        query.setPageNo(1);
        query.setPageSize(size);
        return this.selectByQuery(query);
    }

    /**
     * select by query condition with page.
    */
    @Override
    public PageResult<Ares2MockHttpDO> selectByQueryWithPage(Ares2MockHttpQuery query) {
        PageResult<Ares2MockHttpDO> result = new PageResult<Ares2MockHttpDO>();
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
    public Ares2MockHttpDO selectByPrimaryKey(Long id){
        return ares2MockHttpExtMapper.selectByPrimaryKey(id);
    }

    /**
     * update by query condition selective.
     */
    @Override
    public int updateByQuerySelective( Ares2MockHttpDO record,  Ares2MockHttpQuery query){
        return ares2MockHttpExtMapper.updateByQuerySelective(record,  query);
    }

    /**
     * update by query condition.
     */
    @Override
    public int updateByQuery( Ares2MockHttpDO record,  Ares2MockHttpQuery query){

        return ares2MockHttpExtMapper.updateByQuery(record, query);
    }

    /**
     * update by primary key selective.
     */
    @Override
    public int updateByPrimaryKeySelective(Ares2MockHttpDO record){
        return ares2MockHttpExtMapper.updateByPrimaryKeySelective(record);
    }
}