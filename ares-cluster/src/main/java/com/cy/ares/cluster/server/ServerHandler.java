package com.cy.ares.cluster.server;

import com.cy.ares.cluster.Coordinator;
import com.cy.ares.cluster.error.NetException;
import com.cy.ares.cluster.eventbus.EventBus;
import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.ares.spcp.message.EventMsg;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends SimpleChannelInboundHandler<EventMsg.Event> {

    private static final Logger logger = LoggerFactory.getLogger("SERVER_LOGGER");

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.ALL_IDLE || event.state() == IdleState.READER_IDLE) {
                // close
                logger.info("ws channel idle, will close!");
                ctx.channel().close();
                // bus
                EventBus bus = ctx.channel().attr(EventBus.key).get();
                NetMEvent e = new NetMEvent();
                e.setChannel(ctx.channel());
                e.setEventName(Coordinator.CHANNEL_IDLE);
                bus.publish(e);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel Active!ctx={}", ctx.name());
        EventBus bus = ctx.channel().attr(EventBus.key).get();
        NetMEvent e = new NetMEvent();
        e.setEventName(Coordinator.CHANNEL_ACTIVE);
        e.setChannel(ctx.channel());
        bus.publish(e);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EventMsg.Event msg) {
        logger.info("server read: msgName={},eventId={},header={}", msg.getName(), msg.getEventId(), msg.getHeader());
        EventBus bus = ctx.channel().attr(EventBus.key).get();
        NetMEvent e = new NetMEvent(msg);
        e.setEventName(msg.getName());
        e.setChannel(ctx.channel());
        e.setEventId(msg.getEventId());
        bus.publish(e);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("channel inActive!ctx={}", ctx.name());
        EventBus bus = ctx.channel().attr(EventBus.key).get();
        NetMEvent e = new NetMEvent();
        e.setEventName(Coordinator.CHANNEL_INACTIVE);
        e.setChannel(ctx.channel());
        bus.publish(e);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        if (ctx.channel().isActive()) {
            ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
        EventBus bus = ctx.channel().attr(EventBus.key).get();
        NetMEvent e = new NetMEvent(new NetException(cause));
        e.setChannel(ctx.channel());
        e.setEventName(Coordinator.CHANNEL_EXCEPTION);
        bus.publish(e);
    }
}
