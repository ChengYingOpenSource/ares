package com.cy.ares.spcp.client;

import org.apache.commons.lang3.StringUtils;

/**
 * 服务器信息;
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年4月29日 下午1:37:13
 * @version V1.0
 */
public class NodeInfo {

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        String h = this.host == null ? StringUtils.EMPTY : this.host;
        Integer p = this.port;
        return h.concat(p.toString()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        NodeInfo n = (NodeInfo) obj;
        String host = n.getHost();
        int port = n.getPort();

        return StringUtils.equals(host, this.host) && port == this.port;
    }

}
