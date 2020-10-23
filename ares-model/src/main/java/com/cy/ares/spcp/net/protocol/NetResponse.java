package com.cy.ares.spcp.net.protocol;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



public class NetResponse {
    
    protected String eventId;
    
    protected String name;
    
    protected Map<String,Object> header = new HashMap<>();
    
    public NetResponse(){
        
    }
    
    public NetResponse(String respName){
        this.name = respName;
    }
    
    public String getServiceId() {
        return header==null?null:(String)header.get(HeaderStand.serviceId);
    }

    public void setServiceId(String serviceId) {
        header.put(HeaderStand.serviceId, serviceId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
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
    
    
}
