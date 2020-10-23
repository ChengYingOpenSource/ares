package com.cy.ares.spcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.net.Callback;
import com.cy.ares.spcp.net.protocol.NetRequest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;

/**
 * 所有请求的sender
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月5日 上午11:14:54
 * @version V1.0
 */
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private PoolManager pool;

    private Receiver receiver;

    private static final int defTimeout = 5000;

    public Sender(PoolManager pool, Receiver receiver) {
        this.pool = pool;
        this.receiver = receiver;
    }
    
    
    
    private void sendEvent(Event event){
        Channel ch = null;
        try {
            ch = pool.channel();
            if (ch == null) {
                throw new ChannelException("no channel exist! pool" + pool.toString());
            }
            if (!ch.isOpen() || !ch.isActive()) {
                throw new ChannelException("channel is not open | active ! pool" + pool.toString());
            }
            // ch write and release
            ch.writeAndFlush(event);
        } catch (ChannelException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(ch != null){
                pool.releaseChannel(ch);// flush
            }
        }
    }
    
    /**
     * 
     * @param request
     * @param callback
     * @param timeout
     *            毫秒
     * @return
     */
    public ReceiverFuture send(NetRequest request, Callback callback, int timeout) {
        Event event = MessageConvert.toEvent(request);
        sendEvent(event);
        return receiver.sendCallback(event.getEventId(), callback, timeout);
    }

    public ReceiverFuture send(NetRequest request, Callback callback) {
        ReceiverFuture rfFuture = send(request, callback, defTimeout);
        return rfFuture;
    }

    /**
     * 空回调，调用者可以默认实现同步处理 get 直接返回的是 event
     * 
     * @param request
     * @param timeout
     * @return
     */
    public ReceiverFuture send(NetRequest request, int timeout) {
        Callback<Event> callback = new NothingCallback();
        ReceiverFuture rfFuture = send(request, callback, timeout);
        return rfFuture;
    }

    /**
     * 空回调，调用者可以默认实现同步处理
     * 
     * @param request
     * @return
     */
    public ReceiverFuture send(NetRequest request) {
        Callback<Event> callback = new NothingCallback();
        ReceiverFuture rfFuture = send(request, callback, defTimeout);
        return rfFuture;
    }
    
    
    public void sendAck(NetRequest request){
        Event event = MessageConvert.toEvent(request);
        sendEvent(event);
    }

    public void close() {
        this.pool.close();
    }

    public PoolManager getPool() {
        return pool;
    }

    public Receiver getReceiver() {
        return receiver;
    }
}
