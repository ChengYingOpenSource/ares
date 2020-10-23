package com.cy.ares.spcp.context;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import com.cy.ares.spcp.client.NodeInfo;
import com.google.common.base.Preconditions;
/**
 * @author
 * */
@Data
public class SpcpConfig {

    private final static String sepeator = ",";

    /**
     *
     *   ip:port,ip:port格式
     */

    private String serverAddr;

    /** config 解析之后的 node信息;
     *
     */
    private List<NodeInfo> confNodes;

    /** 初始化，第一次获取配置时的timeout
     *
     */
    private int initReadTimeout = 10000;

    private int readTimeout = 5000;

    private int connectTimeout = 3000;

    /** 每个通道池的最大数量
     *
     */
    private int maxChannel = 2;

    /** 每个通道池初始化次数
     *
     */
    private int maxPoolTry = 3;

    /** server返回信息的异步处理线程;
     *
     */
    private int respExecuteMaxThread = 16;

    /** 心跳间隔 ms
     *
     */
    private int heartbeatInterval = 3000;

    private int heartbeatRespTimeout = 3000;

    private int heartbeatMaxFailedCount = 3;

    /** 是否进行定时比较&更新
     * , TODO 默认false
     */
    private boolean compared = false;
    /** 默认=30秒
     *
     */
    private int compareTimeIntervalMs = 30000;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {

        Preconditions.checkArgument(StringUtils.isNotBlank(serverAddr),"serverAddr不能为空!");
        this.serverAddr = serverAddr;
        this.confNodes = new ArrayList<>();
        String[] nodeArr = serverAddr.split(sepeator);
        for(String p:nodeArr){
            String[] info = p.split(":");
            NodeInfo nInfo = new NodeInfo();
            nInfo.setHost(info[0]);
            nInfo.setPort(Integer.parseInt(info[1]));
            this.confNodes.add(nInfo);
        }
    }

    public int getReadTimeout() {
        if(readTimeout <= 0){
            return 5000;
        }
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getInitReadTimeout() {
        if(initReadTimeout <= 0){
            return 5000;
        }
        return initReadTimeout;
    }

    public void setInitReadTimeout(int initReadTimeout) {
        this.initReadTimeout = initReadTimeout;
    }
}
