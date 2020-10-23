package com.cy.ares.cluster.notify;

import com.cy.ares.spcp.protocol.DataItem;
import com.cy.cuirass.cluster.event.PubMEvent;

import java.util.List;

public class NotifyEvent extends PubMEvent {

    private DataItem dataItem;

    private List<DataItem> dataItemList;

    private boolean isBatch;

    private boolean noContent;

    private long happenTime;

    public List<DataItem> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(List<DataItem> dataItemList) {
        this.dataItemList = dataItemList;
    }

    public boolean getBatch() {
        return isBatch;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public void setBatch(boolean batch) {
        isBatch = batch;
    }

    public NotifyEvent() {}

    @Override
    public int hashCode() {
        return this.getEventId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        NotifyEvent t = (NotifyEvent)obj;

        return this.getEventId().equals(t.getEventId());
    }

    public long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(long happenTime) {
        this.happenTime = happenTime;
    }

    public DataItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    public boolean isNoContent() {
        return noContent;
    }

    public void setNoContent(boolean noContent) {
        this.noContent = noContent;
    }

}
