package com.cy.onepush.dcommon.raft.transport;

import com.alibaba.fastjson.JSON;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.raft.NetNode;
import com.cy.onepush.dcommon.raft.RFCluster;
import com.cy.onepush.dcommon.raft.RaftContainer;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);
    private OkHttpClient client = new OkHttpClient.Builder().connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
        .connectTimeout(3, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS).build();

    private RaftContainer container;

    public Sender(RaftContainer container) {
        this.container = container;
    }

    public int broadcast(MEvent evnet) {

        String bodystr = JSON.toJSONString(evnet);

        RFCluster cluster = container.getRfCluster();
        String ulr = container.getConfig().getEventUrl();
        int num = 0;
        for (NetNode netNode : cluster.getClusterNode()) {

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), bodystr);
            Request.Builder requestBuilder = new Request.Builder().post(body);

            Request request = requestBuilder.url(realUrl(netNode, ulr)).build();
            try {
                Response response = client.newCall(request).execute();
                // TODO 是否确认返回信息
                num++;
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return num;
    }

    public String realUrl(NetNode netNode, String eventUrl) {

        String url = "http://" + netNode.getIp() + ":" + netNode.getPort() + "/" + eventUrl;
        return url;

    }

}
