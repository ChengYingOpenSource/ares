package com.cy.ares.spcp.message;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.NetResponse;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;



public class MessageConvert {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageConvert.class);
    
    private static String defInstanceId ;
    
    public static void setDefInstanceId(String instanceId){
        defInstanceId = instanceId;
    }
    
    public static Event toEvent(NetResponse resp){
        if(resp == null){
            return null;
        }
        
        EventMsg.Event.Builder eb = EventMsg.Event.newBuilder();
        eb.setEventId(resp.getEventId()).setHeader(JSON.toJSONString(resp.getHeader(),SerializerFeature.WRITE_MAP_NULL_FEATURES,SerializerFeature.DisableCircularReferenceDetect))
          .setName(resp.getName());
        
        JSONObject jsonObject = (JSONObject)JSON.toJSON(resp);
        jsonObject.remove("eventId");
        jsonObject.remove("name");
        jsonObject.remove("header");
        eb.setData(jsonObject.toJSONString());
        
        return eb.build();
        
        
    }
    
    public static Event toEvent(NetRequest req){
        if(req == null){
            return null;
        }
        String iid = req.getInstanceId();
        if(StringUtils.isBlank(iid)){
            req.setInstanceId(defInstanceId);
        }
        EventMsg.Event.Builder eb = EventMsg.Event.newBuilder();
        eb.setEventId(req.getEventId()).setHeader(JSON.toJSONString(req.getHeader(),SerializerFeature.WRITE_MAP_NULL_FEATURES,SerializerFeature.DisableCircularReferenceDetect))
          .setName(req.getName());
        
        JSONObject jsonObject = (JSONObject)JSON.toJSON(req);
        jsonObject.remove("eventId");
        jsonObject.remove("name");
        jsonObject.remove("header");
        eb.setData(jsonObject.toJSONString());
        
        return eb.build();
    }
    
    public static NetRequest toReq(Event event){
        if(event == null){
            return null;
        }
        String name = event.getName();
        Class clazz = ProtocolCenter.protolClass(name);
        if(clazz == null){
            logger.warn("no protolClass find,eventName={}",name);
            return null;
        }
        String data = event.getData();
        NetRequest resquestObj = null;
        if(StringUtils.isBlank(data)){
            try {
                resquestObj = (NetRequest)clazz.newInstance();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                logger.error("request event newInstance() failed!,event={}",JSON.toJSONString(event));
                resquestObj = new NetRequest();
            }
        }else{
            resquestObj = (NetRequest)JSON.parseObject(data, clazz);
        }
        resquestObj.setEventId(event.getEventId());
        resquestObj.setName(event.getName());
        resquestObj.setHeader(JSON.parseObject(event.getHeader(), Map.class));
        
        return resquestObj;
    }
    
    
    public static NetResponse toResp(Event event){
        if(event == null){
            return null;
        }
        String name = event.getName();
        Class clazz = ProtocolCenter.protolClass(name);
        if(clazz == null){
            return null;
        }
        String data = event.getData();
        NetResponse responseObj = null;
        if(StringUtils.isBlank(data)){
            try {
                responseObj = (NetResponse)clazz.newInstance();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                logger.error("resp event newInstance() failed!,event={}",JSON.toJSONString(event));
                responseObj = new NetResponse();
            }
        }else{
            responseObj = (NetResponse)JSON.parseObject(data, clazz);
        }
        responseObj.setEventId(event.getEventId());
        responseObj.setName(event.getName());
        responseObj.setHeader(JSON.parseObject(event.getHeader(), Map.class));
        
        return responseObj;
    }
    
}
