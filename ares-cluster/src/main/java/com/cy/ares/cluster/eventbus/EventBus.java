package com.cy.ares.cluster.eventbus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.cy.onepush.dcommon.event.LocalSubscriberRegistry;
import com.cy.onepush.dcommon.event.MEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.netty.util.AttributeKey;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * pub -> event -> EventBus=>Router(eventName->subList) -> subscribe
 *
 * @author maoxq
 * @version V1.0
 * @Description
 * @date 2019年5月8日 下午5:18:31
 */
public class EventBus {

    public static final AttributeKey<EventBus> key = AttributeKey.newInstance(EventBus.class.getSimpleName());

    private ActorSystem actorSystem;
    private LocalSubscriberRegistry pubSubRegister;
    private List<ActorRef> eventRouter;
    private AtomicInteger ai = new AtomicInteger(0);

    public EventBus(String busName, LocalSubscriberRegistry pubSubRegister) {

        // config2
        URL url = EventBus.class.getClassLoader().getResource("application-eventbus.conf");
        Config config2 = ConfigFactory.parseURL(url);
        // Create an Akka system
        Config configAres = config2.getConfig("ares").withFallback(ConfigFactory.load());
        this.actorSystem = ActorSystem.create(busName, configAres);

        // 创建router actor
        this.pubSubRegister = pubSubRegister;
        List<ActorRef> lr = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ActorRef ar =
                this.actorSystem.actorOf(Props.create(EventRouter.class, pubSubRegister), "Ares-EventRouter" + i);
            lr.add(ar);
        }
        this.eventRouter = lr;
    }

    /**
     * pub
     */
    public void publish(MEvent event) {
        // 1. 看是否有订阅者;
        // 2. EventRouter 并发执行;
        List list = this.pubSubRegister.findSub(event);
        if (list == null || list.isEmpty()) {
            return;
        }
        int nextInt = ThreadLocalRandom.current().nextInt();
        int index = Math.abs(nextInt) % 4;
        this.eventRouter.get(index).tell(event, ActorRef.noSender());
    }

    public LocalSubscriberRegistry getPubSubRegister() {
        return pubSubRegister;
    }

}
