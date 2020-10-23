package com.cy.cuirass.raft;

public interface RaftLeaderListener {
    
    
    
    /**
     * 某个Node 选举成功
     */
    public void electionUp();
    
    /**
     * 某个Node 退位 
     */
    public void electionLeave();
    
    
    
    
}
