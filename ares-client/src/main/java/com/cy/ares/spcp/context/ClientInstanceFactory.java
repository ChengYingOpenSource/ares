package com.cy.ares.spcp.context;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.cy.ares.spcp.client.network.InetUtils;
import com.cy.ares.spcp.client.network.InetUtils.HostInfo;
import com.cy.ares.spcp.cst.ConfigCst;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;

public class ClientInstanceFactory {

    public static ClientInstanceInfo instanceClient(String appCode, String envCode) {
        return instanceClient(ConfigCst.NAMESPACE_DEF, appCode, envCode, ConfigCst.CLUSTER_DEF);
    }

    public static ClientInstanceInfo instanceClient(String namespaceCode, String appCode, String envCode,
        String clusterCode) {

        namespaceCode = StringUtils.isBlank(namespaceCode) ? ConfigCst.NAMESPACE_DEF : namespaceCode;
        clusterCode = StringUtils.isBlank(clusterCode) ? ConfigCst.CLUSTER_DEF : clusterCode;

        HostInfo info = InetUtils.getFirstNonLoopbackHostInfo();

        ClientInstanceInfo ci = new ClientInstanceInfo();

        StringBuilder sb = new StringBuilder();
        sb.append(info.getIpAddress());
        sb.append(";");
        sb.append(info.getHostname());
        sb.append(";");
        // 5位随机数
        sb.append(Math.abs(RandomUtils.nextInt(0, 100000)));
        sb.append(";");
        String time = String.valueOf(System.currentTimeMillis());
        time = time.substring(5, time.length());
        sb.append(time);

        ci.setNamespaceCode(namespaceCode);
        ci.setInstanceId(sb.toString());
        ci.setHostName(info.getHostname());
        ci.setIpAddr(info.getIpAddress());
        ci.setAppCode(appCode);
        ci.setEnvCode(envCode);
        ci.setClusterCode(clusterCode);
        return ci;
    }

}
