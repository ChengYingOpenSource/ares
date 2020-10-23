package com.cy.ares.spcp.actor.timer;

import com.cy.ares.spcp.actor.register.ServiceRegisteActor;
import com.cy.ares.spcp.client.ReceiverFuture;
import com.cy.ares.spcp.client.Sender;
import com.cy.ares.spcp.client.errors.PoolException;
import com.cy.ares.spcp.context.SpcpConfig;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.HeartbeatReq;
import com.cy.ares.spcp.protocol.HeartbeatResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 心跳定时器;
 * <p>
 * Note: 心跳之前先进行服务注册;
 * <p>
 * <p>
 * TODO: 这个定时一次只能检测一个channel ?
 *
 * @author maoxq
 * @version V1.0
 * @Description
 * @date 2019年4月29日 下午3:52:54
 */
public class TimerHeartbeatCheck {

    // Sender + HeartbeatReq + NodeManager;
    // 定时刷新服务器list

    private static final Logger logger = LoggerFactory.getLogger(TimerHeartbeatCheck.class);

    private SpcpContext ctx;
    private volatile boolean stop = false;

    public TimerHeartbeatCheck(SpcpContext context) {
        this.ctx = context;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int failedCount = 0;

                SpcpConfig conf = ctx.getConfig();
                int heartbeatInterval = conf.getHeartbeatInterval(); // ms
                int timeout = conf.getHeartbeatRespTimeout(); // ms
                Sender sender = ctx.getSender();
                ServiceRegisteActor serviceRegisteActor = ctx.getServiceRegisteActor();
                ClientInstanceInfo clientInstanceInfo = serviceRegisteActor.getClientInstanceInfo();
                while (!stop) {
                    try {
                        // pool close 说明 sender 也 close了;
                        if (sender.getPool().isClose()) {
                            logger.info("heartbeat check sender is close, heartbeat trigger stop!");
                            stop();
                            continue;
                        }
                        if (failedCount >= conf.getHeartbeatMaxFailedCount()) {
                            // 触发 client refresh
                            // TODO 通过 NodeManager 来管理连接对象(NodeInfo), 当前是顺序选择;
                            boolean f = sender.getPool().refreshClient();
                            if (!f && sender.getPool().isClose()) {
                                stop();
                                logger.info("heartbeat check sender is close, heartbeat trigger stop!");
                                continue;
                            } else if (!f) {
                                Thread.sleep(3000);
                            } else {
                                failedCount = 0;
                                ctx.refreshConfig();
                            }
                        }

                        HeartbeatReq hr = new HeartbeatReq();
                        hr.setInterval(conf.getHeartbeatInterval());
                        hr.setCurrentTime(System.currentTimeMillis());
                        hr.setClientInstanceInfo(clientInstanceInfo);

                        ReceiverFuture rf = sender.send(hr, timeout);
                        Event event = rf.get(timeout, TimeUnit.MILLISECONDS);
                        HeartbeatResp resp = (HeartbeatResp)MessageConvert.toResp(event);

                        // 服务端返回 集群列表， 客户端刷新这个列表 ? ? 造成问题，客户端启动的时候永远只能链接 配置好的服务端node
                        // 但是 服务端信息 确实需要不断的刷新; 通过心跳是否合适 ? 心跳的响应数据量应该少

                        logger.debug("service heartbeat resp status={}", resp.getStatus());
                        failedCount = 0;
                        Thread.sleep(heartbeatInterval);

                    } catch (TimeoutException e) {
                        failedCount++;
                        logger.warn(e.getMessage(), e);
                        logger.warn("heartbeat timeout !failedCount={}.", failedCount);
                    } catch (PoolException e) {
                        failedCount++;
                        // 说明情况比较严重, sleep 5s 之后再进行
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e1) {
                            logger.error(e1.getMessage(), e1);
                            Thread.currentThread().interrupt();
                        }
                    } catch (Exception e) {
                        failedCount++;
                        try {
                            if (failedCount >= conf.getHeartbeatMaxFailedCount()) {
                                Thread.sleep(5000);
                            }
                        } catch (InterruptedException e1) {
                            logger.error(e1.getMessage(), e1);
                            Thread.currentThread().interrupt();
                        }
                        logger.warn("heartbeat exception=" + e.getMessage(), e);
                    }
                }
                logger.info("heartbeat haved stop!");
            }
        }).start();
    }

    public void stop() {
        stop = true;
    }
}
