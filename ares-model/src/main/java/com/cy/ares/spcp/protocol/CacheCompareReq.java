package com.cy.ares.spcp.protocol;

import com.cy.ares.spcp.net.protocol.NetRequest;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Req;

import java.util.HashMap;
import java.util.Set;

/**
 * 某group/所有 * 的cacheMd5
 * 
 * @author maoxq
 *
 * @Description
 * @date 2019年11月11日 下午2:18:28
 * @version V1.0
 */
public class CacheCompareReq extends NetRequest {

    static {
        ProtocolCenter.registe(Req.conf_compare, CacheCompareReq.class);
    }

    public CacheCompareReq() {
        super(Req.conf_compare);
    }

    private String cacheMd5;

    private DataGroupKey groupKey; 

    public DataGroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(DataGroupKey groupKey) {
        this.groupKey = groupKey;
    }

    public String getCacheMd5() {
        return cacheMd5;
    }

    public void setCacheMd5(String cacheMd5) {
        this.cacheMd5 = cacheMd5;
    }
}
