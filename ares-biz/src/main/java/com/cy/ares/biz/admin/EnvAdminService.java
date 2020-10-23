package com.cy.ares.biz.admin;

import java.util.List;

import com.cy.ares.common.error.AresHandlerException;
import com.cy.ares.common.error.AresHandlerTipException;
import com.cy.ares.dao.common.model.Ares2EnvDO;
import com.cy.ares.dao.common.query.Ares2EnvQuery;
import com.cy.ares.dao.core.manager.Ares2EnvManager;
import com.cy.ares.dao.core.manager.Ares2NamespaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvAdminService {

    @Autowired
    private Ares2NamespaceManager ares2NamespaceManager;

    @Autowired
    private Ares2EnvManager ares2EnvManager;

    public long insert(Ares2EnvDO env) {
        int u = ares2NamespaceManager.exist(env.getNamespaceCode());
        if( u <= 0 ){
            throw new AresHandlerException(String.format("ns=%s,不存在",env.getNamespaceCode()));
        }
        u = ares2EnvManager.exist(env.getNamespaceCode(),env.getEnvCode());
        if( u > 0 ){
          throw new AresHandlerTipException(String.format("ns=%s,env=%s已经存在",env.getNamespaceCode(),env.getEnvCode()));
        }
        return ares2EnvManager.insertSelective(env);
    }

    public List<Ares2EnvDO> listAll(String namespaceCode) {
        Ares2EnvQuery query = new Ares2EnvQuery();
        query.createCriteria().andNamespaceCodeEqualTo(namespaceCode);
        return ares2EnvManager.selectByQuery(query);
    }

    public int deleteEnv(String namespaceCode, String envCode) {
        // 相关表删除; @Note 逻辑 写在了sql里面
        return ares2EnvManager.deleteEnv(namespaceCode, envCode);
    }

}
