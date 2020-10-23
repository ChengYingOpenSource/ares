package com.cy.onepush.dcommon.event;
import java.util.*;



public abstract class EventEmitter {
    
    private List<BusNode> nodes;
    
    public EventEmitter(List<BusNode> nodes){
        this.nodes = nodes;
    }
    
    public EventEmitter(){}
    
    public void emit(String namespace,MEvent event){
        event.setNamespace(Subscriber.Default);
        emit(event);
    }
    
    public void emit(String eventName,Object eventData){
        MEvent mEvent = new MEvent();
        mEvent.setEventName(eventName);
        mEvent.setEvent(eventData);
        mEvent.setNamespace(Subscriber.Default);
        emit(mEvent);
    }
    
    
    public abstract void emit(MEvent event) ;
    
    
}




