package com.cy.cuirass.raft.protocol;
import java.io.Serializable;
import java.util.*;

import com.cy.onepush.dcommon.event.MEvent;



public class VoteResp extends MEvent implements Serializable{
    
    
    private static final long serialVersionUID = -101389383009093582L;

    private boolean vote ;
    
    private long fromTerm;
    
    private String fromInfo;
    
    
    public VoteResp(){}
    
    
    public VoteResp(long fromTerm,boolean vote){
        this.fromTerm = fromTerm;
        this.vote = vote;
    }

    public boolean isVote() {
        return vote;
    }
    
    public boolean getVote() {
        return vote;
    }

    public VoteResp setVote(boolean vote) {
        this.vote = vote;
        return this;
    }

    public long getFromTerm() {
        return fromTerm;
    }

    public VoteResp setFromTerm(long fromTerm) {
        this.fromTerm = fromTerm;
        return this;
    }

    public String getFromInfo() {
        return fromInfo;
    }

    public VoteResp setFromInfo(String fromInfo) {
        this.fromInfo = fromInfo;
        return this;
    }
    
    
    
}
