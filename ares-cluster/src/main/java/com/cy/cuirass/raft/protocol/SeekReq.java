package com.cy.cuirass.raft.protocol;

import java.io.Serializable;

import com.cy.onepush.dcommon.event.MEvent;

/**
 * node 启动时，主动进行一次leader的寻找; 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年5月30日 下午2:16:21
 * @version V1.0
 */
public class SeekReq extends MEvent implements Serializable{
    
    private static final long serialVersionUID = 8277267811646138815L;
    
}
