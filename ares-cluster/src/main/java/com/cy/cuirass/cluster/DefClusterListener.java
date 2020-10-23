package com.cy.cuirass.cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.LeaderChanged;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;



public class DefClusterListener implements ClusterListener{
    
    private static final Logger logger = LoggerFactory.getLogger(DefClusterListener.class);
    
    @Override
    public void clusterState(CurrentClusterState state) {
        logger.info("CurrentClusterState event={}",state.getLeader());
    }
    
    @Override
    public void leaderChanged(LeaderChanged leaderChanged) {
        logger.info("LeaderChanged event={}",leaderChanged.getLeader());
    }
    
    @Override
    public void memberEvent(MemberEvent me) {
        logger.info("MemberEvent event={}",me.member().address());    
    }
    
    @Override
    public void memberRemoved(MemberRemoved mr) {
        logger.info("MemberRemoved event={}",mr.member().address());    
    }
    
    @Override
    public void memberUp(MemberUp up) {
        logger.info("MemberUp event={}",up.member().address());    
    }
    
    @Override
    public void unReachableMember(UnreachableMember um) {
        logger.info("UnreachableMember event={}",um.member().address());    
    }
}
