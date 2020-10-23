package com.cy.cuirass.cluster;

import com.cy.cuirass.cluster.event.PubMEvent;
import com.cy.onepush.dcommon.event.MEvent;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;

public class AkPublisher extends AbstractActor {

    // activate the extension
    private ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
    
    private AkCluster cluster;

    public AkPublisher(AkCluster cluster) {
        this.cluster = cluster;
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MEvent.class, in -> {
            
            ActorRef sender = getSender();
            if(sender == null || sender == ActorRef.noSender()){
                sender = getSelf();
            }
            if(in instanceof PubMEvent){
                PubMEvent message = (PubMEvent)in;
                message.setSendAddress(cluster.getLocalNode().linkAddress());
                message.setSendPort(cluster.getLocalNode().getPort());
            }
            
            String topic = in.getNamespace();
            mediator.tell(new DistributedPubSubMediator.Publish(topic, in), sender);
        }).build();
    }

}
