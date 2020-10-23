package com.cy.ares.spcp.client;

import io.netty.channel.Channel;

public interface PoolManager {

    boolean newClient();

    void releaseClient();

    boolean refreshClient(NodeInfo node);

    boolean refreshClient();

    // 目前只有netty , channel暂不封装
    Channel channel();

    void releaseChannel(Channel ch);

    void close();

    boolean isClose();

    void init();
}
