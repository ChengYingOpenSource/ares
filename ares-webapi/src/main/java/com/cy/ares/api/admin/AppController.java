package com.cy.ares.api.admin;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.AppAdminService;
import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.dao.common.model.Ares2AppDO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static com.cy.ares.biz.util.IdentityHelper.getCurrentUserAccount;

@RestController
@RequestMapping("/${ares.ctx}/app")
public class AppController extends DefaultController {

    @Autowired
    private AppAdminService appService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultMessage appList(@NotNull  String namespaceCode, Integer pageNum, Integer pageSize) {
        pageSize = pageSize == null ? 20 : pageSize;
        pageNum = pageNum == null ? 1 : pageNum;
        PageInfo<Ares2AppDO> pi = appService.listPage(namespaceCode, pageNum, pageSize);
        ResultMessage rm = ok(pi.getList());
        rm.setTotal(pi.getTotal());
        return rm;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMessage appAdd(Ares2AppDO ares2AppDO) {
        ares2AppDO.setCreateUserAccount(getCurrentUserAccount());
        ares2AppDO.setModifyUserAccount(getCurrentUserAccount());
        appService.insert(ares2AppDO);
        return ok();
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResultMessage appDel(String namespaceCode, String appCode) {
        appService.deleteApp(namespaceCode, appCode);
        return ok();
    }

    @RequestMapping(value = "/bind/env", method = RequestMethod.POST)
    public ResultMessage bindEnv(String namespaceCode, String appCode, String envCode, String clusterCode) {
        OptionBO ob = appService.bindEnv(namespaceCode, appCode, envCode, clusterCode);
        if (!ob.isSuccess()) {
            return fails(ob.getMsg());
        }
        return ok();
    }

}
