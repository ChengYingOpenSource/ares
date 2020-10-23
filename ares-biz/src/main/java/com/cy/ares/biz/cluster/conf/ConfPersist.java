package com.cy.ares.biz.cluster.conf;

import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.cluster.conf.ConfPersistence;
import com.cy.ares.common.utils.ListUtils;
import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.common.model.Ares2ConfLogDO;
import com.cy.ares.dao.common.query.Ares2ConfQuery;
import com.cy.ares.dao.core.manager.Ares2ConfLogManager;
import com.cy.ares.dao.core.manager.Ares2ConfManager;
import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.spcp.cst.ConfigCst;
import com.cy.ares.spcp.protocol.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dbConf")
public class ConfPersist implements ConfPersistence {

    private static final Logger logger = LoggerFactory.getLogger(ConfPersist.class);

    @Autowired
    private Ares2ConfLogManager ares2ConfLogManager;

    @Autowired
    private Ares2ConfManager ares2ConfManager;

    @Override
    public FetchResp fetch(FetchReq fr) {
        DataGroupKey groupKey = fr.getGroupKey();
        // group;dataId
        Set<String> keys = fr.getKeys();
        if ((keys == null || keys.isEmpty()) && groupKey == null) {
            // 按照 groupKey取
            logger.warn("fetchReq args is illegal, all is null !");
            FetchResp fetchResp = new FetchResp();
            return fetchResp;
        }
        String namespaceCode = groupKey.getNamespaceCode();
        namespaceCode = StringUtils.isBlank(namespaceCode) ? ConfigCst.NAMESPACE_DEF : namespaceCode;
        String appCode = groupKey.getAppCode();
        String envCode = groupKey.getEnvCode();
        String clusterCode = groupKey.getClusterCode();
        clusterCode = StringUtils.isBlank(clusterCode) ? ConfigCst.CLUSTER_DEF : clusterCode;
        String group = groupKey.getGroup();
        // 优先通过 keys;
        List<Ares2ConfDO> confResult = null;

        ConfPath path = new ConfPath();
        path.setNamespaceCode(namespaceCode);
        path.setAppCode(appCode);
        path.setEnvCode(envCode);
        path.setClusterCode(clusterCode);

        if (keys != null && !keys.isEmpty()) {
            // Note 不同group下的dataId可以重复
            Set<String> dataIdSet = new HashSet<>();
            keys.forEach(e -> {
                dataIdSet.add(e.split(ConfigCst.semicolon)[1]);
            });
            path.setGroup(group);
            confResult = ares2ConfManager.findByKeyDataIds(path, ListUtils.toList(dataIdSet));
        } else {
            if (DataGroupKey.ALL_GROUP.equals(group) || DataGroupKey.MULTI_GROUP.equals(group)) {
                group = null;
            }
            path.setGroup(group);
            confResult = ares2ConfManager.findByGroupKey(path);
        }

        FetchResp fresp = new FetchResp();
        if (confResult != null) {
            List<DataItem> diList = new ArrayList<>();
            confResult.forEach(e -> {
                DataItem di = new DataItem();
                MbeanUtils.copyProperties(e, di);
                di.setGmtModified(e.getGmtModified());
                diList.add(di);
            });
            fresp.setItemList(diList);
        }
        return fresp;
    }

    @Override
    public DataItem findDataItem(DataGroupKey group, String dataId) {

        String namespaceCode = group.getNamespaceCode();
        namespaceCode = StringUtils.isBlank(namespaceCode) ? ConfigCst.NAMESPACE_DEF : namespaceCode;
        group.setNamespaceCode(namespaceCode);

        List<String> dlist = new ArrayList<>();
        dlist.add(dataId);

        ConfPath clusterPath = new ConfPath();
        MbeanUtils.copyProperties(group, clusterPath);
        List<Ares2ConfDO> confDOList = ares2ConfManager.findByKeyDataIds(clusterPath, dlist);

        Ares2ConfDO cd = CollectionUtils.isEmpty(confDOList) ? null : confDOList.get(0);
        if (cd == null) {
            return null;
        }
        DataItem target = new DataItem();
        MbeanUtils.copyProperties(cd, target);
        return target;
    }

    @Override
    public List<DataItem> findDataItems(DataGroupKey groupKey) {
        String namespaceCode = groupKey.getNamespaceCode();
        namespaceCode = StringUtils.isBlank(namespaceCode) ? ConfigCst.NAMESPACE_DEF : namespaceCode;
        groupKey.setNamespaceCode(namespaceCode);

        String group = groupKey.getGroup();
        if (DataGroupKey.ALL_GROUP.equals(group) || DataGroupKey.MULTI_GROUP.equals(group)) {
            group = null;
        }
        ConfPath groupPath = new ConfPath();
        MbeanUtils.copyProperties(group, groupPath);
        List<Ares2ConfDO> confDOList = ares2ConfManager.findByGroupKey(groupPath);
        List<DataItem> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(confDOList)) {
            return result;
        }
        MbeanUtils.copyPropertiesList(confDOList, result, DataItem.class);
        return result;
    }

    /**
     * 是否存在比这条 modifiedTime 还大的item;
     */
    @Override
    public boolean isLastItem(DataItem item) {
        if (item == null) {
            return false;
        }
        String appCode = item.getAppCode();
        String envCode = item.getEnvCode();
        String clusterCode = item.getClusterCode();
        String dataId = item.getDataId();
        String group = item.getGroup();
        Date gmtModified = item.getGmtModified();
        Ares2ConfQuery query = new Ares2ConfQuery();
        Ares2ConfQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(item.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(item.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(appCode)) {
            criteria.andAppCodeEqualTo(appCode);
        }
        if (StringUtils.isNotBlank(envCode)) {
            criteria.andEnvCodeEqualTo(envCode);
        }
        if (StringUtils.isNotBlank(clusterCode)) {
            criteria.andClusterCodeEqualTo(clusterCode);
        }
        if (StringUtils.isNotBlank(group)) {
            criteria.andGroupEqualTo(group);
        }
        if (StringUtils.isNotBlank(dataId)) {
            criteria.andDataIdEqualTo(dataId);
        }
        criteria.andGmtModifiedGreaterThan(gmtModified);
        return ares2ConfManager.selectOneByQuery(query) != null;
    }

    @Override
    public int saveConfPushLog(boolean isSuccess, List<DataItem> items, ClientInstanceInfo instanceInfo,
                               String pushMsg) {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        List<Ares2ConfLogDO> confLogList = new ArrayList<>();
        items.forEach(e -> {
            Ares2ConfLogDO confLog = new Ares2ConfLogDO();
            confLog.setTraceId(e.getTraceId());
            confLog.setAppCode(e.getAppCode());
            confLog.setDataId(e.getDataId());
            confLog.setPushInfo(pushMsg);
            confLog.setPushStatus(String.valueOf(isSuccess));
            if (instanceInfo != null) {
                confLog.setHostname(instanceInfo.getHostName());
                confLog.setIpAddr(instanceInfo.getIpAddr());
                confLog.setInstanceId(instanceInfo.getInstanceId());
            }
            confLogList.add(confLog);
        });

        int s = ares2ConfLogManager.insertBatch(confLogList);
        return s;
    }

}
