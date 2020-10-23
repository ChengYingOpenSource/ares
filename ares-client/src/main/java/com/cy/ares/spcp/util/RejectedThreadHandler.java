package com.cy.ares.spcp.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedThreadHandler implements RejectedExecutionHandler{

    
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        
        try {
            // logger 
            r.run();
        } catch (Exception e) {
            
        }
        
    }
}
