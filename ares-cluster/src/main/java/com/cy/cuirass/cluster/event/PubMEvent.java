package com.cy.cuirass.cluster.event;
import java.util.UUID;

import com.cy.onepush.dcommon.event.MEvent;


/**
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年9月12日 上午11:21:09
 * @version V1.0
 */
public class PubMEvent extends MEvent{
    
    // mediator.tell(new DistributedPubSubMediator.Send("/user/destination", in, localAffinity), sender);
    // "akka.tcp://ClusterSystem@127.0.0.1:2551/user/destination",
    
    private String clusterName;
    
    private String uuid = UUID.randomUUID().toString();
    
    private String sendAddress;
    
    private int sendPort;
    
    private boolean shouldAck = true;
    
    public PubMEvent(){}
    
    public PubMEvent(MEvent message){
        super(message.getEventName(), message.getEvent());
        this.setNamespace(message.getNamespace());
        this.setHead(message.getHead());
    }
    
    public PubMEvent(MEvent message,boolean shouldAck){
        super(message.getEventName(), message.getEvent());
        this.setNamespace(message.getNamespace());
        this.setHead(message.getHead());
        this.shouldAck = shouldAck;
    }
    
    public boolean isShouldAck() {
        return shouldAck;
    }

    public void setShouldAck(boolean shouldAck) {
        this.shouldAck = shouldAck;
    }

    public String getClusterName() {
        return clusterName;
    }

    public PubMEvent setClusterName(String clusterName) {
        this.clusterName = clusterName;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public PubMEvent setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    
    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public int getSendPort() {
        return sendPort;
    }

    public PubMEvent setSendPort(int sendPort) {
        this.sendPort = sendPort;
        return this;
    }
    
    
}
