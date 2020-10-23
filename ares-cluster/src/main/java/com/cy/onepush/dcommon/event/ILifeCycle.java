package com.cy.onepush.dcommon.event;

public interface ILifeCycle {
    
    public void init()  ;
    
    public void destory();
    
    // 订阅 event
    public Subscriber listen();
}
