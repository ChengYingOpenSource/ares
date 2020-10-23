package com.cy.onepush.dcommon.raft;

import lombok.Data;

@Data
public class NetNode {

    private String ip;

    private int port;

    public int hashCode() {

        return ip.hashCode() | port;

    }

    public boolean equals(Object node) {

        if (node == null || node.getClass() != this.getClass()) {
            return false;
        }
        NetNode netNode = (NetNode)node;
        if (this.ip.equals(netNode.getIp()) && this.port == netNode.getPort()) {
            return true;
        }
        return false;
    }

}
