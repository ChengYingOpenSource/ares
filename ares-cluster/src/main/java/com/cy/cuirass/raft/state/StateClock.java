package com.cy.cuirass.raft.state;
import java.util.concurrent.Callable;

import com.cy.onepush.dcommon.async.Async;



public class StateClock<V> {
    
    private static Async aynsc = new Async();
    
    private volatile long timems ;
    private volatile boolean reset ;
    private Callable<V> callable;
    
    private InnerRunner runner;
    
    private NodeState state;
    
    public StateClock(NodeState state,long timems,Callable<V> callable,boolean isLoop){
        this.state = state;
        this.timems = timems;
        this.callable = callable;
        if(isLoop)
            this.runner = new LoopRunner(this);
        else
            this.runner = new ClockRunner(this);
    }
    
    // start 只能一次
    private volatile boolean isStart = false;
    public synchronized void start(){
        if(this.runner == null || !runner.isRun() || isStart){
            return;
        }
        aynsc.async(this.runner);
        isStart = true;
    }
    
    // 当时间耗尽的回调 ; 
    public synchronized V callback(){
        try {
            // 多线程二次判断
            if(this.reset || !runner.isRun()){
                return null;
            }
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(" state-clock callback error!",e);
        }
    }
    
    public synchronized void reset(long timems,Callable<V> callable){
        this.timems = timems ;
        this.reset = true;
        this.callable = callable;
    }
    public synchronized void reset(){
        this.reset = true;
    }
            
    public synchronized void stop(){
        runner.stop();
    }
    
    @SuppressWarnings("all")
    static class LoopRunner extends InnerRunner{
        
        private StateClock stateClock;
        private volatile boolean run = true;
        public LoopRunner(StateClock stateClock){
            this.stateClock = stateClock;
        }
        
        @Override
        public void run() {
            long timems = stateClock.getTimems();
            long s1 = System.currentTimeMillis();
            while(run){
                long s2 = System.currentTimeMillis();
                if(stateClock.reset){
                    stateClock.reset = false;
                    timems = stateClock.getTimems();
                    s1 = System.currentTimeMillis();
                    s2 = System.currentTimeMillis();
                }
                long remain = timems - (s2-s1);
                if(remain <= 0){
                    stateClock.callback() ;
                    s1 = System.currentTimeMillis();
                    s2 = System.currentTimeMillis();
                }else{
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        
        public void stop(){
            run = false;
        }
        
        public void start(){
            run = true;
        }
        
        public boolean isRun() {
            return run;
        }
        
    }
    
    
    
    @SuppressWarnings("all")
    static class ClockRunner extends InnerRunner{
        
        private StateClock stateClock;
        private volatile boolean run = true;
        private volatile boolean timeUseUp = false;
        public ClockRunner(StateClock stateClock){
            this.stateClock = stateClock;
        }
        
        @Override
        public void run() {
            long timems = stateClock.getTimems();
            long s1 = System.currentTimeMillis();
            while(run){
                long s2 = System.currentTimeMillis();
                if(stateClock.reset){
                    stateClock.reset = false;
                    timeUseUp = false;
                    timems = stateClock.getTimems();
                    s1 = System.currentTimeMillis();
                    s2 = System.currentTimeMillis();
                }
                long remain = timems - (s2-s1);
                if(remain <= 0){
                    if(!timeUseUp){
                        stateClock.callback() ;
                        timeUseUp = true;
                    }
                }else{
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        
        public void stop(){
            run = false;
        }
        
        public boolean isTimeUseUp() {
            return timeUseUp;
        }
        
        public boolean isRun() {
            return run;
        }
        
    }
    
    
    static abstract class InnerRunner implements Runnable{
        public abstract void stop() ;
        
        public  boolean isTimeUseUp() {
            return false;
        }
        
        public abstract boolean isRun() ;
            
        
    }
    
    
    
    public long getTimems() {
        return timems;
    }

    public boolean isReset() {
        return reset;
    }

    public NodeState getState() {
        return state;
    }
    
    
}
