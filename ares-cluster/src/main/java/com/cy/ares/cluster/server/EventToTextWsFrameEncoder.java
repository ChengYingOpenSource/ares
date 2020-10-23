package com.cy.ares.cluster.server;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.ares.spcp.message.EventMsg.Event;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;



public class EventToTextWsFrameEncoder extends MessageToMessageEncoder<EventMsg.Event>{
    
    private static final Logger logger = LoggerFactory.getLogger(EventToTextWsFrameEncoder.class);
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Event event, List<Object> out) throws Exception {
        
        String name = event.getName();
        String eventId = event.getEventId();
        String header = event.getHeader();
        String data = event.getData();
        
        JSONObject js = new JSONObject();
        js.put("name", name);
        js.put("eventId", eventId);
        js.put("header", header);
        js.put("data", data);
        
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(js));
        out.add(textWebSocketFrame);
    }
}
