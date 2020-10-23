package com.cy.ares.api.cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.domain.ConfPushDTO;
import com.cy.ares.biz.cluster.conf.PushService;
import com.cy.ares.common.domain.BasicBO;
import com.cy.ares.common.domain.ResultMessage;


@RestController
@RequestMapping("/${ares.cluster.ctx}/push")
public class PushController extends DefaultController {
    
    @Autowired
    private PushService pushService;
    
    @RequestMapping(value = "/app/dataItem", method = RequestMethod.POST)
    public ResultMessage pushAppConf(ConfPushDTO confPush,BasicBO basicBO) {
        
        //boolean f = pushService.pushAppConf(confPush, basicBO);
        
        return fails("接口不可用,failed!");
    }
    
}
