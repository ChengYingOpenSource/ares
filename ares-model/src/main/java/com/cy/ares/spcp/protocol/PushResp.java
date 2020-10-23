package com.cy.ares.spcp.protocol;

import java.util.ArrayList;
import java.util.List;

import com.cy.ares.spcp.net.protocol.NetResponse;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;

public class PushResp extends NetResponse {
    
    static {
        ProtocolCenter.registe(Resp.conf_push, PushResp.class);
    }
    
    public PushResp(){
        super(Resp.conf_push);
    }
    
    private List<DataItem> items = new ArrayList<>();
    
    // dataItem 的 key
    private DataGroupKey dataGroup;
    
    private DataClusterKey dataCluster;
    
    
    public DataClusterKey getDataCluster() {
        return dataCluster;
    }

    public void setDataCluster(DataClusterKey dataCluster) {
        this.dataCluster = dataCluster;
    }

    public List<DataItem> getItems() {
        return items;
    }
    
    public void setItems(List<DataItem> items) {
        this.items = items;
    }

    public DataGroupKey getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(DataGroupKey dataGroup) {
        this.dataGroup = dataGroup;
    }
    
    
    
    
}
