package com.cy.ares.cluster.registe;
import com.cy.ares.cluster.Coordinator;
import com.cy.ares.cluster.client.instance.ClientChannelCenter;
import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;
import com.cy.ares.spcp.protocol.ServiceRegisteReq;
import com.cy.ares.spcp.protocol.ServiceRegisteResp;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;

import io.netty.channel.Channel;


public class ServiceCoordinator extends Subscriber implements Coordinator {
    
    private ClientChannelCenter channelCenter;
    
    public ServiceCoordinator(ClientChannelCenter channelCenter) {
        addEventName(Req.service_registe);
        addEventName(Req.service_sub_fetch);
        
        addEventName(Resp.service_registe);
        addEventName(Resp.service_sub_fetch);
        this.channelCenter = channelCenter;
    }

    public void serviceRegiste(ServiceRegisteReq req,NetMEvent nme){
        
        Channel channel = nme.getChannel();
        
        ServiceRegisteResp resp = new ServiceRegisteResp();
        resp.setEventId(req.getEventId());
        
        channel.writeAndFlush(MessageConvert.toEvent(resp));
    }
    
    
    /**
     * 内部消息的进入口; cluster同步的信息;
     */
    @Override
    public MEvent action(MEvent event) {
        // serverHandler -> EventBus -> messageIn
        String eName = event.getEventName();
        
        String name = event.getEventName();
        Object obj = event.getEvent();
        if (Req.service_registe.equals(name)) {
            EventMsg.Event ee = (EventMsg.Event) obj;
            ServiceRegisteReq req = (ServiceRegisteReq) MessageConvert.toReq(ee);
            NetMEvent nme = (NetMEvent) event;
            serviceRegiste(req, nme);
        } else if (Req.service_sub_fetch.equals(name)) {
            NetMEvent nme = (NetMEvent) event;
        } 
        return null;
        
    }
    
}