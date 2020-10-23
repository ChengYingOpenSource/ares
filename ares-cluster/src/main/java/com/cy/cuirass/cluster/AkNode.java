package com.cy.cuirass.cluster;

public class AkNode {

    private String ip;

    private int port;

    private String hostName;

    private boolean hostNameEnable;

    public String linkAddress() {
        return hostNameEnable ? hostName : ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port<=0?80:port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isHostNameEnable() {
        return hostNameEnable;
    }

    public void setHostNameEnable(boolean hostNameEnable) {
        this.hostNameEnable = hostNameEnable;
    }
    
    
    
}
