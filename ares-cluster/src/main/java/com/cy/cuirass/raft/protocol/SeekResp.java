package com.cy.cuirass.raft.protocol;
import java.io.Serializable;

import com.cy.onepush.dcommon.event.MEvent;



public class SeekResp extends MEvent implements Serializable{
    
    private static final long serialVersionUID = 6876907627082735198L;
    
    private long term;
    
    public SeekResp(){}
    
    public SeekResp(long term){
        this.term = term;
    }
    
    public long getTerm() {
        return term;
    }

    public SeekResp setTerm(long term) {
        this.term = term;
        return this;
    }
    
    
    
}
