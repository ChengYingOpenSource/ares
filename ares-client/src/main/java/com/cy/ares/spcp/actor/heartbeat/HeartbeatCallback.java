package com.cy.ares.spcp.actor.heartbeat;

import com.cy.ares.spcp.context.RefreshContext;
import com.cy.ares.spcp.net.Callback;
import com.cy.ares.spcp.protocol.HeartbeatResp;

public class HeartbeatCallback implements Callback<HeartbeatResp> {

    @Override
    public void success(HeartbeatResp obj, RefreshContext contxt) {

    }

    @Override
    public void error(RefreshContext contxt, Exception e) {

    }

}
