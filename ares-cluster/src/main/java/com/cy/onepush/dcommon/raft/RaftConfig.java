package com.cy.onepush.dcommon.raft;
import java.util.*;

import lombok.Data;


/**
 * 暂时无需那么多配置
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年5月25日 下午4:57:12
 * @version V1.0
 */
@Data
public class RaftConfig {
    
    
    private String clusterName ;
    
    // event url
    private String eventUrl;
    
    // cluster nodes
    private Set<NetNode> netNodes;
    
    
    private long lazyTime;
    
    private int voteResposeTimeout = 3;
    
    
    private int heartbeatIntervalMs = 3000 ;
    private int heartbeatTimeoutMs = 10000 ;
    
    
}
