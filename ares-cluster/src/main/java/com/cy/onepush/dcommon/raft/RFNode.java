package com.cy.onepush.dcommon.raft;

import java.util.Set;

import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.raft.election.ElectionTerm;
import com.cy.onepush.dcommon.raft.election.HeartbeatExecutor;
import com.cy.onepush.dcommon.raft.message.AppendEntry;
import com.cy.onepush.dcommon.raft.message.VoteEntry;
import com.cy.onepush.dcommon.raft.message.VoteRespEntry;

import lombok.Data;

@Data
public class RFNode {

    private RaftContainer raftContainer;

    // 本node，自己的NetNode
    private NetNode selfNetNode;

    private long term;
    private long voteNum;

    private StateEnum state;
    private volatile boolean onoff = false;

    // election term
    private ElectionTerm electionTerm;

    // 心跳接受和发送, 如果没有心跳了则update leader=null;
    private HeartbeatExecutor hbExecutor;

    void start() {
        electionTerm = new ElectionTerm(new Runnable() {

            @Override
            public void run() {
                onoff = true;
                long lazyTime = raftContainer.getConfig().getLazyTime();
                try {
                    Thread.sleep(lazyTime);
                } catch (InterruptedException e) {
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
                while (onoff) {

                    NetNode leader = findLeader();

                    if (state == StateEnum.follwer && leader == null) {
                        hbExecutor.stopHeartCheck();
                        // start election term;
                        boolean isOK = electionTerm.wait4Candidate();
                        if (!isOK) {
                            // 说明已经有leader了;
                            continue;
                        }

                        // 成为candidate, start term
                        VoteEntry voteEntry = new VoteEntry();
                        // 每次发起投票， term++
                        voteEntry.setTerm(voteEntry.getTerm() + 1);

                        voteNum = 0;
                        voteNum++;
                        state = StateEnum.candidate;
                        // 发送投票通知
                        int ballot = sendVote(voteEntry);
                        int timeSeconds = raftContainer.getConfig().getVoteResposeTimeout();
                        isOK = electionTerm.wait4VoteResponse(ballot, timeSeconds);
                        if (!isOK) {
                            // 时间耗尽但是没有接受到结果
                            // TODO 打印日志
                            continue;
                        }
                        isOK = canLeaderSelf();
                        if (isOK) {
                            raftContainer.updateLeader(selfNetNode);
                            // 心跳
                            hbExecutor.toHeart();
                        }
                    } else {
                        electionTerm.wait4Candidate();
                        hbExecutor.startHeartCheck();

                    }
                }
            }
        });
        electionTerm.start();

    }

    /*
     * 返回发出去的请求数量
     */
    public int sendVote(VoteEntry voteEntry) {

        MEvent event = new MEvent();
        event.setEventName(RFEventCst.VOTE_NOTICE);
        event.setEvent(voteEntry);

        int num = this.raftContainer.getSender().broadcast(event);

        return num;

    }

    public void receiveVote(VoteEntry voteEntry) {

        // 接受投票 & 进行响应
        // 更新自己的term

        // sendVoteResponse()

    }

    public void voteResponse(VoteRespEntry respEntry) {

        electionTerm.wakeup4Vote();

    }

    public NetNode findLeader() {

        NetNode leader = raftContainer.getRfCluster().getLeader();
        if (leader != null) {
            return leader;
        }

        return null;
    }

    public boolean canLeaderSelf() {

        Set<NetNode> clusterNode = raftContainer.getRfCluster().getClusterNode();

        int size = clusterNode.size();
        if (size <= 1) {
            return true;
        }

        int threshold = size / 2 + 1;
        if (voteNum >= threshold) {
            return true;
        }
        return false;
    }

    public void heartbeatListener(AppendEntry heartbeat) {

        long leaderTerm = heartbeat.getLeaderTerm();

    }

    public void stop() {
        this.onoff = false;
    }

}
