package com.cy.ares.spcp.actor.timer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.cy.ares.spcp.actor.cache.CacheCompareActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.actor.register.ServiceRegisteActor;
import com.cy.ares.spcp.client.ReceiverFuture;
import com.cy.ares.spcp.client.Sender;
import com.cy.ares.spcp.client.errors.PoolException;
import com.cy.ares.spcp.context.SpcpConfig;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.HeartbeatReq;

/**
 * 本地key 缓存和线上的比较;
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年4月29日 下午3:52:54
 * @version V1.0
 */
public class TimerCacheCompare {

    // Sender + HeartbeatReq + NodeManager;
    // 定时刷新服务器list

    private static final Logger logger = LoggerFactory.getLogger(TimerCacheCompare.class);

    private SpcpContext ctx;
    private volatile boolean stop = false;

    public TimerCacheCompare(SpcpContext context) {
        this.ctx = context;
    }

    public void start() {

        SpcpConfig spcpConfig = this.ctx.getConfig();
        boolean compared = spcpConfig.isCompared();
        if(!compared){
            return;
        }
        long timeInterval = spcpConfig.getCompareTimeIntervalMs();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(!stop){
//                    long s1 = System.currentTimeMillis();
//                    try{
//                        Thread.currentThread().sleep(timeInterval);
//                    }catch (Exception e){  }
//                    if(stop){
//                        break;
//                    }
//                    CacheCompareActor cacheCompareActor = ctx.getCacheCompareActor();
//                }
//            }
//        }).start();
    }

    public void stop() {
        stop = true;
    }
}
