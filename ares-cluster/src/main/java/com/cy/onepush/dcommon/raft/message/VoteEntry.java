package com.cy.onepush.dcommon.raft.message;
import java.util.*;

import com.cy.onepush.dcommon.raft.NetNode;
import com.cy.onepush.dcommon.raft.StateEnum;

import lombok.Data;


@Data
public class VoteEntry {
    
    private long term ;
    
    private NetNode netNode;
    
    private StateEnum state;
}
