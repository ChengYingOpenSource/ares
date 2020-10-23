package com.cy.cuirass.cluster.asyn;
import java.util.concurrent.Callable;



public class HolderCallable<V> implements Callable<V> {
    
    private V result ;

    private Callable<V> callable;
    
    public HolderCallable<V> setResult(V result) {
        this.result = result;
        return this;
    }
    
    
    public HolderCallable(Callable<V> callable){
        this.callable = callable;
    }
    
    
    @Override
    public V call() throws Exception {
        
        V t = callable.call();
        if(result == null){
            return t;
        }
        return result;
    }
    
}
