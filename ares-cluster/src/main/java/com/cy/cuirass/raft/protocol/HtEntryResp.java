package com.cy.cuirass.raft.protocol;
import java.io.Serializable;

import com.cy.onepush.dcommon.event.MEvent;



public class HtEntryResp extends MEvent implements Serializable{
    
    private static final long serialVersionUID = -4710463225435534767L;
    
    private long term;
    
    
    public HtEntryResp(){}
    
    public HtEntryResp(long term){
        this.term = term;
    }
    
    
    public long getTerm() {
        return term;
    }

    public HtEntryResp setTerm(long term) {
        this.term = term;
        return this;
    }
    
    
    
}
