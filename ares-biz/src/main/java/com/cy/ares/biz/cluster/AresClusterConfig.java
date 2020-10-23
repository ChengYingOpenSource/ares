package com.cy.ares.biz.cluster;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.cy.ares.cluster.ClusterConfig;



@ConfigurationProperties(prefix = "cluster.config")
@Component
public class AresClusterConfig extends ClusterConfig{
    
    
    
    
}
