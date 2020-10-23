package com.cy.ares.cluster.client.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.HeartbeatReq;
import com.cy.ares.spcp.protocol.HeartbeatResp;
import com.cy.ares.spcp.protocol.ServiceRegisteReq;
import com.cy.onepush.dcommon.async.Await;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;

public class ResourceManager {

    private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    // instanceId -> ClientInstanceInfo信息 [service_registe]
    private Map<String, ClientRefResource> instanceResource = new ConcurrentHashMap<>();

    // key -> 多个instanceId信息实例 [service_registe]
    // key = [appCode,envCode clusterCode]
    private Map<String, CopyOnWriteArraySet<String>> keyToInstanceId = new ConcurrentHashMap<>();

    // channelId -> instanceId;
    private Map<ChannelId, ChannelInfo> channelInfoHistory = new ConcurrentHashMap<>();

    private Await await = new Await();

    // TODO 定时驱逐 心跳超时, 不用 chanelIdle控制;

    public ClientRefResource findClientRefResource(String instanceId) {
        ClientRefResource crr = instanceResource.get(instanceId);
        if (crr == null) {
            try {
                await.await(3, new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return instanceResource.containsKey(instanceId);
                    }
                });
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                crr = null;
            }
            crr = instanceResource.get(instanceId);
        }
        return crr;
    }

    public ClientInstanceInfo findInstanceInfo(String instanceId) {
        ClientRefResource crr = findClientRefResource(instanceId);
        if (crr != null) {
            return crr.getInstanceInfo();
        }
        return null;
    }

    public Channel findChannel(String instanceId) {
        ClientRefResource crr = findClientRefResource(instanceId);
        if (crr != null) {
            return crr.randomChannel();
        }
        return null;
    }

    public List<String> findByKey(String key) {
        CopyOnWriteArraySet<String> cws = keyToInstanceId.get(key);
        if (cws == null) {
            return new ArrayList<>();
        }
        List<String> lls = new ArrayList<>();
        cws.forEach(e -> {
            lls.add(e);
        });
        return lls;
    }

    public void serviceRegiste(ServiceRegisteReq srr, NetMEvent nme) {
        ClientInstanceInfo clientInstanceInfo = srr.getInstanceInfo();
        Channel channel = nme.getChannel();
        resource(clientInstanceInfo, channel);
    }

    public void resource(ClientInstanceInfo clientInstanceInfo, Channel channel) {
        String instanceId = clientInstanceInfo.getInstanceId();
        String key = clientInstanceInfo.key();

        ClientRefResource crr = instanceResource.get(instanceId);
        if (crr == null) {
            // 多个客户端同时初始化连接，会产生 同步锁的竞争;
            synchronized (this) {
                crr = instanceResource.get(instanceId);
                if (crr == null) {
                    crr = new ClientRefResource();
                    crr.setInstanceKey(clientInstanceInfo.key());
                    crr.setInstanceInfo(clientInstanceInfo);
                    instanceResource.put(instanceId, crr);
                }
            }
        }
        crr.hookChannel(channel);
        crr.setLastHeartbeatTime(System.currentTimeMillis());

        ChannelInfo ci = channelInfoHistory.get(instanceId);
        if (ci == null) {
            synchronized (crr) {
                ci = channelInfoHistory.get(instanceId);
                if (ci == null) {
                    ci = new ChannelInfo();
                    ci.setChannelId(channel.id());
                    ci.setInstanceId(instanceId);
                    ci.setKey(key);
                    if (channel.isActive())
                        channelInfoHistory.put(ci.getChannelId(), ci);
                }
            }
        }

        CopyOnWriteArraySet<String> cwalist = keyToInstanceId.get(key);
        if (cwalist == null) {
            synchronized (crr) {
                cwalist = keyToInstanceId.get(key);
                if (cwalist == null) {
                    cwalist = new CopyOnWriteArraySet<>();
                    cwalist.add(instanceId);
                    keyToInstanceId.put(key, cwalist);
                }
            }
        } else {
            if (!cwalist.contains(instanceId))
                cwalist.add(instanceId);
        }
    }

    public void heartbeatReq(HeartbeatReq srr, NetMEvent nme) {
        ClientInstanceInfo clientInstanceInfo = srr.getClientInstanceInfo();
        Channel channel = nme.getChannel();
        resource(clientInstanceInfo, channel);
        HeartbeatResp resp = new HeartbeatResp();
        resp.setEventId(srr.getEventId());
        channel.writeAndFlush(MessageConvert.toEvent(resp));

    }

    /******** 通过channel 事件 exvict **********/

    public void clearResource(ChannelInfo cinfo) {
        if (cinfo == null) {
            return;
        }
        String instanceId = cinfo.getInstanceId();
        String key = cinfo.getKey();
        ChannelId channelId = cinfo.getChannelId();

        ClientRefResource crr = instanceResource.get(instanceId);
        if (crr != null) {
            crr.releaseChannel(channelId);
            if(crr.getChannelMap().isEmpty()){
                instanceResource.remove(instanceId);
            }
        }

        CopyOnWriteArraySet<String> cwalist = keyToInstanceId.get(key);
        if (cwalist != null) {
            cwalist.remove(instanceId);
        }
    }

    private void clearChannel(Channel channel) {
        ChannelId id = channel.id();
        ChannelInfo cinfo = channelInfoHistory.get(id);
        if (cinfo == null) {
            return;
        }
        channelInfoHistory.remove(id);
        clearResource(cinfo);
    }

    public void channelException(Channel channel) {
        clearChannel(channel);
    }

    public void channelIdle(Channel channel) {
        clearChannel(channel);
    }

    public void channelActive(Channel channel) {
        // 此时没有 instanceId信息;
    }

    public void channelInActive(Channel channel) {
        clearChannel(channel);
    }

    @Data
    public static class ChannelInfo {

        private ChannelId channelId;

        private String instanceId;

        private String key;
    }

}
