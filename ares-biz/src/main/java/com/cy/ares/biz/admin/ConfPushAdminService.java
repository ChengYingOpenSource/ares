package com.cy.ares.biz.admin;

import com.cy.ares.biz.admin.domain.ConfPushDTO;
import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.common.domain.BasicBO;
import com.cy.ares.common.error.AresHandlerException;
import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.core.manager.Ares2ConfManager;
import com.cy.ares.spcp.cst.ConfigCst;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ConfPushAdminService {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfPushAdminService.class);
    
    @Autowired
    private Ares2ConfManager ares2ConfManager;

    /**
     * 默认批量是在同一个app ns env下面的
     * @param confDataIdList
     * @param basicBO
     * @return
     */
    public List<Ares2ConfDO> mergeConfPushBatch(List<ConfPushDTO> confDataIdList, BasicBO basicBO) {
        List<Ares2ConfDO> listConf = new ArrayList<>();
        // 多线程判断是save 还是 update ;
        // 然后再主线程中进行 db 操作 ; TODO Note:多线程无法操作同一个事务, 需要 自己管理事务和session
        // 多线程操作同一个事务的前提是：每个线程操作的数据不进行互相依赖，各自独立 (本质和线程安全问题是一样的)
        for (ConfPushDTO dto : confDataIdList) {
            Ares2ConfDO cd = mergeConfPushReplace(dto, basicBO);
            listConf.add(cd);
        }
        try {
            ares2ConfManager.replaceConf(listConf);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new AresHandlerException("Conf save batch error{}"+e.getMessage());
        }
        return listConf;
    }

    public Ares2ConfDO mergeConfPushReplace(ConfPushDTO confPush, BasicBO basicBO) {
        String content = confPush.getContent();
        int contentSize = content.getBytes().length;
        String digest = Md5Crypt.md5Crypt(content.getBytes());
        Ares2ConfDO confdo = new Ares2ConfDO();
        MbeanUtils.copyProperties(confPush, confdo);
        confdo.setCreateUser(basicBO.getCreateUser());
        confdo.setModifyUser(basicBO.getModifyuser());
        confdo.setDigest(digest);
        confdo.setContentSize(contentSize);
        String contentType = confdo.getContentType();
        confdo.setContentType(StringUtils.isBlank(contentType)?"text":contentType);

        String clusterCode = confPush.getClusterCode();
        String group = confPush.getGroup();
        String nc = confPush.getNamespaceCode();
        nc = StringUtils.isBlank(nc)?ConfigCst.NAMESPACE_DEF:nc;
        group = StringUtils.isBlank(group)?ConfigCst.GROUP_DEF:group;
        clusterCode = StringUtils.isBlank(clusterCode)?ConfigCst.CLUSTER_DEF:clusterCode;
        confdo.setGmtCreate(new Date());
        confdo.setGmtModified(new Date());
        confdo.setClusterCode(clusterCode);
        confdo.setGroup(group);
        confdo.setNamespaceCode(nc);
        String traceId = UUID.randomUUID().toString();
        confdo.setTraceId(traceId);
        confdo.setCreateUserAccount(basicBO.getCreateUserAccount());
        confdo.setModifyUserAccount(basicBO.getModifyUserAccount());
        return confdo;
    }

    public Ares2ConfDO mergeConfPush(ConfPushDTO confPush, BasicBO basicBO) {
        List<Ares2ConfDO> listConf = new ArrayList<>();
        Ares2ConfDO cd = mergeConfPushReplace(confPush, basicBO);
        try {
            listConf.add(cd);
            ares2ConfManager.replaceConf(listConf);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new AresHandlerException("Conf save error{}"+e.getMessage());
        }
        return cd;
    }

}
