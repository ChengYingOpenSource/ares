package com.cy.onepush.dcommon.raft;

import lombok.Getter;

/**
 * 
 * init -> find leader -> no leader -> follwer
 * (因为eureka通知的不及时性, 先由node自己find一次)
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年5月25日 下午5:25:44
 * @version V1.0
 */
@Getter
public enum StateEnum {
    
    
    init(-1) , follwer(2000) , leader(-1), candidate(2000);
    
    private long stateStart ;
    
    private long stateTimeout;
    
    StateEnum(long stateTimeout){
        this.stateTimeout = stateTimeout;
    }
    
    public void start(){
        this.stateStart = System.currentTimeMillis();
    }
    
}
