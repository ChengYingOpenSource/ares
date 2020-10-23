package com.cy.ares.api.admin;

import com.cy.ares.dao.common.model.Ares2EnvDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.EnvAdminService;
import com.cy.ares.common.domain.ResultMessage;
import static com.cy.ares.biz.util.IdentityHelper.*;

@RestController
@RequestMapping("/${ares.ctx}/env")
public class EnvController extends DefaultController {

    @Autowired
    private EnvAdminService envService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultMessage envList(String namespaceCode) {
        return ok(envService.listAll(namespaceCode));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMessage envAdd(Ares2EnvDO env) {
        env.setCreateUserAccount(getCurrentUserAccount());
        env.setModifyUserAccount(getCurrentUserAccount());
        envService.insert(env);
        return ok();
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResultMessage envDel(String namespaceCode, String envCode) {
        //envService.deleteEnv(namespaceCode, envCode);
        return ok("防止误操作，暂不支持删除！");
    }

}
