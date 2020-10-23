package com.cy.ares.api.rpc;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.NamespaceAdminService;
import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.dao.common.model.Ares2NamespaceDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${ares.ctx}/rpc/namespace")
public class RpcNamespaceController extends DefaultController {

    @Autowired
    private NamespaceAdminService namespaceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultMessage ncList() {
        return ok(namespaceService.listAll());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMessage ncAdd(@RequestBody Ares2NamespaceDO namespace) {
        namespaceService.insert(namespace);
        return ok();
    }

}
