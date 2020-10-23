package com.cy.ares.spcp.client.network.netty;

import com.cy.ares.spcp.message.EventMsg;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
@Deprecated
public class NetClientInitializer extends ChannelInitializer<SocketChannel> {

    public NetClientInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        
        // 半包粘包 + 压缩
        pipeline.addLast(new ProtobufVarint32FrameDecoder())
        .addLast(new ProtobufDecoder(EventMsg.Event.getDefaultInstance()))
        .addLast(new ProtobufVarint32LengthFieldPrepender())
        .addLast(new ProtobufEncoder());
        
        // and then business logic.
        pipeline.addLast(new LogicClientHandler());
    }
}
