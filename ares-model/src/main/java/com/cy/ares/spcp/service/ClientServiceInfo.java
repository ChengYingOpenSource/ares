package com.cy.ares.spcp.service;

import com.cy.ares.spcp.service.SubscibeInfo;

import java.util.*;

/**
 * 该类信息都由上层信息传入;
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月5日 下午11:14:02
 * @version V1.0
 */
public class ClientServiceInfo {

    private String healthCheckUrl;

    // 类似 eureka点击 intanceId 跳转的页面
    private String homePageUrl;

    // slb 或 其它gateway地址
    private String proxyUrl;

    // 当前服务自定义metaInfo
    private Map<String, Object> serviceMetaInfo;

    // 订阅的其它App信息， 不一定在同一个namespace和env下;
    private List<SubscibeInfo> subscibeList;

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public Map<String, Object> getServiceMetaInfo() {
        return serviceMetaInfo;
    }

    public void setServiceMetaInfo(Map<String, Object> serviceMetaInfo) {
        this.serviceMetaInfo = serviceMetaInfo;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public List<SubscibeInfo> getSubscibeList() {
        return subscibeList;
    }

    public void setSubscibeList(List<SubscibeInfo> subscibeList) {
        this.subscibeList = subscibeList;
    }

}
