package com.cy.cuirass.raft.protocol;
import java.io.Serializable;

import com.cy.cuirass.cluster.AkNode;
import com.cy.onepush.dcommon.event.MEvent;



public class HtEntryReq extends MEvent implements Serializable{
    
    private static final long serialVersionUID = -2928426381195994349L;

    private long term;
    
    private AkNode node;
    
    public HtEntryReq(){}
    
    public HtEntryReq(long term){
        this.term = term;
    }
    
    public HtEntryReq(long term,AkNode node){
        this.term = term;
        this.node = node;
    }
    
    public long getTerm() {
        return term;
    }

    public HtEntryReq setTerm(long term) {
        this.term = term;
        return this;
    }

    public AkNode getNode() {
        return node;
    }

    public HtEntryReq setNode(AkNode node) {
        this.node = node;
        return this;
    }
    
    
    
    
}
