package com.cy.ares.spcp.actor.cache;

import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;

public class CacheCompareReq extends NetRequest {
    static {
        ProtocolCenter.registe(Req.conf_compare, CacheCompareReq.class);
    }

    public CacheCompareReq() {
        super(ProtocolCenter.Req.conf_compare);
    }

    private String key;

}
