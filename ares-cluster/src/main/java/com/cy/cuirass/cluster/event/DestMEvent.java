package com.cy.cuirass.cluster.event;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.cy.cuirass.cluster.AkNode;
import com.cy.onepush.dcommon.event.MEvent;


/**
 * 给固定dest 发送event & ack 返回
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年9月12日 上午11:21:09
 * @version V1.0
 */
public class DestMEvent extends MEvent{
    
    // mediator.tell(new DistributedPubSubMediator.Send("/user/destination", in, localAffinity), sender);
    // "akka.tcp://ClusterSystem@127.0.0.1:2551/user/destination",
    
    private String destAddress;
    
    private int destPort;
    
    private String clusterName;
    
    private String uuid = UUID.randomUUID().toString();
    
    private String sendAddress;
    
    private int sendPort;
    
    public DestMEvent(){}
    
    public DestMEvent(MEvent message){
        super(message.getEventName(), message.getEvent());
        this.setNamespace(message.getNamespace());
        this.setHead(message.getHead());
    }
    
    public DestMEvent(AkNode destNode,MEvent message){
        super(message.getEventName(), message.getEvent());
        this.setNamespace(message.getNamespace());
        this.setHead(message.getHead());
        this.destAddress = destNode.linkAddress();
        this.destPort = destNode.getPort();
    }
    
    
    public boolean hasDestAddress(){
        
        return StringUtils.isNotBlank(destAddress) && destPort>0;
        
    }
    
    public int addressHashCode(){
        
        String s = this.clusterName.concat(this.destAddress).concat(destPort+"");
        
        return s.hashCode();
    }
    
    

    public String getDestAddress() {
        return destAddress;
    }

    public DestMEvent setDestAddress(String destAddress) {
        this.destAddress = destAddress;
        return this;
    }

    public int getDestPort() {
        return destPort;
    }

    public DestMEvent setDestPort(int destPort) {
        this.destPort = destPort;
        return this;
    }

    public String getClusterName() {
        return clusterName;
    }

    public DestMEvent setClusterName(String clusterName) {
        this.clusterName = clusterName;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public DestMEvent setUuid(String uuid) {
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

    public DestMEvent setSendPort(int sendPort) {
        this.sendPort = sendPort;
        return this;
    }
    
    
}
