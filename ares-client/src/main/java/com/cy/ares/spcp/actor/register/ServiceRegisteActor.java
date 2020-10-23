package com.cy.ares.spcp.actor.register;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.ares.spcp.client.ReceiverFuture;
import com.cy.ares.spcp.client.Sender;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.service.ClientServiceInfo;
import com.cy.ares.spcp.protocol.ServiceRegisteReq;
import com.cy.ares.spcp.protocol.ServiceRegisteResp;

/**
 * 注册+订阅; ok
 * 只注册、不订阅; ok
 * 不注册、只订阅; -> 只注册基本信息，而不注册服务信息； 剩下的和注册流程是一样的;
 * 主动查找 ; ok
 * @author maoxq
 */
public class ServiceRegisteActor {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRegisteActor.class);

    private ClientInstanceInfo clientInstanceInfo;
    private SpcpContext ctx;

    public ServiceRegisteActor(SpcpContext ctx, ClientInstanceInfo clientInstanceInfo) {
        this.clientInstanceInfo = clientInstanceInfo;
        this.ctx = ctx;
    }

    public boolean subscribe(ClientServiceInfo serviceInfo) {

        return true;
    }

    public boolean unSubscribe(ClientServiceInfo serviceInfo) {

        return true;
    }

    public boolean unregiste(ClientServiceInfo serviceInfo) {
        return  registe0(serviceInfo,ServiceRegisteReq.UN_REGISTE);
    }

    public boolean registe(ClientServiceInfo serviceInfo) {
        return  registe0(serviceInfo,ServiceRegisteReq.REGISTE);
    }

    private boolean registe0(ClientServiceInfo serviceInfo,String registeAction) {
        Sender sender = this.ctx.getSender();
        ServiceRegisteReq req = new ServiceRegisteReq();
        req.setInstanceInfo(clientInstanceInfo);
        req.setAction(registeAction);
        req.setServiceInfo(serviceInfo);
        ServiceRegisteResp resp = null;
        try {
            ReceiverFuture rf = sender.send(req);
            Event event = rf.get(10, TimeUnit.SECONDS);
            resp = (ServiceRegisteResp) MessageConvert.toResp(event);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        if(resp != null) {
            logger.info("registe success result={}", JSON.toJSONString(resp));
        }
        return true;
    }

    public ClientInstanceInfo getClientInstanceInfo() {
        return clientInstanceInfo;
    }

}
