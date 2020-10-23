package com.cy.ares.spcp.net.protocol;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class NetRequest {
    
    protected String eventId;
    
    protected String name;
    
    protected Map<String,Object> header = new HashMap<>();
    
    public NetRequest(){
        
    }
    
    
    public NetRequest(String reqName){
        this.name = reqName;
        this.eventId = UUID.randomUUID().toString();
    }
    
    public String getInstanceId() {
        return header==null?null:(String)header.get(HeaderStand.instanceId);
    }
    
    public void setInstanceId(String instanceId) {
        header.put(HeaderStand.instanceId, instanceId);
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getName() {
        return name;
    }
    
    public void addHeader(String key,Object value){
        header.put(key, value);
    }
    
    public void removeHeader(String key){
        header.remove(key);
    }
    
    public Map<String, Object> getHeader() {
        return header;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }
    
}
