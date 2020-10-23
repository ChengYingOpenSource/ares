package com.cy.onepush.dcommon.async;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class DefThreadFactory implements ThreadFactory{

    
    private String prefix;
    
    public DefThreadFactory(String prefix) {
        this.prefix = prefix;
    }
    
    private AtomicLong index = new AtomicLong(0);
    
    @Override
    public Thread newThread(Runnable r) {
        
        Thread t = new Thread( r, this.prefix + index.getAndIncrement());
        
        return t;
    }
}
