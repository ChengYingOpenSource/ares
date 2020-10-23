package com.cy.ares.api.admin;

import com.cy.ares.dao.common.model.Ares2NamespaceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.NamespaceAdminService;
import com.cy.ares.common.domain.ResultMessage;
import static com.cy.ares.biz.util.IdentityHelper.*;

@RestController
@RequestMapping("/${ares.ctx}/namespace")
public class NamespaceController extends DefaultController {

    @Autowired
    private NamespaceAdminService namespaceService;
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultMessage ncList() {
        return ok(namespaceService.listAll());
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMessage ncAdd(Ares2NamespaceDO namespace) {
        namespace.setCreateUserAccount(getCurrentUserAccount());
        namespace.setModifyUserAccount(getCurrentUserAccount());
        namespaceService.insert(namespace);
        return ok();
    }
    
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResultMessage ncDel(String namespaceCode) {
        //namespaceService.deleteNamespace(namespaceCode);
        return ok("防止误操作，暂不支持删除！");
    }

}
