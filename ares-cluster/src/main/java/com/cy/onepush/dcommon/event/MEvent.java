package com.cy.onepush.dcommon.event;
import java.util.UUID;

import lombok.Data;


@Data
public class MEvent {
    
    private String head;
    
    private Object event ;
    
    private String eventName;
    
    private String namespace;
    
    private String eventId;
    
    public MEvent(String eventName,String eventId){
        this.eventName = eventName;
        this.namespace = Subscriber.Default;
        this.eventId = eventId;
    }
    
    
    public MEvent(String eventName,Object event){
        this.eventName = eventName;
        this.event = event;
        this.namespace = Subscriber.Default;
        this.eventId = UUID.randomUUID().toString();
    }
    
    public MEvent(){
        this.namespace = Subscriber.Default;
        this.eventId = UUID.randomUUID().toString();
    }
    
    public MEvent(Object event){
        this.event = event;
        this.namespace = Subscriber.Default;
    }
    
}
