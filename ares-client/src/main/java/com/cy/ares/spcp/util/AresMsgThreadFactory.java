package com.cy.ares.spcp.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class AresMsgThreadFactory implements ThreadFactory{

    
    private String prefix;
    
    public AresMsgThreadFactory(String prefix) {
        this.prefix = prefix;
    }
    
    private AtomicLong index = new AtomicLong(0);
    
    @Override
    public Thread newThread(Runnable r) {
        
        Thread t = new Thread( r, this.prefix + index.getAndIncrement());
        
        return t;
    }
}
