package com.cy.ares.spcp.actor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.cy.ares.spcp.actor.fetcher.FetchCallback;
import com.cy.ares.spcp.client.NothingCallback;
import com.cy.ares.spcp.client.ReceiverFuture;
import com.cy.ares.spcp.context.SpcpConfig;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.net.Callback;
import com.cy.ares.spcp.net.protocol.NetResponse;
import com.cy.ares.spcp.protocol.FetchResp;
import com.cy.ares.spcp.util.AresMsgThreadFactory;
import com.cy.ares.spcp.util.RejectedThreadHandler;

/**
 * 从server端返回的event处理
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月5日 下午2:37:03
 * @version V1.0
 */
public class RespActor {

    private ThreadPoolExecutor executor;
    private SpcpConfig conf;
    private SpcpContext context;

    public RespActor(SpcpContext context) {
        this.conf = context.getConfig();
        this.context = context;
        ThreadFactory threadFactory = new AresMsgThreadFactory("ares2-resp-");

        int maxThread = this.conf.getRespExecuteMaxThread();
        maxThread = maxThread <= 32 ? 32 : maxThread;
        this.executor = new ThreadPoolExecutor(4, maxThread, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(16), threadFactory, new RejectedThreadHandler());
    }

    public void execute(ReceiverFuture rf, Event event) {
        Callback callback = rf.getCallback();
        rf.setResp(event);
        rf.run();
        // nothing特殊返回
        if (callback instanceof NothingCallback) {
            return;
        }
        // 异步执行
        this.executor.execute(new RespRunnable(callback, event, this.context));
    }
    
    public void execute(Callback callback, Event event) {
        this.executor.execute(new RespRunnable(callback, event, this.context));
    }
    
    public void close(){
        this.executor.shutdownNow();
    }
    
    public static class RespRunnable implements Runnable {
        
        private Callback callback;
        private Event event;
        private SpcpContext context;
        
        public RespRunnable(Callback callback, Event event, SpcpContext context) {
            this.callback = callback;
            this.event = event;
            this.context = context;
        }
        
        @Override
        public void run() {
            NetResponse netResponse = MessageConvert.toResp(event);
            if (netResponse == null) {
                callback.success(event, this.context);
            } else {
                callback.success(netResponse, this.context);
            }
        }
    }

}
