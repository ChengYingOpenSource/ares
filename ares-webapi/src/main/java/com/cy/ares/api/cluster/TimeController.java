package com.cy.ares.api.cluster;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.common.domain.ResultMessage;



@RestController
@RequestMapping("/${ares.cluster.ctx}/time")
public class TimeController extends DefaultController {
    
    
    @RequestMapping(value = "/acquire", method = RequestMethod.POST)
    public ResultMessage acquire() {
        return ok(new Date().getTime());
    }
    
}
