package com.cy.ares.cluster.notify;
import com.cy.ares.spcp.protocol.DataGroupKey;
import com.cy.cuirass.cluster.event.PubMEvent;



public class NotifyCommitEvent extends PubMEvent {
   
    private DataGroupKey dataGroup;
    
    private String dataId;

    private String dataIdAction;
    
    private String notifyEventId;
    
    private long happenTime;
    
    private boolean reload;

    private boolean batch;

    public boolean getBatch() {
        return batch;
    }

    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    public String getDataIdAction() {
        return dataIdAction;
    }

    public void setDataIdAction(String dataIdAction) {
        this.dataIdAction = dataIdAction;
    }

    public boolean isReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(long happenTime) {
        this.happenTime = happenTime;
    }

    public String getNotifyEventId() {
        return notifyEventId;
    }

    public void setNotifyEventId(String notifyEventId) {
        this.notifyEventId = notifyEventId;
    }

    public DataGroupKey getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(DataGroupKey dataGroup) {
        this.dataGroup = dataGroup;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
    
    
}
