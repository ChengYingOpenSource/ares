package com.cy.onepush.dcommon.raft;
import java.util.*;

import lombok.Data;


@Data
public class RFCluster {
    
    private NetNode leader ;
    
    private Set<NetNode> clusterNode;
    
}
