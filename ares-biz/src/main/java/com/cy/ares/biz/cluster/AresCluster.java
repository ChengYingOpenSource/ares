package com.cy.ares.biz.cluster;

import com.alibaba.fastjson.JSON;
import com.cy.ares.cluster.ClusterConfig;
import com.cy.ares.cluster.ClusterManager;
import com.cy.ares.cluster.conf.ConfPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AresCluster {
    private static final Logger logger = LoggerFactory.getLogger(AresCluster.class);
    @Autowired
    private AresClusterConfig aresClusterConfig;
    @Value("${spring.cloud.config.profile:}")
    private String profile;
    @Resource(name = "dbConf")
    private ConfPersistence dbConfPersist;
    private static ClusterManager clusterManager = new ClusterManager();
    private static volatile boolean init = false;

    public void start() {
        if (init) { return; }
        //aresClusterConfig.getClusterInnerInfo().setClusterName("ares2-local-test");
        ClusterConfig clusterConf = aresClusterConfig;
        boolean isMem = clusterConf.isEnableMemdb();
        // 选择h2数据库;
        ConfPersistence confPersistence = dbConfPersist;
        logger.info("cluster create conf={}", JSON.toJSONString(clusterConf));
        clusterManager.create(clusterConf, confPersistence);
        init = true;
    }

    public ClusterManager getClusterManager() {
        return clusterManager;
    }
}
