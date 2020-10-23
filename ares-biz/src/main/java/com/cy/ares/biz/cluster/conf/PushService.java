package com.cy.ares.biz.cluster.conf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.core.manager.Ares2ConfManager;
import com.cy.ares.spcp.cst.DataIdActionCst;
import com.cy.ares.spcp.protocol.DataGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.cy.ares.biz.admin.ConfPushAdminService;
import com.cy.ares.biz.admin.domain.ConfPushDTO;
import com.cy.ares.biz.cluster.AresCluster;
import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.cluster.notify.ICallback;
import com.cy.ares.cluster.notify.ServerCluster;
import com.cy.ares.common.domain.BasicBO;
import com.cy.ares.common.error.AresHandlerException;
import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.spcp.protocol.DataItem;

/**
 * 先push ,确认 notify收到;
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年6月6日 上午11:30:10
 * @version V1.0
 */
@Service
public class PushService {

    private static final Logger logger = LoggerFactory.getLogger(PushService.class);

    @Autowired
    private ConfPushAdminService confAdminService;

    @Autowired
    private AresCluster aresCluster;
    
    @Autowired
    private Ares2ConfManager ares2ConfManager;
    
    @Autowired
    private DataSourceTransactionManager transactionManager ;

    public boolean pushAppConf(ConfPushDTO confPush, BasicBO basicBO) {
        basicBO = basicBO == null ? new BasicBO() : basicBO;
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
        TransactionStatus status = transactionManager.getTransaction(def); 
        try {
            Ares2ConfDO confdo = confAdminService.mergeConfPush(confPush, basicBO);
            boolean f = push(confdo,new ICallback<Object>() {
                @Override
                public Object success(Object obj) {
                    transactionManager.commit(status);
                    ConfPath clusterPath = new ConfPath();
                    MbeanUtils.copyProperties(confPush, clusterPath);
                    int u = ares2ConfManager.hasNotEqualRecord(clusterPath, confdo.getGmtModified());
                    return u > 0?Boolean.FALSE:Boolean.TRUE;
                }
                @Override
                public void failed(Object obj, Exception e) {
                    transactionManager.rollback(status);
                }
            });
            if(!f) {
                throw new AresHandlerException("push 失败！");
            }
        } catch (Exception e) {
            if(!status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
        } 
        return true;
    }

    public boolean pushBatch(DataGroupKey dataGroupKey,List<Ares2ConfDO> confdos, ICallback callback) {
        logger.info("push batch,dataGroupKey=[{}]", dataGroupKey);
        ServerCluster cluster = aresCluster.getClusterManager().getCluster();
        List<DataItem> diList = new ArrayList<>();
        for (Ares2ConfDO ce : confdos) {
            DataItem di = new DataItem();
            Date gmt = ce.getGmtModified();
            if(gmt == null){
                ce.setGmtModified(new Date());
            }
            // 包含traceId
            MbeanUtils.copyProperties(ce, di);
            di.setAction(DataIdActionCst.MERGE);
            diList.add(di);
        }
        boolean f = cluster.pubBatch(dataGroupKey,diList,callback);
        if (!f) {
            logger.warn("push batch failed!conf={}",JSON.toJSONString(confdos));
        }
        return f;
    }

    public boolean pushDel(Ares2ConfDO confdo,ICallback callback) {
        return push0(DataIdActionCst.DEL,confdo,callback);
    }

    public boolean push(Ares2ConfDO confdo,ICallback callback) {
        return push0(DataIdActionCst.MERGE,confdo,callback);
    }

    // 不要改动 confDO
    private boolean push0(String actionCst,Ares2ConfDO confdo, ICallback callback) {
        logger.info("push actionCst={},dataId={},appCode={},envCode={},clusterCode={}", actionCst,confdo.getDataId(), confdo.getAppCode(), confdo.getEnvCode(), confdo.getClusterCode());
        ServerCluster cluster = aresCluster.getClusterManager().getCluster();
        DataItem di = new DataItem();
        Date gmt = confdo.getGmtModified();
        if(gmt == null){
            confdo.setGmtModified(new Date());
        }
        // 包含traceId
        MbeanUtils.copyProperties(confdo, di);
        di.setAction(actionCst);
        if(DataIdActionCst.DEL.equals(actionCst)){
            di.setContent(null);
            di.setContentSize(0);
            di.setDigest(null);
        }
        boolean f = cluster.pub(di,callback);
        if (!f) {
            logger.warn("push failed!conf={}",JSON.toJSONString(confdo));
        }
        return f;
    }

}
