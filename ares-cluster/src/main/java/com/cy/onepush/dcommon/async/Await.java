package com.cy.onepush.dcommon.async;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;



public class Await {
    
    @SuppressWarnings("all")
    public boolean isDone(Future ...futures){
        for(Future f:futures){
            if(!f.isDone()){
                return false;
            }
        }
        return true;
    }
    
    @SuppressWarnings("all")
    public boolean isDone(List<Future> futures){
        for(Future f:futures){
            if(!f.isDone()){
                return false;
            }
        }
        return true;
    }
    
    public boolean isDone(long timeout,List<Future> futures){
        long timescope= timeout * 1000;
        long s1 = System.currentTimeMillis();
        for(Future f:futures){
            if(!f.isDone()){
                long s2 = System.currentTimeMillis();
                long remain = timescope-(s2-s1);
                if(remain <= 0){
                    return false;
                }
                try {
                    boolean flag = f.isDone();
                    while(!flag){
                        flag = f.isDone();
                        s2 = System.currentTimeMillis();
                        remain = timescope-(s2-s1);
                        if(remain <= 0){
                            throw new TimeoutException(" timeout in "+timeout+" seconds !");
                        }
                        Thread.sleep(50);
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    @SuppressWarnings("all")
    public boolean isDone(long timeout,Future ...futures){
        return isDone(timeout,futures);
    }
    
    /**
     * TimeoutException
     * @param timeout
     * @param when
     * @return
     */
    public boolean await(long timeout,Callable<Boolean> when) throws TimeoutException{
        long timescope= timeout * 1000;
        long s1 = System.currentTimeMillis();
        try {
            boolean flag = when.call();
            while(!flag){
                flag = when.call();
                long s2 = System.currentTimeMillis();
                long remain = timescope-(s2-s1);
                if(remain <= 0){
                    throw new TimeoutException(" timeout in "+timeout+" seconds !");
                }
                Thread.sleep(50);
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Exception e){
            return false;
        }
        return true;
        
        
    }
    
    
    public <V> V get(Future<V> future) throws InterruptedException, ExecutionException{
        return future.get();
    }
    /**
     * second 单位是秒
     * @param future
     * @param timeout
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public <V> V get4s(Future<V> future,long timeout) throws InterruptedException, ExecutionException, TimeoutException{
        return future.get(4,TimeUnit.SECONDS);
    }
    
    /**
     * m 单位是分钟
     * @param future
     * @param timeout
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public <V> V get4m(Future<V> future,long timeout) throws InterruptedException, ExecutionException, TimeoutException{
        return future.get(4,TimeUnit.MINUTES);
    }
    
    /**
     * ms 单位是毫秒
     * @param future
     * @param timeout
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public <V> V get4ms(Future<V> future,long timeout) throws InterruptedException, ExecutionException, TimeoutException{
        return future.get(4,TimeUnit.MILLISECONDS);
    }
    
    
}
