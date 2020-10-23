package com.cy.ares.spcp.client;

import java.util.concurrent.FutureTask;

import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.net.Callback;

/**
 * TODO 后期改成 Promise
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月5日 下午2:54:45
 * @version V1.0
 */
public class ReceiverFuture extends FutureTask<Event> {

    private String reqId;
    private Callback<Event> callback;
    // 毫秒
    private int timeout;
    private long createTime;
    private volatile boolean timeoutFlag = false;

    public ReceiverFuture(Callback<Event> callback, String reqId, int timeout) {
        super(callback);
        this.reqId = reqId;
        this.callback = callback;
        this.timeout = timeout <= 0 ? 3000 : timeout;
        this.createTime = System.currentTimeMillis();
    }

    public synchronized boolean isTimeout() {
        if (timeoutFlag) {
            return timeoutFlag;
        }
        boolean f = super.isDone();
        if (f) {
            return false;
        }
        long now = System.currentTimeMillis();
        if ((now - this.createTime) > timeout) {
            timeoutFlag = true;
            return true;
        }
        return false;
    }

    public synchronized void setResp(Event v) {
        if (isTimeout()) {
            return;
        }
        super.set(v);
    }

    public String getReqId() {
        return reqId;
    }

    public Callback<Event> getCallback() {
        return callback;
    }

    public int getTimeout() {
        return timeout;
    }

}
