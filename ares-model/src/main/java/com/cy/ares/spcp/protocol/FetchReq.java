package com.cy.ares.spcp.protocol;

import java.util.Set;

import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;

/**
 * 主动获取一次全量conf & 附带 client 基本注册信息
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年4月29日 下午2:18:28
 * @version V1.0
 */
public class FetchReq extends NetRequest {

    static {
        ProtocolCenter.registe(Req.conf_registe_fetch, FetchReq.class);
    }

    public FetchReq() {
        super(ProtocolCenter.Req.conf_registe_fetch);
    }
    // group;dataId 
    private Set<String> keys;
    
    private DataGroupKey groupKey; 
    
    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public DataGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(DataGroupKey groupKey) {
        this.groupKey = groupKey;
    }
}
