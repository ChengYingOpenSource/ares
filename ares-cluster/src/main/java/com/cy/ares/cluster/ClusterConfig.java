package com.cy.ares.cluster;
import com.cy.cuirass.app.AppInfo;

import lombok.Data;


@Data
public class ClusterConfig {
    
    private int serverPort = 30333;
    
    private int webSocketPort = 30336;
    
    private int idleTimeoutSecond = 30;
    
    private int maxHandleThread = 16;
    
    private AppInfo clusterInnerInfo = new AppInfo();
    
    private boolean enableMemdb = false;
    
    // 缓存相关
    private int cacheSize = 300000;
    private int compressThreshold = 1024;

    // redis 配置
    // mode=&nodes=["","",""]&p_xx=&p_xx=
    private String redisLink;
}
