package com.cy.ares.spcp.client;

import com.cy.ares.spcp.context.RefreshContext;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.net.Callback;

public class NothingCallback implements Callback<Event> {

    public void success(Event obj, com.cy.ares.spcp.context.RefreshContext contxt) {

    }

    @Override
    public void error(RefreshContext contxt, Exception e) {

    }
}
