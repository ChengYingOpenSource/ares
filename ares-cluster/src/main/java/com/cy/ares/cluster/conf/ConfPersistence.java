package com.cy.ares.cluster.conf;
import java.util.*;

import com.cy.ares.spcp.protocol.DataItem;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.DataGroupKey;
import com.cy.ares.spcp.protocol.FetchReq;
import com.cy.ares.spcp.protocol.FetchResp;



public interface ConfPersistence {
    
    public FetchResp fetch(FetchReq req) ;
    
    public DataItem findDataItem(DataGroupKey group,String dataId);
    
    public List<DataItem> findDataItems(DataGroupKey group);
    
    public boolean isLastItem(DataItem item);
    
    public int saveConfPushLog(boolean isSuccess,List<DataItem> items,ClientInstanceInfo instanceInfo,String pushMsg);
    
    
}
