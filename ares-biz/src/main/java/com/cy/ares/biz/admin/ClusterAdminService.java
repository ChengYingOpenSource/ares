package com.cy.ares.biz.admin;

import com.cy.ares.biz.admin.domain.ClusterCopyDTO;
import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.common.utils.ListUtils;
import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.common.query.Ares2ConfQuery;
import com.cy.ares.dao.core.manager.Ares2ConfManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import static com.cy.ares.biz.util.IdentityHelper.*;
/**
 * @Description
 * @Author Mxq
 * @Date 2019/10/2 12:58
 */
@Service
public class ClusterAdminService {

    private static final Logger logger = LoggerFactory.getLogger(ConfWebAdminService.class);

    @Autowired
    private Ares2ConfManager ares2ConfManager;

    /**
     * 复制功能
     * @param clusterCopy
     * @return
     */
    public OptionBO clusterCopy(ClusterCopyDTO clusterCopy) {
        Ares2ConfQuery query = new Ares2ConfQuery();
        Ares2ConfQuery.Criteria criteria = query.createCriteria();
        criteria.andNamespaceCodeEqualTo(clusterCopy.getNamespaceCode())
            .andEnvCodeEqualTo(clusterCopy.getFromEnvCode()).andClusterCodeEqualTo(clusterCopy.getFromClusterCode())
            .andAppCodeEqualTo(clusterCopy.getAppCode()).andGroupEqualTo(clusterCopy.getFromGroup());
        List<Ares2ConfDO> confFromList = ares2ConfManager.selectByQuery(query);
        if(CollectionUtils.isEmpty(confFromList)){
            return OptionBO.oks("新增配置 num=0,更新配置 num=0");
        }
        Ares2ConfQuery query2 = new Ares2ConfQuery();
        Ares2ConfQuery.Criteria criteria2 = query2.createCriteria();
        criteria2.andNamespaceCodeEqualTo(clusterCopy.getNamespaceCode())
            .andEnvCodeEqualTo(clusterCopy.getEnvCode()).andClusterCodeEqualTo(clusterCopy.getClusterCode())
            .andAppCodeEqualTo(clusterCopy.getAppCode()).andGroupEqualTo(clusterCopy.getGroup());
        List<Ares2ConfDO> confToList =  ares2ConfManager.selectByQuery(query2);
        Map<String,Ares2ConfDO> dataIdMap = ListUtils.listToMap(confToList,(e)->{return e.getDataId();});

        List<Ares2ConfDO> confToAdd = Lists.newArrayList();
        List<Ares2ConfDO> confToUpdate = Lists.newArrayList();
        for (Ares2ConfDO confDO : confFromList) {
            confDO.setGroup(clusterCopy.getGroup());
            confDO.setEnvCode(clusterCopy.getEnvCode());
            confDO.setClusterCode(clusterCopy.getClusterCode());
            confDO.setGmtModified(new Date());
            Ares2ConfDO confToDO = dataIdMap.get(confDO.getDataId());
            if(confToDO != null){
                // update
                if(clusterCopy.isCoverage()){
                    confToDO.setContent(confDO.getContent());
                    confToDO.setContentSize(confDO.getContentSize());
                    confToDO.setModifyUserAccount(getCurrentUserAccount());
                    confToDO.setGmtModified(new Date());
                    confToUpdate.add(confToDO);
                }
            }else{
                confDO.setCreateUserAccount(getCurrentUserAccount());
                confDO.setModifyUserAccount(getCurrentUserAccount());
                confToAdd.add(confDO);
            }
        }
        if(!CollectionUtils.isEmpty(confToAdd)) {
            ares2ConfManager.insertBatch(confToAdd);
        }
        if(!CollectionUtils.isEmpty(confToUpdate)) {
            for (Ares2ConfDO ele : confToUpdate) {
                ares2ConfManager.updateByPrimaryKeySelective(ele);
            }
        }
        String msg = String.format("新增配置 num=%d,更新配置 num=%d",confToAdd.size(),confToUpdate.size());
        return OptionBO.oks(msg);
    }



}
