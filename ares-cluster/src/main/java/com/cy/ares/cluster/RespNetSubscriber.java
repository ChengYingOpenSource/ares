package com.cy.ares.cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cy.ares.cluster.eventbus.NetMEvent;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;


/**
 * TODO 统一封装返回 , 但是 akka 内部event netEevent 是否有影响，待确认
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年8月7日 上午11:33:16
 * @version V1.0
 */
public abstract class RespNetSubscriber extends Subscriber {
    
    private static final Logger logger = LoggerFactory.getLogger(RespNetSubscriber.class);
    
    public void action0(NetMEvent msg){
        MEvent r = null;
        try {
            r = action(msg);
        } catch (Exception e) {
            logger.error("eventName={},evetnId={},exception",msg.getEventName(),msg.getEventId());
            logger.error(e.getMessage(),e);
            try {
                JSONObject js = new JSONObject();
                js.put("name", ProtocolCenter.Error.system_error);
                js.put("eventId", msg.getEventId());
                js.put("header", "{}");
                js.put("data", e.getMessage());
                if(msg.getChannel().isActive())
                    msg.getChannel().writeAndFlush(JSON.toJSONString(js,SerializerFeature.WRITE_MAP_NULL_FEATURES));
                else
                    logger.error("return errorInfo channel is not active!");
            } catch (Exception e2) {
                logger.error(e2.getMessage(),e2);
            }
        }
        msg.getChannel().writeAndFlush(r);
    }
    
}
