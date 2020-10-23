package com.cy.ares.cluster.conf;

import com.cy.ares.cluster.client.instance.ClientChannelCenter;
import com.cy.ares.cluster.conf.push.PushAwatiAck;
import com.cy.ares.cluster.conf.push.PushLogProcessor;
import com.cy.ares.cluster.conf.push.PushRunner;
import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.ares.common.utils.ListUtils;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;
import com.cy.ares.spcp.protocol.FetchReq;
import com.cy.ares.spcp.protocol.FetchResp;
import com.cy.ares.spcp.protocol.PushAck;
import com.cy.ares.spcp.protocol.PushResp;
import com.cy.onepush.dcommon.async.Async;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 协调器，代理client与server之间的交互
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月7日 下午3:55:26
 * @version V1.0
 */
public class ConfCoordinator extends Subscriber {

    // public static final AttributeKey<ConfCoordinator> key =
    // AttributeKey.newInstance(ConfCoordinator.class.getSimpleName());

    private static final Logger logger = LoggerFactory.getLogger(ConfCoordinator.class);

    public static final int MAX_SEND_PAGE = 5;


    private ConfPersistence confPersistence;

    private ClientChannelCenter channelCenter;

    private Async async;

    private PushLogProcessor pushLogProcessor;

    public ConfCoordinator(ConfPersistence confPersistence, ClientChannelCenter channelCenter) {
        this.confPersistence = confPersistence;
        this.channelCenter = channelCenter;
        int cpu = Runtime.getRuntime().availableProcessors();
        int min = cpu<=16?16:cpu;
        int max = cpu*2 < 32?32:cpu*2;
        this.async = new Async(min,max,2000);
        this.pushLogProcessor = new PushLogProcessor(this);
        addEventName(Req.conf_registe_fetch);
        addEventName(Req.conf_compare);
        
        addEventName(Resp.conf_registe_fetch);
        addEventName(Resp.conf_compare);
        addEventName(Resp.conf_push);
        
        addEventName(Req.conf_push_ack);
    }

    public void confRegisteFetch(FetchReq fr, NetMEvent nme) {
        FetchResp fetchResp = confPersistence.fetch(fr);
        fetchResp.setEventId(fr.getEventId());
        Channel channel = nme.getChannel();
        channel.writeAndFlush(MessageConvert.toEvent(fetchResp));
    }
    
    public void confPush(PushResp pushResp) {
        String key = pushResp.getDataCluster().key();
        List<String> ls = channelCenter.findByKey(key);
        // 没有客户端实例链接;
        if (ls == null || ls.isEmpty()) {
            return;
        }
        // 是否多线程push?
        int size = ls.size();
        int pageSize = MAX_SEND_PAGE;
        if (size <= pageSize) {
            // TODO 提交会报错
            this.async.async(new PushRunner(pushResp, this, ls));
            return;
        }
        int pageNum = size % pageSize == 0 ? size / pageSize : (size / pageSize) + 1;
        for (int i = 0; i < pageNum; i++) {
            List<String> pageList = ListUtils.pageList(ls, i, pageSize);
            if (pageList.isEmpty()) {
                continue;
            }
            this.async.async(new PushRunner(pushResp, this, pageList));
        }
    }
    
    private ConcurrentSkipListSet<PushAwatiAck> pushAckConfirm = new ConcurrentSkipListSet<>(new Comparator<PushAwatiAck>() {
        public int compare(PushAwatiAck o1, PushAwatiAck o2) {
            return o1.getEventId().compareTo(o2.getEventId());
        };
    });


    private void confirmPushAck(PushAck ack) {
        logger.info("receive pushAck,ackId={}",ack.getEventId());
        pushAckConfirm.remove(new PushAwatiAck(ack.getEventId()));
    }

    /**
     * 内部消息的进入口; cluster同步的信息;
     */
    @Override
    public MEvent action(MEvent event) {
        // serverHandler -> EventBus -> messageIn

        String name = event.getEventName();
        Object obj = event.getEvent();
        if (Req.conf_push_ack.equals(name)) {
            EventMsg.Event ee = (EventMsg.Event) obj;
            PushAck ack = (PushAck) MessageConvert.toReq(ee);
            confirmPushAck(ack);
        } else if (Req.conf_registe_fetch.equals(name)) {
            EventMsg.Event ee = (EventMsg.Event) obj;
            FetchReq fr = (FetchReq) MessageConvert.toReq(ee);
            NetMEvent nme = (NetMEvent) event;
            confRegisteFetch(fr, nme);
        } else if (Req.conf_compare.equals(name)) {
            NetMEvent nme = (NetMEvent) event;
        } else if (Resp.conf_push.equals(name)) {
            PushResp pr = (PushResp) obj;
            confPush(pr);
        }
        return null;
    }

    public ConfPersistence getConfPersistence() {
        return confPersistence;
    }

    public ClientChannelCenter getChannelCenter() {
        return channelCenter;
    }

    public PushLogProcessor getPushLogProcessor() {
        return pushLogProcessor;
    }

    public ConcurrentSkipListSet<PushAwatiAck> getPushAckConfirm() {
        return pushAckConfirm;
    }

}
