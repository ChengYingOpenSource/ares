package com.cy.ares.spcp.protocol;

import static com.cy.ares.spcp.cst.ConfigCst.*;

public class DataClusterKey {
    
    private String namespaceCode;
    private String appCode;
    private String envCode;
    private String clusterCode;
    
    public String key() {
        StringBuilder sb = new StringBuilder();
        sb.append(namespaceCode);
        sb.append(semicolon);
        sb.append(appCode);
        sb.append(semicolon);
        sb.append(envCode);
        sb.append(semicolon);
        sb.append(clusterCode);
        return sb.toString();
    }
    
    public String getNamespaceCode() {
        return namespaceCode;
    }
    
    public void setNamespaceCode(String namespaceCode) {
        this.namespaceCode = namespaceCode;
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
    
    
    
    
}
