package com.cy.ares.cluster.client.instance;

import com.cy.ares.cluster.Coordinator;
import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.HeartbeatReq;
import com.cy.ares.spcp.protocol.ServiceRegisteReq;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class ClientChannelCenter extends Subscriber {

    private static final Logger logger = LoggerFactory.getLogger(ClientChannelCenter.class);

    private ResourceManager resourceManager = new ResourceManager();

    public ClientChannelCenter() {
        addEventName(Coordinator.CHANNEL_EXCEPTION);
        addEventName(Coordinator.CHANNEL_IDLE);
        addEventName(Coordinator.CHANNEL_ACTIVE);
        addEventName(Coordinator.CHANNEL_INACTIVE);

        // 同时监听 service registe
        addEventName(Req.service_registe);
        addEventName(Req.heart);
    }

    public Channel findByInstanceId(String instanceId) {
        return resourceManager.findChannel(instanceId);
    }

    public ClientInstanceInfo findInstanceInfo(String instanceId) {
        return resourceManager.findInstanceInfo(instanceId);
    }

    // appCode envCode clusterCode -> [instance1,2,3,4....]
    public List<String> findByKey(String key) {
        return resourceManager.findByKey(key);
    }

    @Override
    public MEvent action(MEvent event) {

        NetMEvent nme = (NetMEvent)event;
        String name = event.getEventName();
        Object obj = event.getEvent();
        // 服务注册; channel instanceId key
        if (Req.service_registe.equals(name)) {
            EventMsg.Event ee = (EventMsg.Event)obj;
            ServiceRegisteReq srr = (ServiceRegisteReq)MessageConvert.toReq(ee);
            resourceManager.serviceRegiste(srr, nme);
        } else if (Req.heart.equals(name)) {
            EventMsg.Event ee = (EventMsg.Event)obj;
            HeartbeatReq hbr = (HeartbeatReq)MessageConvert.toReq(ee);
            resourceManager.heartbeatReq(hbr, nme);
        }

        if (Coordinator.CHANNEL_ACTIVE.equals(name)) {
            Channel channel = nme.getChannel();
            resourceManager.channelActive(channel);
        } else if (Coordinator.CHANNEL_INACTIVE.equals(name)) {
            Channel channel = nme.getChannel();
            resourceManager.channelInActive(channel);
        } else if (Coordinator.CHANNEL_IDLE.equals(name)) {
            Channel channel = nme.getChannel();
            resourceManager.channelIdle(channel);
        } else if (Coordinator.CHANNEL_EXCEPTION.equals(name)) {
            Channel channel = nme.getChannel();
            resourceManager.channelException(channel);
        }

        return null;
    }

}
