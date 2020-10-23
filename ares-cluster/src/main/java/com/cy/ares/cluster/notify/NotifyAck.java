package com.cy.ares.cluster.notify;
import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.onepush.dcommon.event.MEvent;



public class NotifyAck extends MEvent {
    
    
    public NotifyAck(String eventName,String evetnId) {
        super(eventName,evetnId);
    }
}
