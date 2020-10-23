package com.cy.ares.cluster.conf.push;

import lombok.Data;

@Data
public class PushAwatiAck {
    private String eventId;
    private long pushTime;
    private String instanceId;

    private boolean isAck = false;

    public PushAwatiAck(String eventId) {
        this.eventId = eventId;
        this.pushTime = System.currentTimeMillis();
    }

    public PushAwatiAck(String eventId, String instanceId) {
        this.eventId = eventId;
        this.instanceId = instanceId;
        this.pushTime = System.currentTimeMillis();
    }

    @Override
    public int hashCode() {
        return eventId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PushAwatiAck pa = (PushAwatiAck)obj;
        return this.eventId.equals(pa.getEventId());
    }

}
