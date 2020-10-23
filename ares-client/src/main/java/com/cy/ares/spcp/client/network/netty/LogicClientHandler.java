package com.cy.ares.spcp.client.network.netty;

import com.cy.ares.spcp.client.Receiver;
import com.cy.ares.spcp.client.errors.NetException;
import com.cy.ares.spcp.message.EventMsg;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogicClientHandler extends SimpleChannelInboundHandler<EventMsg.Event> {

    private static final Logger logger = LoggerFactory.getLogger(LogicClientHandler.class);

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel Active!ctx={}", ctx.name());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EventMsg.Event msg) {
        logger.debug("client read: msgName={},eventId={}", msg.getName(), msg.getEventId());
        Receiver receiver = ctx.channel().attr(Receiver.key).get();
        receiver.respHandle(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("channel inActive!ctx={}", ctx.name());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        if (ctx.channel().isActive()) {
            ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }

        Receiver receiver = ctx.channel().attr(Receiver.key).get();
        receiver.exceptionHandle(new NetException(cause));
    }
}
