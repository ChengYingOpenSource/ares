package com.cy.ares.spcp.protocol;

import com.cy.ares.spcp.net.protocol.NetResponse;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;

public class ServiceRegisteResp extends NetResponse {

    static {
        ProtocolCenter.registe(Resp.service_registe, ServiceRegisteResp.class);
    }
    
    public ServiceRegisteResp(){
        super(Resp.service_registe);
    }

    private String appCode;

    // app instance 当前注册的 count
    private int count;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
