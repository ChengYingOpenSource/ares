package com.cy.ares.spcp.message;

public class HttpMessage {
    
    // 每次随机生成
    private String eventId;
    
    private String name;
    
    // k=v&k=v格式即可;
    private String header;
    
    private String data;
    
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
}
