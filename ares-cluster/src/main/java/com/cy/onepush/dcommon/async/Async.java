package com.cy.onepush.dcommon.async;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * TODO 
 * 如果使用 Fiber 对查询数据库没有什么帮助!!!!
 * 
 * 替换Fiber为线程池；线程池做到可配置，增加一个InnerAsync内部类 ; 
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年9月1日 下午12:54:01
 * @version V1.0
 */
public class Async {
    
    private ThreadPoolExecutor executor ;
    
    public Async(int min,int max,int queue){
        
        this.executor = new ThreadPoolExecutor(min,max, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queue), new DefThreadFactory("Async-"),new RejectedThreadHandler());
        
    }
    
    
    public Async(){
        int cpu = Runtime.getRuntime().availableProcessors();
        cpu = cpu<=4?4:cpu;
        int max = cpu*2 < 32?32:cpu*2;
        this.executor = new ThreadPoolExecutor(cpu,max, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(50), new DefThreadFactory("Async-"),new RejectedThreadHandler());
    }
    
    
    public void close(){
        if(executor != null){
            executor.shutdown();
        }
    }
    
    public <T> Future<T> async(Callable<T> callable){
        
        return this.executor.submit(callable);
    }
    
    
    public Future<Boolean> async(Runnable runnable){
        
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                runnable.run();
                return true;
            }
        };
        
        return async(callable) ;
        
    }
    
}
