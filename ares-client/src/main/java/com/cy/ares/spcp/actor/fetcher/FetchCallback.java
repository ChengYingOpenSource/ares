package com.cy.ares.spcp.actor.fetcher;

import com.cy.ares.spcp.context.RefreshContext;
import com.cy.ares.spcp.net.Callback;
import com.cy.ares.spcp.protocol.FetchResp;

public class FetchCallback implements Callback<FetchResp> {

    @Override
    public void success(FetchResp obj, RefreshContext contxt) {
        
    }

    @Override
    public void error(RefreshContext contxt, Exception e) {
        
    }

}
