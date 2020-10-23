package com.cy.ares.cluster.notify;
import java.util.*;

import com.cy.onepush.dcommon.event.MEvent;



public class NotifyCommitAck extends MEvent {
    
    
    public NotifyCommitAck(String eventName,String evetnId) {
        super(eventName,evetnId);
    }
    
}

