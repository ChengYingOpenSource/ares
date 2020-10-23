package com.cy.cuirass.cluster;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.cuirass.cluster.event.DestAckMEvent;
import com.cy.cuirass.cluster.event.DestMEvent;
import com.cy.cuirass.cluster.event.PubAckMEvent;
import com.cy.cuirass.cluster.event.PubMEvent;
import com.cy.onepush.dcommon.async.Async;
import com.cy.onepush.dcommon.event.LocalSubscriberRegistry;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;

public class AkSubscriber extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(AkSubscriber.class);

    private Async async;
    private LocalSubscriberRegistry registry;
    private AkCluster akCluster;

    public AkSubscriber(AkCluster akCluster, Async async, LocalSubscriberRegistry registry, String topic) {
        ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
        mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());

        this.async = async;
        this.registry = registry;
        this.akCluster = akCluster;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MEvent.class, msg -> {
            List<Subscriber> subscribers = this.registry.findSub(msg);
            if (subscribers == null) {
                logger.warn("AkSubscriber# no Subscriber find!,event={}", JSON.toJSONString(msg));
                return;
            }
            for (Subscriber sub : subscribers) {
                this.async.async(() -> {
                    try {
                        if (msg instanceof DestMEvent) {
                            DestMEvent event = (DestMEvent) msg;
                            MEvent object = sub.action(event);
                            DestAckMEvent et = new DestAckMEvent();
                            et.setDestAddress(event.getSendAddress());
                            et.setDestPort(event.getSendPort());
                            if (object != null) {
                                et.setEvent(object.getEvent());
                                et.setEventName(object.getEventName());
                                et.setHead(object.getHead());
                                et.setNamespace(object.getNamespace());
                            }
                            et.setUuid(event.getUuid());
                            et.setClusterName(this.akCluster.getClusterSysName());
                            this.akCluster.send(et);
                        } else if (msg instanceof PubMEvent) {
                            PubMEvent event = (PubMEvent) msg;
                            MEvent object = sub.action(event);
                            PubAckMEvent et = new PubAckMEvent();
                            et.setDestAddress(event.getSendAddress());
                            et.setDestPort(event.getSendPort());
                            if (object != null) {
                                et.setEvent(object.getEvent());
                                et.setEventName(object.getEventName());
                                et.setHead(object.getHead());
                                et.setNamespace(object.getNamespace());
                            }
                            et.setUuid(event.getUuid());
                            et.setClusterName(this.akCluster.getClusterSysName());
                            if (event.isShouldAck())
                                this.akCluster.send(et);

                        } else {
                            sub.action(msg);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            }
        }).match(DistributedPubSubMediator.SubscribeAck.class, msg -> {
            // subscribed ,订阅成功的 ack
            logger.info("subscribe ack !,msg={}", msg);
        }).build();
    }

}
