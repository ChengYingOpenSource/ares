package com.cy.onepush.dcommon.raft.message;
import com.cy.onepush.dcommon.raft.NetNode;

import lombok.Data;


@Data
public class AppendEntry {
    
    private NetNode leader;
    
    private long leaderTerm;
    
}
