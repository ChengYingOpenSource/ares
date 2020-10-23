package com.cy.ares.spcp.actor.fetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.protocol.DataItem;

public class LogListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(LogListener.class);

    @Override
    public void receive(DataItem item) {
        logger.info("logReceive dataItem curTime={},dataId={},action={},content={},gmtModified={}",
                System.currentTimeMillis(),item.getDataId(), item.getAction(),item.getContent(),item.getGmtModified().getTime());
        
    }
}
