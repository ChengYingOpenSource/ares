package com.cy.cuirass.raft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.cuirass.cluster.DefClusterListener;

import akka.actor.ActorRef;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;


/**
 * 用于对 master 节点的判断, 如果是master remove,则重新进行选举 ;
 * 可以监听之后，进行请求确认; 
 * 
 */
public class RaftClusterListener extends DefClusterListener{
    
    private static final Logger logger = LoggerFactory.getLogger(RaftClusterListener.class);
    
    // 把需要的消息发送给 NodeActor; 
    private ActorRef nodeActor;
    
    public  RaftClusterListener(ActorRef ref){
        this.nodeActor = ref ;
    }
    
    @Override
    public void memberRemoved(MemberRemoved mr) {
        logger.info("MemberRemoved event={}",mr.member().address());    
        this.nodeActor.tell(mr, ActorRef.noSender());
    }
    
    @Override
    public void memberUp(MemberUp up) {
        logger.info("MemberUp event={}",up.member().address());    
        this.nodeActor.tell(up, ActorRef.noSender());
    }
    
    @Override
    public void unReachableMember(UnreachableMember um) {
        logger.info("UnreachableMember event={}",um.member().address());    
        this.nodeActor.tell(um, ActorRef.noSender());
    }
}


