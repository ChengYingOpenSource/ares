package com.cy.ares.cluster.eventbus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.onepush.dcommon.async.Async;
import com.cy.onepush.dcommon.event.LocalSubscriberRegistry;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;

import akka.actor.AbstractActor;

public class EventRouter extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(EventRouter.class);
    
    private LocalSubscriberRegistry pubSubRegister;
    private Async async ;
    
    public EventRouter(LocalSubscriberRegistry pubSubRegister) {
        this.pubSubRegister = pubSubRegister;
        this.async = new Async(8,32,100);
        // 4 ~ 16 threadPool
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MEvent.class, msg -> {
            List<Subscriber> subscribers = this.pubSubRegister.findSub(msg);
            // 并发执行; 
            for(Subscriber  sub:subscribers){
                this.async.async(()->{
                    try {
                        sub.action(msg);
                    } catch (Exception e) {
                        logger.error("eventName={},evetnId={},exception",msg.getEventName(),msg.getEventId());
                        logger.error(e.getMessage(),e);
                        if(msg instanceof NetMEvent){
                            try {
                                // TODO action 应该返回值， writeAndFlush 统一封装在这里返回, 或者 继承一个 RespNetSubscriber ，进行返回封装;
                                JSONObject js = new JSONObject();
                                js.put("name", ProtocolCenter.Error.system_error);
                                js.put("eventId", msg.getEventId());
                                js.put("header", "{}");
                                js.put("data", e.getMessage());
                                NetMEvent nm = (NetMEvent)msg;
                                if(nm.getChannel().isActive())
                                    nm.getChannel().writeAndFlush(JSON.toJSONString(js,SerializerFeature.WRITE_MAP_NULL_FEATURES));
                                else
                                    logger.error("return errorInfo channel is not active!");
                            } catch (Exception e2) {
                                logger.error(e2.getMessage(),e2);
                            }
                        }
                    }
                });
            }
        }).build();
    }
    
}