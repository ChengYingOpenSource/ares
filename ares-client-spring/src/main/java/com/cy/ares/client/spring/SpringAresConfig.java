package com.cy.ares.client.spring;
import java.util.*;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.cy.ares.spcp.context.SpcpConfig;
import com.cy.ares.spcp.cst.ConfigCst;


@ConfigurationProperties(prefix = "ares.config")
@Component
public class SpringAresConfig extends SpcpConfig{
    
    private boolean enable = true;
    private boolean bootstrap = true;
    
    private String appCode ;
    private String envCode ;
    private String clusterCode = ConfigCst.CLUSTER_DEF;
    private String namespaceCode = ConfigCst.NAMESPACE_DEF;

    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    public boolean isBootstrap() {
        return bootstrap;
    }
    public void setBootstrap(boolean bootstrap) {
        this.bootstrap = bootstrap;
    }
    public String getAppCode() {
        return appCode;
    }
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
    public String getEnvCode() {
        return envCode;
    }
    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }
    public String getClusterCode() {
        return clusterCode;
    }
    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
    }
    public String getNamespaceCode() {
        return namespaceCode;
    }
    public void setNamespaceCode(String namespaceCode) {
        this.namespaceCode = namespaceCode;
    }
    
    
}
