package com.cy.ares.spcp.protocol;

import java.util.List;

import com.cy.ares.spcp.net.protocol.NetResponse;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;

public class FetchResp extends NetResponse {
    
    static {
        ProtocolCenter.registe(Resp.conf_registe_fetch, FetchResp.class);
    }

    public FetchResp(){
        super(Resp.conf_registe_fetch);
    }
    
    private List<DataItem> itemList;

    public List<DataItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<DataItem> itemList) {
        this.itemList = itemList;
    }

}
