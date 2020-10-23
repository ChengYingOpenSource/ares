package com.cy.ares.api.admin;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.ConfGroupWebAdminService;
import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.common.error.AresHandlerTipException;
import com.cy.ares.dao.common.model.Ares2ConfGroupDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static com.cy.ares.biz.util.IdentityHelper.getCurrentUserAccount;
/**
 * @Description
 * @Author Mxq
 * @Date 2019/9/9 14:43
 */
@RestController
@RequestMapping("/${ares.ctx}/conf/group")
public class ConfGroupWebController extends DefaultController {

    @Autowired
    private ConfGroupWebAdminService confGroupWebAdminService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultMessage addConfGroup(Ares2ConfGroupDO confGroupDO) {
        if (StringUtils.isBlank(confGroupDO.getNamespaceCode()) || StringUtils.isBlank(confGroupDO.getAppCode())
            || StringUtils.isBlank(confGroupDO.getEnvCode())
            || StringUtils.isBlank(confGroupDO.getClusterCode()) || StringUtils.isBlank(confGroupDO.getGroup())) {
            throw new AresHandlerTipException("必填参数不能为空");
        }
        confGroupDO.setCreateUserAccount(getCurrentUserAccount());
        confGroupDO.setModifyUserAccount(getCurrentUserAccount());
        confGroupWebAdminService.add(confGroupDO);
        return ok("Group添加成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultMessage delete(Ares2ConfGroupDO confGroupDO) {
        if (StringUtils.isBlank(confGroupDO.getNamespaceCode()) || StringUtils.isBlank(confGroupDO.getAppCode())
            || StringUtils.isBlank(confGroupDO.getEnvCode())
            || StringUtils.isBlank(confGroupDO.getClusterCode()) || StringUtils.isBlank(confGroupDO.getGroup())) {
            throw new AresHandlerTipException("必填参数不能为空");
        }
        confGroupWebAdminService.delete(confGroupDO);
        return ok("");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultMessage list(Ares2ConfGroupDO confGroupDO) {
        if (StringUtils.isBlank(confGroupDO.getNamespaceCode()) || StringUtils.isBlank(confGroupDO.getAppCode())
            || StringUtils.isBlank(confGroupDO.getEnvCode()) || StringUtils.isBlank(confGroupDO.getClusterCode())) {
            throw new AresHandlerTipException("必填参数不能为空");
        }
        return ok(confGroupWebAdminService.list(confGroupDO));
    }

}
