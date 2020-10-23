package com.cy.cuirass.cluster.asyn;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class HolderCounterFuture<V> extends FutureTask<V> {

    private HolderCallable<V> hcCallable;
    private AtomicInteger ai;

    public HolderCounterFuture(HolderCallable<V> callable, AtomicInteger ai) {
        super(callable);
        this.hcCallable = callable;
        this.ai = ai;
    }

    public HolderCounterFuture(Callable<V> callable) {
        super(callable);
    }

    public HolderCounterFuture(Runnable runnable, V result) {
        super(runnable, result);
    }

    public HolderCallable<V> getHcCallable() {
        return hcCallable;
    }

    public boolean decrement() {
        if (ai.intValue() <= 0) {
            return true;
        }
        int a = ai.decrementAndGet();
        return a <= 0 ? true : false;
    }

    public boolean isCountFinish() {
        return ai.intValue() <= 0 ? true : false;
    }

}
