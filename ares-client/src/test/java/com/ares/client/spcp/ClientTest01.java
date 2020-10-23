package com.ares.client.spcp;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.cy.ares.spcp.actor.fetcher.ConfigFetchActor;
import com.cy.ares.spcp.context.ClientInstanceFactory;
import com.cy.ares.spcp.context.SpcpConfig;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.cst.ConfigCst;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.DataItem;
import com.cy.ares.spcp.protocol.FetchReq;


public class ClientTest01 {
    
    
    
    public static void main(String[] args) {
        
        FetchReq fr = new FetchReq();
        Set<String> keys = new HashSet<>();
        keys.add("xxx");
        fr.setKeys(keys);
        
        System.out.println(JSON.toJSONString(fr));
    }
    
    public static void main2(String[] args) {
        
        SpcpConfig conf = new SpcpConfig();
        //conf.setServerAddr("30.14.202.36:35500");
        //conf.setServerAddr("2.0.1.102:35500");
        conf.setServerAddr("192.168.3.205:35500");
        ClientInstanceInfo instanceInfo = ClientInstanceFactory.instanceClient(null,"appm01", "dev", ConfigCst.CLUSTER_DEF);

        SpcpContext sc = new SpcpContext(conf,instanceInfo);
        sc.bootstrap();

        ConfigFetchActor fetchActor = sc.getConfFetchActor();
        DataItem dataItem =  fetchActor.getConfig("thread.test001");
        System.out.println(JSON.toJSONString(dataItem));

    }
}
