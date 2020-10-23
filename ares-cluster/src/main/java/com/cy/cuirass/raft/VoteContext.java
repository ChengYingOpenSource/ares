package com.cy.cuirass.raft;

import java.util.HashMap;
import java.util.Map;

import com.cy.cuirass.raft.protocol.VoteResp;

public class VoteContext {

    private long term = 0;

    private int count = 0;

    private Map<String, VoteResp> voteDetail = new HashMap<>();

    public long getTerm() {
        return term;
    }

    public VoteContext setTerm(long term) {
        this.term = term;
        return this;
    }

    public int getCount() {
        return count;
    }
    
    public synchronized VoteContext addCount(int num) {
        this.count += num;
        return this;
    }

    public Map<String, VoteResp> getVoteDetail() {
        return voteDetail;
    }

    public VoteContext setVoteDetail(Map<String, VoteResp> voteDetail) {
        this.voteDetail = voteDetail;
        return this;
    }

}
