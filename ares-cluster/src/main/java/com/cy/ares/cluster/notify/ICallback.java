package com.cy.ares.cluster.notify;

public interface ICallback<T> {
    
    void failed(T obj, Exception e);
    
    Object success(T obj)  ;
}
