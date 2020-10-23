package com.cy.onepush.dcommon.async;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.LoggerFactory;

public class RejectedThreadHandler implements RejectedExecutionHandler{

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RejectedThreadHandler.class);
    
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        
        r.run();
        
        
    }
}
