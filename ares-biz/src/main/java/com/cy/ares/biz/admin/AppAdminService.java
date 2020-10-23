package com.cy.ares.biz.admin;
import java.util.List;
import java.util.UUID;

import com.cy.ares.common.error.AresHandlerTipException;
import com.cy.ares.dao.common.model.Ares2AppDO;
import com.cy.ares.dao.common.model.Ares2AppEnvDO;
import com.cy.ares.dao.common.model.Ares2ClusterDO;
import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.common.query.Ares2AppQuery;
import com.cy.ares.dao.core.manager.Ares2AppEnvManager;
import com.cy.ares.dao.core.manager.Ares2AppManager;
import com.cy.ares.dao.core.manager.Ares2ClusterManager;
import com.cy.ares.dao.core.manager.Ares2ConfGroupManager;
import com.cy.ares.dao.core.manager.Ares2EnvManager;
import com.cy.ares.dao.core.manager.Ares2NamespaceManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.spcp.cst.ConfigCst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import static com.cy.ares.biz.util.IdentityHelper.*;

@Service
public class AppAdminService {
    
    @Autowired
    private Ares2AppEnvManager ares2AppEnvManager;

    @Autowired
    private Ares2NamespaceManager ares2NamespaceManager;

    @Autowired
    private Ares2AppManager ares2AppManager;

    @Autowired
    private Ares2EnvManager ares2EnvManager;

    @Autowired
    private Ares2ClusterManager ares2ClusterManager;

    @Autowired
    private Ares2ConfGroupManager ares2ConfGroupManager;
    
    @Transactional(rollbackFor=Exception.class)
    public boolean insert(Ares2AppDO app) {
        Ares2AppQuery query = new Ares2AppQuery();
        Ares2AppQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(app.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(app.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(app.getAppCode())) {
            criteria.andAppCodeEqualTo(app.getAppCode());
        }
        Ares2AppDO ares2AppDO = ares2AppManager.selectOneByQuery(query);
        if (ares2AppDO != null) {
            throw new AresHandlerTipException("app已经存在");
        }
        ares2AppManager.insertSelective(app);
        return true;
    }

    public PageInfo<Ares2AppDO> listPage(String namespaceCode, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Ares2AppQuery query = new Ares2AppQuery();
        Ares2AppQuery.Criteria criteria = query.createCriteria();
        criteria.andNamespaceCodeEqualTo(namespaceCode);
        query.setPageNo(pageNum);
        query.setPageSize(pageSize);
        List<Ares2AppDO> result = ares2AppManager.selectByQueryWithPage(query).getResult();
        PageInfo<Ares2AppDO> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }
    
    @Transactional
    public boolean deleteApp(String namespaceCode,String appCode){
        // 相关表删除; @Note 逻辑 写在了sql里面
        Ares2AppDO ares2AppDO = new Ares2AppDO();
        ares2AppDO.setNamespaceCode(namespaceCode);
        ares2AppDO.setAppCode(appCode);
        ares2AppManager.deleteApp(ares2AppDO);
        return true;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public OptionBO bindEnv(String namespaceCode,String appCode,String envCode,String clusterCode){
        OptionBO ob = new OptionBO();
        int u = ares2NamespaceManager.exist(namespaceCode);
        if( u <= 0 ){
            return ob.setSuccess(false).setMsg("命名code不存在!");
        }
        u = ares2EnvManager.exist(namespaceCode, envCode);
        if(u <= 0){
            return ob.setSuccess(false).setMsg("环境code不存在!");
        }
        Ares2AppQuery query = new Ares2AppQuery();
        Ares2AppQuery.Criteria criteria = query.createCriteria();
        criteria.andNamespaceCodeEqualTo(namespaceCode);
        criteria.andAppCodeEqualTo(appCode);
        if (null == ares2AppManager.selectOneByQuery(query)) {
            return ob.setSuccess(false).setMsg("应用code不存在!");
        }
        
        clusterCode = StringUtils.isBlank(clusterCode)?ConfigCst.CLUSTER_DEF:clusterCode;
        // 检测环境是否已经绑定过
        Ares2AppEnvDO appEnvDO = new Ares2AppEnvDO();
        appEnvDO.setNamespaceCode(namespaceCode);
        appEnvDO.setAppCode(appCode);
        appEnvDO.setEnvCode(envCode);
        u = ares2AppEnvManager.exist(namespaceCode, appCode, envCode);
        if(u <= 0){
            appEnvDO.setCreateUserAccount(getCurrentUserAccount());
            appEnvDO.setModifyUserAccount(getCurrentUserAccount());
            ares2AppEnvManager.insertSelective(appEnvDO);
        }

        Ares2ClusterDO cluster = new Ares2ClusterDO();
        MbeanUtils.copyProperties(appEnvDO, cluster);
        cluster.setClusterCode(clusterCode);
        u = ares2ClusterManager.exist(cluster);
        if(u <= 0){
            cluster.setCreateUserAccount(getCurrentUserAccount());
            cluster.setModifyUserAccount(getCurrentUserAccount());
            ares2ClusterManager.insertSelective(cluster);
        }

        Ares2ConfGroupDO group = new Ares2ConfGroupDO();
        MbeanUtils.copyProperties(cluster, group);
        group.setGroup(ConfigCst.GROUP_DEF);
        u = ares2ConfGroupManager.exist(group);
        if(u<=0){
            group.setGroupUid(UUID.randomUUID().toString());
            group.setCreateUserAccount(getCurrentUserAccount());
            group.setModifyUserAccount(getCurrentUserAccount());
            ares2ConfGroupManager.insertSelective(group);
        }
        return ob.setSuccess(true); 
    }
    
}
