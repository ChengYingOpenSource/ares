package com.cy.cuirass.raft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO : 是否暴露 cluster 信息 CurrentClusterState封装下; 给 RaftLeaderListener
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年9月10日 下午5:52:50
 * @version V1.0
 */
public class DefRaftLeaderListener implements RaftLeaderListener{
    
    private static final Logger logger = LoggerFactory.getLogger(RaftClusterListener.class);
    
    @Override
    public void electionLeave() {
        logger.warn("leader is leave!");
    }
    
    
    @Override
    public void electionUp() {
        logger.warn("leader is up!");
    }
    
}
