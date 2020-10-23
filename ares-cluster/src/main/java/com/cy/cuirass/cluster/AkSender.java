package com.cy.cuirass.cluster;

import org.apache.commons.lang3.StringUtils;

import com.cy.cuirass.cluster.event.DestMEvent;
import com.cy.onepush.dcommon.event.MEvent;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;

public class AkSender extends AbstractActor {

    // activate the extension
    private ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

    private AkCluster cluster;

    public AkSender(AkCluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MEvent.class, in -> {

            String path = "/user/destination";
            if (in instanceof DestMEvent) {
                DestMEvent t = (DestMEvent) in;
                if (StringUtils.isBlank(t.getClusterName())) {
                    t.setClusterName(cluster.getClusterSysName());
                }
                if (t.hasDestAddress()) {
                    path = path + "-unique" + t.addressHashCode();
                }
                t.setSendAddress(cluster.getLocalNode().linkAddress());
                t.setSendPort(cluster.getLocalNode().getPort());
            }

            boolean localAffinity = false;
            ActorRef sender = getSender();
            if (sender == null || sender == ActorRef.noSender()) {
                sender = getSelf();
            }

            mediator.tell(new DistributedPubSubMediator.Send(path, in, localAffinity), sender);

        }).build();
    }

}
