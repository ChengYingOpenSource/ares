package com.cy.onepush.dcommon.raft.message;
import java.util.*;

import lombok.Data;


@Data
public class VoteRespEntry {
    
    // 是不是给你投的票
    private boolean isU ; 
    
    // 投票者自己的term
    private long fromTerm ;
    
    // 投票者选择候选人的 term;
    private long voteDestTerm;
    
}
