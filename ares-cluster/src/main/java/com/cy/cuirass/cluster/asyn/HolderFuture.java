package com.cy.cuirass.cluster.asyn;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;



public class HolderFuture<V> extends FutureTask<V>{
    
    private HolderCallable<V> hcCallable ;
    
    public HolderFuture(HolderCallable<V> callable) {
        super(callable);
        this.hcCallable = callable;
    }
    
    public HolderFuture(Callable<V> callable) {
        super(callable);
    }
    
    public HolderFuture(Runnable runnable, V result) {
       super(runnable, result);
    }

    public HolderCallable<V> getHcCallable() {
        return hcCallable;
    }
    
    
}
