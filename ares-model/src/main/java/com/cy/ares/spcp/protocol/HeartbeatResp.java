package com.cy.ares.spcp.protocol;

import com.cy.ares.spcp.net.protocol.NetResponse;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;

public class HeartbeatResp extends NetResponse {
    
    static {
        ProtocolCenter.registe(Resp.heart, HeartbeatResp.class);
    }
    
    public HeartbeatResp(){
        super(Resp.heart);
    }
    
    // 服务器状态, 200=OK 300=亚健康 400=不健康 500=极度不健康
    private int status = 200;

    // 满分 100;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
