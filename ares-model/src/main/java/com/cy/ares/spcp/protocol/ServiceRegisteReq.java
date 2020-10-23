package com.cy.ares.spcp.protocol;

import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;
import com.cy.ares.spcp.service.ClientServiceInfo;
import com.cy.ares.spcp.service.ServiceStatus;

public class ServiceRegisteReq extends NetRequest {

    static {
        ProtocolCenter.registe(Req.service_registe, ServiceRegisteReq.class);
    }

    public ServiceRegisteReq() {
        super(Req.service_registe);
    }

    public static final String REGISTE = "registe";
    public static final String UN_REGISTE = "unregiste";

    private String status = ServiceStatus.UP.name();

    // registe unregiste
    private String action = "registe";

    private ClientServiceInfo serviceInfo;

    private ClientInstanceInfo instanceInfo;

    public ClientServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ClientServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public ClientInstanceInfo getInstanceInfo() {
        return instanceInfo;
    }

    public void setInstanceInfo(ClientInstanceInfo instanceInfo) {
        this.instanceInfo = instanceInfo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
