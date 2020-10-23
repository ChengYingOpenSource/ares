package com.cy.ares.spcp.protocol;
import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;


public class PushAck extends NetRequest {

    static {
        ProtocolCenter.registe(Req.conf_push_ack, PushAck.class);
    }
    
    public PushAck() {
        super(ProtocolCenter.Req.conf_push_ack);
    }
    
}
