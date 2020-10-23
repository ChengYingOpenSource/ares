package com.cy.cuirass.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.LeaderChanged;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;

public class AkClusterListener extends AbstractActor {

    private static final Logger log = LoggerFactory.getLogger(AkClusterListener.class);

    private Cluster cluster = Cluster.get(getContext().getSystem());

    private ClusterListener listener;

    public AkClusterListener(ClusterListener listener) {
        this.listener = listener;
    }

    // subscribe to cluster changes
    @Override
    public void preStart() {
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class,MemberRemoved.class,MemberUp.class);
        cluster.subscribe(getSelf(), LeaderChanged.class);
    }

    // re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
        
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MemberUp.class, mUp -> {
            this.listener.memberUp(mUp);
            
        }).match(UnreachableMember.class, mUnreachable -> {
            this.listener.unReachableMember(mUnreachable);
            
        }).match(MemberRemoved.class, mRemoved -> {
            this.listener.memberRemoved(mRemoved);
            
        }).match(LeaderChanged.class, leaderChanged -> {
            // leader = leaderChanged.getLeader();
            this.listener.leaderChanged(leaderChanged);

        }).match(CurrentClusterState.class, state -> {
            this.listener.clusterState(state);
            // leaderAddress = state.leader

        }).match(MemberEvent.class, message -> {
            this.listener.memberEvent(message);

        }).build();
    }
}