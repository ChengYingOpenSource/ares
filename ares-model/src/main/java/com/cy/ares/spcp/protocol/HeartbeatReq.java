package com.cy.ares.spcp.protocol;

import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;

public class HeartbeatReq extends NetRequest {

    static {
        ProtocolCenter.registe(Req.heart, HeartbeatReq.class);
    }

    public HeartbeatReq() {
        super(Req.heart);
    }

    // 心跳间隔次数
    private int interval;

    private ClientInstanceInfo clientInstanceInfo ;

    private long currentTime;

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public ClientInstanceInfo getClientInstanceInfo() {
        return clientInstanceInfo;
    }

    public void setClientInstanceInfo(ClientInstanceInfo clientInstanceInfo) {
        this.clientInstanceInfo = clientInstanceInfo;
    }

}
