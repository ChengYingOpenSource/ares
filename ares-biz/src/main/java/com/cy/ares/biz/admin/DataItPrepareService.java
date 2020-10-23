package com.cy.ares.biz.admin;

import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.common.error.AresHandlerException;
import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.common.model.Ares2AppEnvDO;
import com.cy.ares.dao.common.model.Ares2ClusterDO;
import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.common.model.Ares2EnvDO;
import com.cy.ares.dao.core.manager.Ares2AppEnvManager;
import com.cy.ares.dao.core.manager.Ares2AppManager;
import com.cy.ares.dao.core.manager.Ares2ClusterManager;
import com.cy.ares.dao.core.manager.Ares2ConfGroupManager;
import com.cy.ares.dao.core.manager.Ares2EnvManager;
import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.dao.repo.ConfContextRepo;
import com.cy.ares.spcp.cst.ConfigCst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/9/3 17:39
 */
@Service
public class DataItPrepareService {

    @Autowired
    private ConfContextRepo confContextRepo;

    @Autowired
    private Ares2AppManager ares2AppManager;

    @Autowired
    private Ares2AppEnvManager ares2AppEnvManager;

    @Autowired
    private Ares2ClusterManager ares2ClusterManager;

    @Autowired
    private Ares2ConfGroupManager ares2ConfGroupManager;

    @Autowired
    private Ares2EnvManager ares2EnvManager;

    /**
     * 环境不存在，创建 ;
     * 应用不存在，创建 ;
     * 绑定关系不存在, 创建;
     *
     * @param confPath
     */
    @Transactional(rollbackFor=Exception.class)
    public void prepare(ConfPath confPath,int envType,String appType) {
        String envCode = confPath.getEnvCode();
        String appCode = confPath.getAppCode();
        String clusterCode = confPath.getClusterCode();

        String nc = confPath.getNamespaceCode();
        nc = StringUtils.isBlank(nc)? ConfigCst.NAMESPACE_DEF:nc;
        clusterCode = StringUtils.isBlank(clusterCode)?ConfigCst.CLUSTER_DEF:clusterCode;

        checkAndCreate(nc,appCode,envCode,clusterCode,envType,appType);
    }

    @Transactional(rollbackFor=Exception.class)
    public OptionBO checkAndCreate(String namespaceCode, String appCode, String envCode, String clusterCode,int envType,String appType){
        OptionBO ob = new OptionBO();
        int u = confContextRepo.nsExist(namespaceCode);
        if( u <= 0 ){
            throw new AresHandlerException(String.format("命名空间%s不存在!",namespaceCode));
        }
        u = confContextRepo.envExist(namespaceCode, envCode);
        if(u <= 0){
            Ares2EnvDO envDO = new Ares2EnvDO();
            envDO.setEnvCode(envCode);
            envDO.setType(envType);
            envDO.setNamespaceCode(namespaceCode);
            envDO.setEnvName("名称="+envCode);
            ares2EnvManager.insertSelective(envDO);
        }
        if(null == confContextRepo.appFindByCode(namespaceCode,appCode)){
            Ares2AppDO appDO = new Ares2AppDO();
            appDO.setNamespaceCode(namespaceCode);
            appDO.setAppCode(appCode);
            appDO.setAppName("名称="+appCode);
            appDO.setType(StringUtils.isBlank(appType)?"unknown":appType);
            ares2AppManager.insertSelective(appDO);
        }

        clusterCode = StringUtils.isBlank(clusterCode)?ConfigCst.CLUSTER_DEF:clusterCode;
        // 检测环境是否已经绑定过
        Ares2AppEnvDO appEnvDO = new Ares2AppEnvDO();
        appEnvDO.setNamespaceCode(namespaceCode);
        appEnvDO.setAppCode(appCode);
        appEnvDO.setEnvCode(envCode);
        u = confContextRepo.appEnvExist(namespaceCode, appCode, envCode);
        if(u <= 0){
            ares2AppEnvManager.insertSelective(appEnvDO);
        }

        Ares2ClusterDO cluster = new Ares2ClusterDO();
        MbeanUtils.copyProperties(appEnvDO, cluster);
        cluster.setClusterCode(clusterCode);
        u = ares2ClusterManager.exist(cluster);
        if(u <= 0){
            ares2ClusterManager.insertSelective(cluster);
        }

        Ares2ConfGroupDO group = new Ares2ConfGroupDO();
        MbeanUtils.copyProperties(cluster, group);
        group.setGroup(ConfigCst.GROUP_DEF);
        u = ares2ConfGroupManager.exist(group);
        if(u<=0){
            group.setGroupUid(UUID.randomUUID().toString());
            ares2ConfGroupManager.insertSelective(group);
        }
        return ob.setSuccess(true);
    }

}
