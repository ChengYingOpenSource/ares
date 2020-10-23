package com.cy.cuirass.raft.protocol;
import java.io.Serializable;

import com.cy.onepush.dcommon.event.MEvent;



public class VoteReq extends MEvent implements Serializable{
    
    private static final long serialVersionUID = -3842762183975881220L;
    
    private long term;

    
    public VoteReq(){}
    
    public VoteReq(long term){
        this.term = term;
    }
    
    public long getTerm() {
        return term;
    }

    public VoteReq setTerm(long term) {
        this.term = term;
        return this;
    }
    
    
}
