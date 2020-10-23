package com.cy.cuirass.app;
import com.cy.cuirass.cluster.AkCluster;



public class AppContext {
    
    private AkCluster cluster;

    
    private AppInfo info;
    
    public AkCluster getCluster() {
        return cluster;
    }

    public AppContext setCluster(AkCluster cluster) {
        this.cluster = cluster;
        return this;
    }

    public AppInfo getInfo() {
        return info;
    }

    public AppContext setInfo(AppInfo info) {
        this.info = info;
        return this;
    }
    
    
}
