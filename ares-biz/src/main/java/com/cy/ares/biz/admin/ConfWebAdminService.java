package com.cy.ares.biz.admin;

import com.cy.ares.biz.admin.domain.AppRunEnvInfoDTO;
import com.cy.ares.biz.admin.domain.ConfOperatorType;
import com.cy.ares.biz.admin.domain.ConfPushDTO;
import com.cy.ares.biz.cluster.conf.PushService;
import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.cluster.notify.ICallback;
import com.cy.ares.common.domain.BasicBO;
import com.cy.ares.common.error.AresHandlerException;
import com.cy.ares.dao.common.model.*;
import com.cy.ares.dao.common.query.Ares2ClusterQuery;
import com.cy.ares.dao.common.query.Ares2ConfLogQuery;
import com.cy.ares.dao.core.manager.*;
import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.spcp.protocol.DataItem;
import com.cy.onepush.dcommon.async.Async;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

import static com.cy.ares.biz.util.IdentityHelper.getCurrentUserAccount;

@Service
public class ConfWebAdminService {

    private static final Logger logger = LoggerFactory.getLogger(ConfWebAdminService.class);

    @Autowired
    private Ares2ClusterManager ares2ClusterManager;

    @Autowired
    private Ares2ConfHistoryManager ares2ConfHistoryManager;

    @Autowired
    private Ares2ConfLogManager ares2ConfLogManager;

    @Autowired
    private Ares2ConfManager ares2ConfManager;

    @Autowired
    private Ares2EnvManager ares2EnvManager;

    @Autowired
    private ConfPushAdminService confPushAdminService;

    @Autowired
    private PushService pushService;

    private static Async async = new Async();

    public AppRunEnvInfoDTO appRunEnvInfo(String namespaceCode, String appCode) {
        // env + cluster
        Ares2ClusterQuery query = new Ares2ClusterQuery();
        query.createCriteria().andNamespaceCodeEqualTo(namespaceCode).andAppCodeEqualTo(appCode);
        List<Ares2ClusterDO> clusterList = ares2ClusterManager.selectByQuery(query);
        List<Ares2EnvDO> envList = ares2EnvManager.listUnderApp(namespaceCode, appCode);

        AppRunEnvInfoDTO runInfo = new AppRunEnvInfoDTO();
        runInfo.setNamespaceCode(namespaceCode);
        runInfo.setAppCode(appCode);
        Map<String, List<String>> envCluster = runInfo.getEnvCluster();

        for (Ares2ClusterDO clusterEle : clusterList) {
            String envCode = clusterEle.getEnvCode();
            List<String> cdo = envCluster.get(envCode);
            if (cdo == null) {
                cdo = new ArrayList<>();
                envCluster.put(envCode, cdo);
            }
            cdo.add(clusterEle.getClusterCode());
        }
        runInfo.setEnvList(envList);
        return runInfo;
    }

    // 取所有group下的 confInfo
    public List<Ares2ConfDO> appConfInfo(ConfPath clusterPath) {

        List<Ares2ConfDO> confDOs = ares2ConfManager.findDataItemsWithSize(clusterPath);

        return confDOs;
    }

    // 查询某个dataId的详情;

    public Ares2ConfDO confDetailInfo(long id) {
        return ares2ConfManager.selectByPrimaryKey(id);
    }

    public int deleteConfWithDirect(long id) {
        Ares2ConfDO confdo = ares2ConfManager.selectByPrimaryKey(id);
        if (confdo == null) {
            return 0;
        }
        Ares2ConfDO entity = new Ares2ConfDO();
        entity.setId(id);
        int u = ares2ConfManager.deleteByPrimaryKey(entity);
        confdo.setModifyUserAccount(getCurrentUserAccount());
        saveChangeLog(confdo, ConfOperatorType.del);
        return u;
    }

    @Autowired
    private DataSourceTransactionManager transactionManager;

    public boolean rollbackDataItem(Ares2ConfHistoryDO confHistoryDO) {
        BasicBO basicBO = new BasicBO();
        MbeanUtils.copyProperties(confHistoryDO, basicBO);
        basicBO.setUserAccount(getCurrentUserAccount());
        ConfPath confPath = new ConfPath();
        MbeanUtils.copyProperties(confHistoryDO, confPath);

        return mergeDataItem(ConfOperatorType.rollback, confPath, confHistoryDO.getDataId(), confHistoryDO.getContent(),
            confHistoryDO.getContentType(), confHistoryDO.getDesc(), basicBO);
    }

    // 添加成功，即发布
    public boolean mergeDataItemBatch(ConfOperatorType opeType, List<ConfPushDTO> confDataIdList, BasicBO basicBO) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        try {
            List<Ares2ConfDO> confdos = confPushAdminService.mergeConfPushBatch(confDataIdList, basicBO);
            // 在notify的时候保证 commit
            DataItem di = new DataItem();
            MbeanUtils.copyProperties(confdos.get(0), di);
            boolean f = pushService.pushBatch(di.groupKey(), confdos, new ICallback<Object>() {
                @Override
                public Object success(Object obj) {
                    // 提交有可能失败!
                    // TODO 没有进行比较，有并发问题
                    transactionManager.commit(status);
                    return Boolean.TRUE;
                }

                @Override
                public void failed(Object obj, Exception e) {
                    transactionManager.rollback(status);
                }
            });
            if (!f) {
                throw new AresHandlerException("push 失败！");
            }
            async.async(() -> {
                saveChangeLogBatch(confdos, opeType);
            });
        } catch (Exception e) {
            if (!status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
        }
        return true;
    }

    // 添加成功，即发布
    public boolean mergeDataItem(ConfOperatorType opeType, ConfPath confPath, String dataId, String content,
                                 String contentType, String desc, BasicBO basicBO) {
        // 检查是否存在;
        ConfPushDTO confPush = new ConfPushDTO();
        MbeanUtils.copyProperties(confPath, confPush);
        confPush.setDataId(dataId);
        confPush.setContent(content);
        confPush.setContentType(contentType);
        confPush.setDesc(desc);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        try {
            Ares2ConfDO confdo = confPushAdminService.mergeConfPush(confPush, basicBO);
            // 在notify的时候保证 commit
            boolean f = pushService.push(confdo, new ICallback<Object>() {
                /**
                 * 返回结果:
                 * 1. commit 失败! 异常, 发生在insert情况
                 * 2. FALSE , push的时候强制reload data 
                 * 3. TRUE , push的时候不需要 reload data 
                 */
                @Override
                public Object success(Object obj) {
                    // 提交有可能失败!
                    transactionManager.commit(status);
                    // 获取最新的数据库时间戳  
                    ConfPath clusterPath = new ConfPath();
                    MbeanUtils.copyProperties(confPath, clusterPath);
                    clusterPath.setDataId(dataId);
                    // 应该是 != , 有可能被 小于 或 大于的覆盖掉;
                    int u = ares2ConfManager.hasNotEqualRecord(clusterPath, confdo.getGmtModified());
                    if (u > 0) {
                        // 说明commit之后，数据库又被覆盖了;
                        return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                }

                @Override
                public void failed(Object obj, Exception e) {
                    transactionManager.rollback(status);
                }
            });
            if (!f) {
                throw new AresHandlerException("push 失败！");
            }
            async.async(() -> {
                saveChangeLog(confdo, opeType);
            });
        } catch (Exception e) {
            if (!status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
        }
        return true;
    }

    public void saveChangeLogBatch(List<Ares2ConfDO> confdos, ConfOperatorType type) {
        try {
            List<Ares2ConfHistoryDO> historys = new ArrayList<>();
            for (Ares2ConfDO e : confdos) {
                Ares2ConfHistoryDO chd = new Ares2ConfHistoryDO();
                chd.setConfHistoryUid(UUID.randomUUID().toString());
                chd.setOperationType(type.name());
                MbeanUtils.copyProperties(e, chd);
                historys.add(chd);
            }
            ares2ConfHistoryManager.insertBatch(historys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void saveChangeLog(Ares2ConfDO confdo, ConfOperatorType type) {
        try {
            Ares2ConfHistoryDO chd = new Ares2ConfHistoryDO();
            chd.setConfHistoryUid(UUID.randomUUID().toString());
            chd.setOperationType(type.name());
            MbeanUtils.copyProperties(confdo, chd);
            ares2ConfHistoryManager.insertSelective(chd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 更新成功，即发布
    public int updateDataItem(ConfPath confPath, String content, String contentType, String desc, BasicBO basicBO) {
        int contentSize = content.getBytes().length;
        String digest = Md5Crypt.md5Crypt(content.getBytes());

        Ares2ConfDO confdo = new Ares2ConfDO();
        MbeanUtils.copyProperties(confPath, confdo);
        confdo.setCreateUser(basicBO.getCreateUser());
        confdo.setModifyUser(basicBO.getModifyuser());
        confdo.setDigest(digest);
        confdo.setContent(content);
        confdo.setContentSize(contentSize);
        confdo.setContentType(contentType);
        confdo.setDesc(desc);
        confdo.setGmtModified(new Date());
        confdo.setModifyUserAccount(basicBO.getModifyUserAccount());
        confdo.setTraceId(UUID.randomUUID().toString());
        // 开启事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
        int u = 0;
        try {
            u = ares2ConfManager.updateContent(confdo);
            if (u > 0) {
                logger.info("update has record change! will push dataItem,dataId={}", confPath.getDataId());
                boolean f = pushService.push(confdo, new ICallback<Object>() {
                    @Override
                    public Object success(Object obj) {
                        transactionManager.commit(status);
                        ConfPath clusterPath = new ConfPath();
                        MbeanUtils.copyProperties(confPath, clusterPath);
                        int u = ares2ConfManager.hasNotEqualRecord(clusterPath, confdo.getGmtModified());
                        return u > 0 ? Boolean.FALSE : Boolean.TRUE;
                    }

                    @Override
                    public void failed(Object obj, Exception e) {
                        transactionManager.rollback(status);
                    }
                });
                if (!f) {
                    throw new AresHandlerException("push 失败！");
                }
            } else {
                logger.warn("update no record change! dataId={}", confPath.getDataId());
                transactionManager.commit(status);
            }
            saveChangeLog(confdo, ConfOperatorType.update);
        } catch (Exception e) {
            if (!status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
        }
        return u;
    }

    public List<Ares2ConfLogDO> confPushLog(long confId) {
        Ares2ConfDO cf = ares2ConfManager.selectByPrimaryKey(confId);
        if (cf == null) {
            return new ArrayList<>();
        }
        String traceId = cf.getTraceId();
        String appCode = cf.getAppCode();
        Ares2ConfLogQuery query = new Ares2ConfLogQuery();
        query.createCriteria().andAppCodeEqualTo(appCode).andTraceIdEqualTo(traceId);
        query.setOrderByClause("gmt_create desc");
        List<Ares2ConfLogDO> logList = ares2ConfLogManager.selectByQuery(query);

        return logList;
    }

}
