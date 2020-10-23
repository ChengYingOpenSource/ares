package com.cy.ares.biz.admin;

import com.cy.ares.common.error.AresHandlerTipException;
import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import com.cy.ares.dao.common.query.Ares2ConfGroupQuery;
import com.cy.ares.dao.core.manager.Ares2ConfGroupManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/9/9 14:45
 */
@Service
public class ConfGroupWebAdminService {

    @Autowired
    private Ares2ConfGroupManager ares2ConfGroupManager;

    public void add(Ares2ConfGroupDO cg) {

        int u = ares2ConfGroupManager.exist(cg);
        if(u > 0 ){
            throw  new AresHandlerTipException("group已经存在！");
        }
        cg.setGroupUid(UUID.randomUUID().toString());
        ares2ConfGroupManager.insertSelective(cg);
    }

    public void delete(Ares2ConfGroupDO cg) {
        ares2ConfGroupManager.deleteConfGroup(cg);
    }

    public List<Ares2ConfGroupDO> list(Ares2ConfGroupDO cg){
        Ares2ConfGroupQuery query = new Ares2ConfGroupQuery();
        Ares2ConfGroupQuery.Criteria criteria = query.createCriteria();
        if (StringUtils.isNotBlank(cg.getNamespaceCode())) {
            criteria.andNamespaceCodeEqualTo(cg.getNamespaceCode());
        }
        if (StringUtils.isNotBlank(cg.getAppCode())) {
            criteria.andAppCodeEqualTo(cg.getAppCode());
        }
        if (StringUtils.isNotBlank(cg.getEnvCode())) {
            criteria.andEnvCodeEqualTo(cg.getEnvCode());
        }
        if (StringUtils.isNotBlank(cg.getGroup())) {
            criteria.andGroupEqualTo(cg.getGroup());
        }
        return ares2ConfGroupManager.selectByQuery(query);
    }


}
