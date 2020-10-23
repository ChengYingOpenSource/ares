package com.cy.ares.spcp.net.protocol;

import java.util.HashMap;
import java.util.Map;

import com.cy.ares.spcp.net.Callback;

public class ProtocolCenter {

    public static class Req {

        public static final String heart = "req:client:heart";

        // 注册同时获取
        public static final String conf_registe_fetch = "req:app:conf:registe:fetch";

        public static final String conf_compare = "req:app:conf:compare";
        
        public static final String conf_push_ack = "req:app:conf:push:ack";
        
        public static final String service_registe = "req:service:registe";

        public static final String service_sub_fetch = "req:service:sub:fetch";
    }

    public static class Resp {

        public static final String heart = "resp:client:heart";

        // 注册同时获取
        public static final String conf_registe_fetch = "resp:app:conf:registe:fetch";

        public static final String conf_compare = "resp:app:conf:compare";
        
        public static final String conf_push = "resp:app:conf:push";

        public static final String service_registe = "resp:service:registe";

        public static final String service_sub_fetch = "resp:service:sub:fetch";
        
        

    }
    
    public static class Error {
        public static final String ws_error_format = "resp:ws:error:format";
        
        public static final String system_error = "resp:system:error";
    }

    // key: eventName, class 
    private static final Map<String, Class> protolMap = new HashMap<>();
    
    private static final Map<String, Callback> protolCallbackMap = new HashMap<>();
    
    /**
     * protocol登记
     */
    public static boolean registe(String key, Class value) {
        protolMap.put(key, value);
        return true;
    }

    public static Class protolClass(String key) {
        return protolMap.get(key);
    }
    
    
    public static boolean registe(String key, Callback callback) {
        protolCallbackMap.put(key, callback);
        return true;
    }

    public static Callback protolCallback(String key) {
        return protolCallbackMap.get(key);
    }
    
}
