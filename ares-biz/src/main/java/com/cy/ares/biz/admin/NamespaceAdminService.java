package com.cy.ares.biz.admin;

import java.util.*;

import com.cy.ares.common.error.AresHandlerTipException;
import com.cy.ares.dao.common.model.Ares2NamespaceDO;
import com.cy.ares.dao.common.query.Ares2NamespaceQuery;
import com.cy.ares.dao.core.manager.Ares2NamespaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamespaceAdminService {

    @Autowired
    private Ares2NamespaceManager ares2NamespaceManager;
    
    public static final String FIX_NAMESPACE = "public";

    public long insert(Ares2NamespaceDO namespace) {
        int u = ares2NamespaceManager.exist(namespace.getNamespaceCode());
        if(u>0){
            throw new AresHandlerTipException(String.format("ns=%s已经存在！",namespace.getNamespaceCode()));
        }
        return ares2NamespaceManager.insertSelective(namespace);
    }

    public List<Ares2NamespaceDO> listAll() {
        Ares2NamespaceQuery query = new Ares2NamespaceQuery();
        return ares2NamespaceManager.selectByQuery(query);
    }

    public int deleteNamespace(String namespaceCode) {
        // 相关表删除; @Note 逻辑 写在了sql里面
        if(FIX_NAMESPACE.equals(namespaceCode)){
            return 0;
        }
        return ares2NamespaceManager.deleteNamespace(namespaceCode);
    }

}
