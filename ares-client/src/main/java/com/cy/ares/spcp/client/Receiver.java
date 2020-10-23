package com.cy.ares.spcp.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.actor.RespActor;
import com.cy.ares.spcp.client.errors.NetException;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.net.Callback;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;

import io.netty.util.AttributeKey;

/**
 * Future response to Callback 对于发生异常的请求，主要是超时+轮询 移除
 *
 * @author maoxq
 * @version V1.0
 * @Description
 * @date 2019年5月5日 下午2:26:15
 */
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    public static final AttributeKey<Receiver> key = AttributeKey.newInstance(Receiver.class.getSimpleName());

    private Map<String, ReceiverFuture> respMap = new ConcurrentHashMap<>();

    private ConcurrentLinkedDeque<String> sendReq = new ConcurrentLinkedDeque<>();

    private volatile boolean stop = false;

    private SpcpContext ctx;

    public Receiver(SpcpContext ctx) {
        this.ctx = ctx;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    try {
                        // TODO 如果第一个timeout很大，则会有问题;
                        String ele = sendReq.pollFirst();
                        if (ele == null) {
                            Thread.sleep(1000);
                            continue;
                        }
                        ReceiverFuture rf = respMap.get(ele);
                        if (rf == null) {
                            continue;
                        }
                        if (rf != null && rf.isTimeout()) {
                            logger.info("reqId={} -> timeout={}", rf.getReqId(), rf.getTimeout());
                            respMap.remove(rf);
                        } else {
                            if (!rf.isDone()) { sendReq.addFirst(ele); }
                            Thread.sleep(500);
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }).start();
    }

    public ReceiverFuture sendCallback(String reqId, Callback<Event> callback, int timeout) {

        ReceiverFuture rf = new ReceiverFuture(callback, reqId, timeout);
        respMap.put(reqId, rf);
        sendReq.addLast(reqId);
        return rf;
    }

    public void respHandle(EventMsg.Event event) {

        String reqId = event.getEventId();
        ReceiverFuture rf = respMap.get(reqId);
        RespActor respActor = this.ctx.getRespActor();
        String name = event.getName();
        // name 是否是 error信息; 统一异常处理
        if (ProtocolCenter.Error.system_error.equals(name)) {
            logger.warn("name={} header={} data={}!", event.getName(), event.getHeader(), event.getData());
            if (rf != null) {
                respMap.remove(reqId);
            }
            return;
        }
        if (rf == null) {
            // 没有主动的回调, 直接服务端返回的信息 ;
            Callback callback = ProtocolCenter.protolCallback(name);
            if (callback != null) {
                respActor.execute(callback, event);
            } else {
                logger.info("reqId={}, no future to invoke,maybe timeout or stop={}!", reqId, stop);
            }
            return;
        } else {
            respMap.remove(reqId);
            respActor.execute(rf, event);
        }

    }

    public void exceptionHandle(NetException exception) {
        // channel是复用的，所以不知道channel exception是哪个请求;
        // 暂时没必要保存 channelId 与 请求list 的关系; 通过超时处理 ;
        logger.error(exception.getMessage(), exception);
    }

    public void close() {
        stop = true;
        sendReq.clear();
        respMap.clear();
    }

}
