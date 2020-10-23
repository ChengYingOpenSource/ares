package com.cy.ares.spcp.actor.fetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.context.RefreshContext;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.net.Callback;
import com.cy.ares.spcp.protocol.PushResp;

public class PushCallback implements Callback<PushResp> {
    
    private static final Logger logger = LoggerFactory.getLogger(PushCallback.class);
    
    @Override
    public void success(PushResp obj, RefreshContext context) {
        SpcpContext sc = (SpcpContext)context;
        sc.getConfFetchActor().trigger(obj);
    }
    
    @Override
    public void error(RefreshContext contxt, Exception e) {
        logger.error("PushCallback error!");
        logger.error(e.getMessage(),e);
    }

}
