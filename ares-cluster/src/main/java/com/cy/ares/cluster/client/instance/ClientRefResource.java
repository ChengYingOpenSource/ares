package com.cy.ares.cluster.client.instance;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.RandomUtils;

import com.cy.ares.spcp.protocol.ClientInstanceInfo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;

@Data
public class ClientRefResource {

    private Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();

    private ClientInstanceInfo instanceInfo;

    private String instanceKey;

    private long lastHeartbeatTime;

    public ClientRefResource() {

    }

    public void hookChannel(Channel channel) {
        channelMap.putIfAbsent(channel.id(), channel);
    }

    public Channel randomChannel() {
        Collection<Channel> sc = channelMap.values();
        int s = sc.size();
        if (s <= 0) {
            return null;
        }
        int index = Math.abs(RandomUtils.nextInt()) % s;
        return (Channel)sc.toArray()[index];
    }

    public void releaseChannel(ChannelId channelId) {
        channelMap.remove(channelId);
    }

}
