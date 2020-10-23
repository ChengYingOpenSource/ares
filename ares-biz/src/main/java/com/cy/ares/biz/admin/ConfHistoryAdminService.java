package com.cy.ares.biz.admin;

import java.util.List;

import com.cy.ares.dao.common.model.Ares2ConfHistoryDO;
import com.cy.ares.dao.common.query.Ares2ConfHistoryQuery;
import com.cy.ares.dao.core.manager.Ares2ConfHistoryManager;
import com.cy.ares.dao.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.dao.model.dto.ConfPath;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ConfHistoryAdminService {

    private static final Logger logger = LoggerFactory.getLogger(ConfHistoryAdminService.class);

    @Autowired
    private Ares2ConfHistoryManager ares2ConfHistoryManager;
    
    @Autowired
    private ConfWebAdminService confWebAdminService;
    
    public PageInfo<Ares2ConfHistoryDO> historyInfo(ConfPath clusterPath,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Ares2ConfHistoryQuery query = new Ares2ConfHistoryQuery();
        Ares2ConfHistoryQuery.Criteria criteria = query.createCriteria();
        if(StringUtils.isNotBlank(clusterPath.getNamespaceCode())){
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
        query.setOrderByClause(" id desc");
        query.setPageNo(pageNum);
        query.setPageSize(pageSize);
        PageResult<Ares2ConfHistoryDO> pageResult = ares2ConfHistoryManager.selectByQueryWithPage(query);
        List<Ares2ConfHistoryDO> list = pageResult.getResult();
        PageInfo<Ares2ConfHistoryDO> pi = new PageInfo<>(list);
        pi.setTotal(pageResult.getTotalCount());
        return pi;
    }
    
    
    public OptionBO<?> rollback(long id){
        Ares2ConfHistoryDO confHistoryDO = ares2ConfHistoryManager.selectByPrimaryKey(id);
        if(confHistoryDO == null) {
            OptionBO.fail("记录不存在！");
        }
        boolean t = confWebAdminService.rollbackDataItem(confHistoryDO);
        if(t){
            return OptionBO.ok("还原成功！");
        }else{
            return OptionBO.fail("还原失败!");
        }
    }
}
