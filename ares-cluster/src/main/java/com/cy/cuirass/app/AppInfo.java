package com.cy.cuirass.app;

import com.cy.cuirass.raft.RaftLeaderListener;

import lombok.Data;

@Data
public class AppInfo {

    // 集群地址
    private String clusterName;
    // 集群node监听节点
    private int port = 25510;

    // 默认提供 seed-node
    // 格式: ip:port,ip:port
    private String seedNodes;

    private boolean raftEnable = false;
    
    private boolean hostNameEnable = false;
    
    private int raftWaitTime = 3;
    private int raftWaitIncr = 2;
    
    private boolean seekNumCheck = false;
    
    private RaftLeaderListener leaderListeners;
    
}
