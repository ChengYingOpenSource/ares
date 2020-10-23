package com.cy.ares.cluster.notify;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.cy.ares.spcp.cst.DataIdActionCst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.ares.cluster.conf.ConfPersistence;
import com.cy.ares.cluster.eventbus.EventBus;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;
import com.cy.ares.spcp.protocol.DataClusterKey;
import com.cy.ares.spcp.protocol.DataGroupKey;
import com.cy.ares.spcp.protocol.DataItem;
import com.cy.ares.spcp.protocol.PushResp;
import com.cy.cuirass.app.AppContext;
import com.cy.cuirass.cluster.AkCluster;
import com.cy.onepush.dcommon.async.Async;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;

import lombok.Data;

public class ClusterNotifyHandler extends Subscriber {

    private static final Logger logger = LoggerFactory.getLogger(ClusterNotifyHandler.class);

    public static final String NOTIFY = "NOTIFY";
    public static final String NOTIFY_ACK = "NOTIFY_ACK";
    public static final String NOTIFY_COMMIT = "NOTIFY_COMMIT";
    public static final String NOTIFY_COMMIT_ACK = "NOTIFY_COMMIT_ACK";

    private AppContext appContext;

    private AkCluster cluster;

    private EventBus eventBus;
    private ConfPersistence confPersistence;

    private Async async;

    private volatile boolean stop = false;
    public static final long notifyEventCheckTimeout = 5000;

    public ClusterNotifyHandler(AppContext appContext, EventBus eventBus, ConfPersistence confPersistence) {
        addEventName(NOTIFY);
        addEventName(NOTIFY_COMMIT);
        this.appContext = appContext;
        this.eventBus = eventBus;
        this.confPersistence = confPersistence;
        this.cluster = appContext.getCluster();
        this.async = new Async(4, 8, 100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    checkEvent();
                }

            }
        }).start();
    }

    /**
     * NotifyEvent NotifyCommitEvent
     */
    @Override
    public MEvent action(MEvent mEvent) {

        // 1. 如果接受到 Commit 直接发送;
        // 2.
        if (mEvent instanceof NotifyEvent) {
            notifyRecevie((NotifyEvent) mEvent);
            NotifyAck nae = new NotifyAck(NOTIFY_ACK, mEvent.getEventId());
            return nae;
        } else if (mEvent instanceof NotifyCommitEvent) {
            notifyCommitRecevie((NotifyCommitEvent) mEvent);
            NotifyCommitAck nca = new NotifyCommitAck(NOTIFY_COMMIT_ACK, mEvent.getEventId());
            return nca;
        }

        return null;
    }

    // 定时check, 如果超时， check数据库是否已经commit; 通过时间判断；
    private ConcurrentLinkedDeque<NotifyEvent> receiveNotifyEvent = new ConcurrentLinkedDeque<>();
    private Map<String, NotifyEvent> receiveNotifyEventMap = new ConcurrentHashMap<>();

    private void checkEvent() {
        NotifyEvent ne = null;
        try {
            ne = receiveNotifyEvent.pollFirst();
            if (ne == null) {
                Thread.sleep(50);
                return;
            }
            String eventId = ne.getEventId();
            long ht = ne.getHappenTime();
            if ((System.currentTimeMillis() - ht) >= notifyEventCheckTimeout) {
                // 说明已经timeout
                if(receiveNotifyEventMap.get(eventId) == null){
                    return;
                }
                // 虽然下面并非严格串行, 但也可以认为超时;
                receiveNotifyEventMap.remove(eventId);
                // 超时为什么还要重新push ??? 有问题 先注释掉
                // this.async.async(new NotifyTimeoutRunnable(ne));
            } else {
                // @Note有问题; 可能会被comit了, 不能再次加入, 只能等到超时
                if(receiveNotifyEventMap.get(eventId) != null){
                    receiveNotifyEvent.addFirst(ne);
                }
            }
        } catch (Exception e) {
            if (ne != null) {
                receiveNotifyEventMap.remove(ne.getEventId());
            }
            logger.error("clusterNotify loop check:" + e.getMessage(), e);
        }

    }

    @Data
    public class NotifyTimeoutRunnable implements Runnable {
        private NotifyEvent ne;
        public NotifyTimeoutRunnable(NotifyEvent ne) {
            this.ne = ne;
        }
        @Override
        public void run() {
            try {
                timeoutPush(ne);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void timeoutPush(NotifyEvent ne) {
        DataItem di = ne.getDataItem();
        boolean flag = confPersistence.isLastItem(di);
        if (!flag) {
            // 说明纯粹是过期了，dataitem一定时间内未被commit to db
            logger.warn("clusterNotify event timeout check,not lastItem,dataKey={}", di.key());
            return;
        }
        // pushResp
        logger.warn("clusetNotify event timeout,but will be pushed to client!dataKey={}", di.key());
        sendToEventBus(di, ne.getEventId());
    }

    public void notifyRecevie(NotifyEvent nevent) {
        receiveNotifyEvent.add(nevent);
        receiveNotifyEventMap.put(nevent.getEventId(),nevent);
    }

    public void notifyCommitRecevie(NotifyCommitEvent nevent) {
        // notifyevent,是否存在
        String notifyEventId = nevent.getNotifyEventId();
        logger.info("recevie notifyCommitEvent,notifyEventId={}", notifyEventId);
        NotifyEvent ne = receiveNotifyEventMap.get(notifyEventId);
        if (ne != null) {
            receiveNotifyEvent.remove(ne);
            receiveNotifyEventMap.remove(notifyEventId);
        } else {
            DataGroupKey dgk = nevent.getDataGroup();
            if(nevent.isBatch()){
                logger.warn("dataItem batch commit failed notifyEventId={}! because of notifyEvent not find,groupKey={},",nevent.getNotifyEventId(), JSON.toJSONString(dgk));
                return;
            }
            ne = new NotifyEvent();
            ne.setEventId(notifyEventId);
            ne.setEventName(NOTIFY);
            // 从数据库查找;
            DataItem dbItem = confPersistence.findDataItem(dgk, nevent.getDataId());
            if (dbItem == null) {
                logger.warn("dataItem dataId={},commit failed! because of db not find,groupKey={},", nevent.getDataId(), JSON.toJSONString(dgk));
                return;
            }
            ne.setDataItem(dbItem);
            dbItem.setAction(nevent.getDataIdAction());
        }
        if(DataIdActionCst.DEL.equals(nevent.getDataIdAction())){
            sendToEventBus(ne.getDataItem(), ne.getEventId());
            return;
        }
        // 是否batch push，batch默认全部通过网络传输内容
        if(ne.isBatch()){
            sendToEventBus(ne.getDataItemList(), ne.getEventId());
            return;
        }
        // data是否被包含在event中
        if (ne.isNoContent() || nevent.isReload()) {
            DataItem item = ne.getDataItem();
            DataGroupKey dgk = item.groupKey();
            DataItem dbItem = confPersistence.findDataItem(dgk, item.getDataId());
            if (dbItem == null) {
                logger.warn("dataItem groupKey={},dataId={},commit failed!  because of db not find", JSON.toJSONString(dgk), item.getDataId());
                return;
            }
            // pushResp
            sendToEventBus(dbItem, ne.getEventId());
        } else {
            // pushResp
            sendToEventBus(ne.getDataItem(), ne.getEventId());
        }
    }
    private void sendToEventBus(List<DataItem> items, String eventId) {
        logger.info("start to eventBus for push batch,eventId={},dataKey={}",eventId,items.get(0).key());
        PushResp rp = new PushResp();
        rp.setEventId(eventId);
        rp.getItems().addAll(items);
        DataGroupKey dgk = items.get(0).groupKey();
        DataClusterKey dataClusterKey = items.get(0).clusterKey();
        rp.setDataCluster(dataClusterKey);
        rp.setDataGroup(dgk);
        MEvent me = new MEvent(Resp.conf_push, rp);
        eventBus.publish(me);
    }
    private void sendToEventBus(DataItem item, String eventId) {
        logger.info("start to eventBus for push,eventId={},dataKey={}",eventId,item.key());
        PushResp rp = new PushResp();
        rp.setEventId(eventId);
        rp.getItems().add(item);
        DataGroupKey dgk = item.groupKey();
        DataClusterKey dataClusterKey = item.clusterKey();
        rp.setDataCluster(dataClusterKey);
        rp.setDataGroup(dgk);
        MEvent me = new MEvent(Resp.conf_push, rp);
        eventBus.publish(me);
    }

    public boolean pub(NotifyEvent event, int timeout) {
        event.setEventName(NOTIFY);
        try {
            Future<Object> f = cluster.publish(event);
            f.get(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
        return true;
    }

    public void pub(NotifyCommitEvent event) {
        event.setEventName(NOTIFY_COMMIT);
        event.setShouldAck(false);
        try {
            cluster.publish(event);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    public Future<Object> pub(NotifyEvent event) {
        return cluster.publish(event);
    }

    public void close() {
        this.stop = true;
        this.async.close();
    }

}
