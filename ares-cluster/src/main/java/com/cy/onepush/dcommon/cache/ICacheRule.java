package com.cy.onepush.dcommon.cache;

import com.cy.onepush.dcommon.event.ILifeCycle;

public interface ICacheRule extends ILifeCycle{
    
    public boolean flushAll() ;
    
    public Object flushOne(Object t);
    
    public void remove(Object t);
    
}
