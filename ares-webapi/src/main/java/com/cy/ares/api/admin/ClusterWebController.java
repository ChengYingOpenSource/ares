package com.cy.ares.api.admin;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.ClusterAdminService;
import com.cy.ares.biz.admin.domain.ClusterCopyDTO;
import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.common.domain.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/10/2 12:49
 */
@RestController
@RequestMapping("/${ares.ctx}/cluster")
public class ClusterWebController extends DefaultController {

    @Autowired
    private ClusterAdminService clusterAdminService;

    /**
     * 选择某个组，如何进行导入，导入从其它环境下的某个组进行导入;
     * @return
     */
    @RequestMapping(value = "/conf/copy", method = RequestMethod.POST)
    public ResultMessage clusterCopy(ClusterCopyDTO clusterCopy) {
        OptionBO optionBO = clusterAdminService.clusterCopy(clusterCopy);
        return  ResultMessage.build(optionBO.isSuccess(),optionBO.getMsg(),optionBO.getData());
    }

}
