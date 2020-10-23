package com.cy.ares.spcp.protocol;

public class DataGroupKey {
    
    public static final String ALL_GROUP =  "*";
    public static final String MULTI_GROUP =  "?";
    
    private String namespaceCode;
    private String appCode;
    private String envCode;
    private String clusterCode;
    
    // 可以是 * 
    private String group = ALL_GROUP;
    
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    

}
