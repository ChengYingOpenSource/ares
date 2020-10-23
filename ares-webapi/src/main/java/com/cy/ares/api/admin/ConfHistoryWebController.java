package com.cy.ares.api.admin;

import com.cy.ares.dao.common.model.Ares2ConfHistoryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.ConfHistoryAdminService;
import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.dao.model.dto.ConfPath;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/${ares.ctx}/conf/history")
public class ConfHistoryWebController extends DefaultController {
    
    @Autowired
    private ConfHistoryAdminService confHistoryAdminService;
    
    @RequestMapping(value = "/list/info", method = RequestMethod.POST)
    public ResultMessage historyInfo(ConfPath clusterPath,int pageNum,int pageSize) {
        PageInfo<Ares2ConfHistoryDO> pi = confHistoryAdminService.historyInfo(clusterPath,pageNum,pageSize);
        ResultMessage rm = ok(pi.getList());
        rm.setTotal(pi.getTotal());
        return rm;
    }
    
    @RequestMapping(value = "/dataItem/rollback", method = RequestMethod.POST)
    public ResultMessage rollback(long id) {
        OptionBO<?> op = confHistoryAdminService.rollback(id);
        return println(op);
    }
    
    
}
