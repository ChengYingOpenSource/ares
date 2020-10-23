package com.cy.ares.cluster.eventbus;
import com.cy.onepush.dcommon.event.MEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;



public class NetMEvent extends MEvent {
    
    private ChannelId channelId;
    
    private Channel channel;
    
    
    public NetMEvent(){
        
    }
    
    public NetMEvent(Object data){
        super(data);
    }
    
    public ChannelId getChannelId() {
        return channelId;
    }

    public void setChannelId(ChannelId channelId) {
        this.channelId = channelId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    
    
}
