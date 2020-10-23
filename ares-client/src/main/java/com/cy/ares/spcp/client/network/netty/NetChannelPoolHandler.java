package com.cy.ares.spcp.client.network.netty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.client.Receiver;
import com.cy.ares.spcp.message.EventMsg;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/***
 * netty transport handler  pipeline init
 *
 * @author
 *
 * */

public class NetChannelPoolHandler  implements ChannelPoolHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(NetChannelPoolHandler.class);
    
    private Receiver receiver ;
    
    public NetChannelPoolHandler(Receiver receiver){
        this.receiver = receiver;
    }
    
    @Override
    public void channelAcquired(Channel ch) throws Exception {
        
    }
    
    @Override
    public void channelCreated(Channel ch) throws Exception {
        
        ChannelPipeline pipeline = ch.pipeline();
        // PB
        pipeline.addLast(new ProtobufVarint32FrameDecoder())
        .addLast(new ProtobufDecoder(EventMsg.Event.getDefaultInstance()))
        .addLast(new ProtobufVarint32LengthFieldPrepender())
        .addLast(new ProtobufEncoder());
        
        // and then business logic.
        pipeline.addLast(new LogicClientHandler());
        
        ch.attr(Receiver.key).set(receiver);
        
    }
    
    @Override
    public void channelReleased(Channel ch) throws Exception {
        //ch.writeAndFlush(Unpooled.EMPTY_BUFFER); //flush掉所有写回的数据  
    }
    
}
