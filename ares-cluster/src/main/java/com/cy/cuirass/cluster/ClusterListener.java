package com.cy.cuirass.cluster;

import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.LeaderChanged;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;

public interface ClusterListener {

    public void memberUp(MemberUp up);

    public void unReachableMember(UnreachableMember um);

    public void memberRemoved(MemberRemoved mr);

    public void memberEvent(MemberEvent me);

    public void leaderChanged(LeaderChanged leaderChanged);

    public void clusterState(CurrentClusterState state);

}
