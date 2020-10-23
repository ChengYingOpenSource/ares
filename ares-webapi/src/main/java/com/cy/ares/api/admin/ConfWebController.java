package com.cy.ares.api.admin;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.biz.admin.ConfWebAdminService;
import com.cy.ares.biz.admin.domain.AppRunEnvInfoDTO;
import com.cy.ares.biz.admin.domain.ConfOperatorType;
import com.cy.ares.common.domain.BasicBO;
import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.common.utils.ListUtils;
import com.cy.ares.dao.common.model.Ares2ConfDO;
import com.cy.ares.dao.model.dto.ConfPath;
import com.google.common.collect.ListMultimap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cy.ares.biz.util.IdentityHelper.getCurrentUserAccount;

@RestController
@RequestMapping("/${ares.ctx}/conf")
public class ConfWebController extends DefaultController {

    @Autowired
    private ConfWebAdminService confWebAdminService;

    @RequestMapping(value = "/runEnv/info", method = RequestMethod.POST)
    public ResultMessage appRunEnvInfo(String namespaceCode, String appCode) {
        AppRunEnvInfoDTO arid = confWebAdminService.appRunEnvInfo(namespaceCode, appCode);
        return ok(arid);
    }

    @RequestMapping(value = "/list/info", method = RequestMethod.POST)
    public ResultMessage listInfo(ConfPath clusterPath) {
        List<Ares2ConfDO> list = confWebAdminService.appConfInfo(clusterPath);
        ListMultimap<String, Ares2ConfDO> map = ListUtils.toMultimap(list, e -> {
            return e.getGroup();
        });
        return ok(map.asMap());
    }

    @RequestMapping(value = "/dataItem/info", method = RequestMethod.POST)
    public ResultMessage confDetailInfo(long id) {
        return ok(confWebAdminService.confDetailInfo(id));
    }

    @RequestMapping(value = "/dataItem/del", method = RequestMethod.POST)
    public ResultMessage confDel(long id, BasicBO basicBO) {
        return ok(confWebAdminService.deleteConfWithDirect(id));
    }

    @RequestMapping(value = "/dataItem/add", method = RequestMethod.POST)
    public ResultMessage confAdd(ConfPath confPath, String dataId, String content, String contentType, String desc,
                                 BasicBO basicBO) {
        basicBO.setUserAccount(getCurrentUserAccount());
        if (StringUtils.isBlank(dataId) || dataId.contains(";")) {
            return fails("key不能为空&不能存在;字符");
        }
        return ok(confWebAdminService
            .mergeDataItem(ConfOperatorType.add, confPath, dataId, content, contentType, desc, basicBO));
    }

    @RequestMapping(value = "/dataItem/merge", method = RequestMethod.POST)
    public ResultMessage confMerge(ConfPath confPath, String dataId, String content, String contentType, String desc,
                                   BasicBO basicBO) {
        basicBO.setUserAccount(getCurrentUserAccount());
        if (StringUtils.isBlank(dataId) || dataId.contains(";")) {
            return fails("key不能为空&不能存在;字符");
        }
        return ok(confWebAdminService
            .mergeDataItem(ConfOperatorType.merge, confPath, dataId, content, contentType, desc, basicBO));
    }

    @RequestMapping(value = "/dataItem/update", method = RequestMethod.POST)
    public ResultMessage confUpdate(ConfPath confPath, String dataId, String content, String contentType, String desc,
                                    BasicBO basicBO) {
        basicBO.setUserAccount(getCurrentUserAccount());
        if (StringUtils.isBlank(dataId) || dataId.contains(";")) {
            return fails("key不能为空&不能存在;字符");
        }
        return ok(confWebAdminService.updateDataItem(confPath, content, contentType, desc, basicBO));
    }

    @RequestMapping(value = "/dataItem/trace/info", method = RequestMethod.POST)
    public ResultMessage confPushLog(long id) {
        return ok(confWebAdminService.confPushLog(id));
    }

}
