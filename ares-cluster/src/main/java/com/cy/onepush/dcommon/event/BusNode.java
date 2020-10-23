package com.cy.onepush.dcommon.event;

import lombok.Data;

@Data
public class BusNode {
        
    private String host;
    
    private String port;
    
    // relative address
    private String url;
    
    // get post
    private String method;
    
    public String wholeUrl(){
        
        String wurl = "http://"+host+":"+port+"/"+url;
        
        return wurl;
    }
    
    
}
