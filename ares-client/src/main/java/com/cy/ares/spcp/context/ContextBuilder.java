package com.cy.ares.spcp.context;

import com.cy.ares.spcp.actor.RespActor;
import com.cy.ares.spcp.actor.fetcher.ConfigFetchActor;
import com.cy.ares.spcp.actor.register.ServiceRegisteActor;
import com.cy.ares.spcp.actor.timer.TimerCacheCompare;
import com.cy.ares.spcp.actor.timer.TimerHeartbeatCheck;
import com.cy.ares.spcp.client.Receiver;
import com.cy.ares.spcp.client.Sender;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;

/***
 * @author chungui.wcg
 *
 *
 *  private ServiceRegisteActor serviceRegisteActor;
 *     private Receiver receiver;
 *     private Sender sender;
 *
 *         private TimerHeartbeatCheck hbcheck;
 *     private TimerCacheCompare cacheCompare;
 *
 *     // actor
 *     private ConfigFetchActor confFetchActor;
 *
 *     TODO make these compotent abstrct
 * */
public class ContextBuilder {

    SpcpContext spcpContext = new SpcpContext();

    public SpcpContext build(SpcpConfig conf, ClientInstanceInfo instanceInfo) {

        spcpContext.init(conf, instanceInfo);
        return spcpContext;
    }

    public ContextBuilder RespActor(RespActor respActor) {
        spcpContext.respActor(respActor);
        return this;
    }

    public ContextBuilder serviceRegisteActor(ServiceRegisteActor serviceRegisteActor) {
        spcpContext.serviceRegisteActor(serviceRegisteActor);
        return this;
    }

    public ContextBuilder receiver(Receiver receiver) {
        spcpContext.receiver(receiver);
        return this;
    }

    public ContextBuilder sender(Sender sender) {
        spcpContext.sender(sender);
        return this;
    }

    public ContextBuilder timerCacheCompare(TimerCacheCompare timerCacheCompare) {
        spcpContext.timerCacheCompare(timerCacheCompare);
        return this;
    }

    public ContextBuilder timerHeartbeatCheck(TimerHeartbeatCheck timerHeartbeatCheck) {
        spcpContext.timerHeartbeatCheck(timerHeartbeatCheck);
        return this;
    }

    public ContextBuilder confFetchActor(ConfigFetchActor configFetchActor) {
        spcpContext.configFetchActor(configFetchActor);
        return this;
    }

}
