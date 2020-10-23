package com.cy.ares.spcp.protocol;

import static com.cy.ares.spcp.cst.ConfigCst.*;
/**
 * 客户端instance信息
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月5日 下午9:31:52
 * @version V1.0
 */
public class ClientInstanceInfo {

    // ip+hostName+随机数(10位)
    private String instanceId;
    private String ipAddr;
    private String hostName;

    private String namespaceCode = NAMESPACE_DEF;
    private String appCode;
    private String envCode;
    private String clusterCode = CLUSTER_DEF;
    
    // 如果client提供 openhost功能
    private int serverPort;
    
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
    
    public String getHostName() {
        return hostName;
    }
    
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
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

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}
