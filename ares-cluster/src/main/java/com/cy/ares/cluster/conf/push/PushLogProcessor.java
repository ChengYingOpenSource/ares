package com.cy.ares.cluster.conf.push;

import java.util.List;

import com.cy.ares.cluster.conf.ConfCoordinator;
import com.cy.ares.cluster.conf.ConfPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.ares.cluster.client.instance.ClientChannelCenter;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.protocol.DataItem;
import com.cy.ares.spcp.protocol.PushResp;

import lombok.Data;

/**
 * TODO 日志增加缓存写入
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年7月8日 下午3:27:08
 * @version V1.0
 */
public class PushLogProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PushLogProcessor.class);
    
    // 用于重推, @Note 暂时不重推
    private ConfCoordinator confCoordinator;

    public PushLogProcessor(ConfCoordinator cd) {
        this.confCoordinator = cd;
    }

    // failed handler, 默认重试间隔时间=3s,重试一次，失败即放弃;
    public void addFailed(PushFailedData pfd) {
        try {
            logger.info("push failed!,data={}", JSON.toJSONString(pfd));
            String instanceId = pfd.getInstanceId();
            ClientChannelCenter channelCenter = confCoordinator.getChannelCenter();
            ClientInstanceInfo ccInfo = channelCenter.findInstanceInfo(instanceId);
            if (ccInfo == null) {
                logger.warn("instanceId={},can't find ClientInstanceInfo", instanceId);
                return;
            }
            ConfPersistence cp = this.confCoordinator.getConfPersistence();
            PushResp resp = pfd.getResp();
            List<DataItem> items = resp.getItems();
            String failedMsg = pfd.getFailedMsg();
            cp.saveConfPushLog(false, items, ccInfo, failedMsg);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        
    }

    public void addSuccess(PushSuccessData pfd) {
        try {
            logger.info("push success!,data={}", JSON.toJSONString(pfd));
            String instanceId = pfd.getInstanceId();
            ClientChannelCenter channelCenter = confCoordinator.getChannelCenter();
            ClientInstanceInfo ccInfo = channelCenter.findInstanceInfo(instanceId);
            if (ccInfo == null) {
                logger.warn("instanceId={},can't find ClientInstanceInfo", instanceId);
                return;
            }
            
            ConfPersistence cp = this.confCoordinator.getConfPersistence();
            String pushMsg = "SUCCESS";
            PushResp resp = pfd.getResp();
            List<DataItem> items = resp.getItems();
            cp.saveConfPushLog(true, items, ccInfo, pushMsg);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Data
    public static class PushSuccessData {
        private PushResp resp;
        private String instanceId;
        private long pushTime;

        public PushSuccessData(PushResp resp, String instanceId) {
            this.resp = resp;
            this.instanceId = instanceId;
            this.pushTime = System.currentTimeMillis();
        }
    }

    @Data
    public static class PushFailedData {
        private PushResp resp;
        private String instanceId;
        private long pushTime;
        private String failedMsg;

        public PushFailedData(PushResp resp, String instanceId, String failedMsg) {
            this.resp = resp;
            this.instanceId = instanceId;
            this.failedMsg = failedMsg;
            this.pushTime = System.currentTimeMillis();
        }
    }
}
