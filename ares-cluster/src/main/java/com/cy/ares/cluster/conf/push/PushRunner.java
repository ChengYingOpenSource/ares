package com.cy.ares.cluster.conf.push;

import com.cy.ares.cluster.client.instance.ClientChannelCenter;
import com.cy.ares.cluster.conf.ConfCoordinator;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.protocol.PushResp;
import com.cy.onepush.dcommon.async.Await;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/11 10:22
 */
public class PushRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PushRunner.class);

    private static Await await = new Await();
    public static final int PUSH_ACK_TIMEOUT = 5;
    public static final int MAX_RETRY_TIME = 2;

    private ConfCoordinator ccdn;
    private List<String> instanceIds;
    private PushResp pushResp;

    public PushRunner(PushResp pushResp, ConfCoordinator ccdn, List<String> instanceIds) {
        this.ccdn = ccdn;
        this.instanceIds = instanceIds;
        this.pushResp = pushResp;
    }

    @Override
    public void run() {
        List<String> needPushInstanceIds = this.instanceIds;
        int ctime = 0;
        for (int i = 0; i < MAX_RETRY_TIME; i++) {
            List<PushAwatiAck> ls = new ArrayList<>();
            ls = push(needPushInstanceIds);
            List<String> failedIds = ackResponse(ls, ctime);
            if (failedIds == null || failedIds.isEmpty()) {
                return;
            }
            needPushInstanceIds = failedIds;
            ctime++;
        }
    }

    private List<String> ackResponse(List<PushAwatiAck> ls, int count) {
        // ack&timeout处理;
        List<PushAwatiAck> acked = new ArrayList<>();
        List<String> failedIds = new ArrayList<>();
        try {
            if (ls.isEmpty()) {
                return failedIds;
            }
            await.await(PUSH_ACK_TIMEOUT, new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    if (ls.isEmpty()) {
                        return true;
                    }
                    List<PushAwatiAck> lsRemove = new ArrayList<>();
                    ls.forEach(e -> {
                        Boolean flag = ccdn.getPushAckConfirm().contains(e);
                        if (flag == null || flag == false) {
                            // 说明ACK成功
                            acked.add(e);
                            lsRemove.add(e);
                        }
                    });
                    ls.removeAll(lsRemove);
                    return ls.isEmpty();
                }
            });
        } catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        if (!acked.isEmpty()) {
            acked.forEach(e -> {
                String instanceId = e.getInstanceId();
                // 批量or异步 insert
                ccdn.getPushLogProcessor().addSuccess(new PushLogProcessor.PushSuccessData(pushResp, instanceId));
            });
        }
        if (!ls.isEmpty()) {
            // 没接收到ack的当做失败处理重新发送一遍
            ls.forEach(e -> {
                ccdn.getPushAckConfirm().remove(e);
                String instanceId = e.getInstanceId();
                failedIds.add(instanceId);
                ccdn.getPushLogProcessor().addFailed(
                    new PushLogProcessor.PushFailedData(pushResp, instanceId, "ACK_TIMEOUT"));
            });
        }
        return failedIds;
    }

    public List<PushAwatiAck> push(List<String> needPushInstanceIds) {
        ClientChannelCenter channelCenter = ccdn.getChannelCenter();
        List<PushAwatiAck> ls = new ArrayList<>();
        for (String instanceId : needPushInstanceIds) {
            Channel channel = channelCenter.findByInstanceId(instanceId);
            if (channel == null) {
                logger.warn("instance no channel active! instanceId={}", instanceId);
                continue;
            }
            try {
                EventMsg.Event event = MessageConvert.toEvent(pushResp);
                channel.writeAndFlush(event);
                // 需要接收到client端的 pushAck
                PushAwatiAck paa = new PushAwatiAck(event.getEventId(), instanceId);
                ccdn.getPushAckConfirm().add(paa);
                ls.add(paa);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                ccdn.getPushLogProcessor().addFailed(
                    new PushLogProcessor.PushFailedData(pushResp, instanceId, e.getMessage()));
            }
        }
        return ls;
    }

}
