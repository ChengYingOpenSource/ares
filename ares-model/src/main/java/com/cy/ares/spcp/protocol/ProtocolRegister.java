package com.cy.ares.spcp.protocol;
import java.util.*;



public class ProtocolRegister {
    public static void registe(){
        Object obj = new ServiceRegisteReq();
        obj = new ServiceRegisteResp();
        obj = new PushAck();
        obj = new PushResp();
        obj = new HeartbeatReq();
        obj = new HeartbeatResp();
        obj = new FetchReq();
        obj = new FetchResp();
    }
}
