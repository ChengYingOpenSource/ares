package com.cy.ares.api.rpc;

import com.cy.ares.api.basic.DefaultController;
import com.cy.ares.api.rpc.dto.DataKVEntry;
import com.cy.ares.api.rpc.dto.MergeParamBatchDTO;
import com.cy.ares.api.rpc.dto.MergeParamDTO;
import com.cy.ares.biz.admin.ConfWebAdminService;
import com.cy.ares.biz.admin.DataItPrepareService;
import com.cy.ares.biz.admin.domain.AppEnvCst;
import com.cy.ares.biz.admin.domain.ConfOperatorType;
import com.cy.ares.biz.admin.domain.ConfPushDTO;
import com.cy.ares.biz.util.MbeanUtils;
import com.cy.ares.common.domain.BasicBO;
import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.dao.model.dto.ConfPath;
import com.cy.ares.spcp.cst.ConfigCst;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/${ares.ctx}/rpc/conf")
public class RpcConfController extends DefaultController implements RpcConfControllerApi{
    
    @Autowired
    private ConfWebAdminService confWebAdminService;

    @Autowired
    private DataItPrepareService dataItPrepareService;

    @Override
    @RequestMapping(value = "/dataItem/merge", method = RequestMethod.POST)
    public ResultMessage dataItemMerge(@RequestBody MergeParamDTO paramItem) {
        if(StringUtils.isAnyBlank(paramItem.getEnvCode(),paramItem.getAppCode(),paramItem.getDataId())){
            return fails("必要参数不能为空！");
        }
        ConfPath confPath = new ConfPath();
        String dataId = paramItem.getDataId();
        String content = paramItem.getContent();
        String contentType = paramItem.getContentType();
        String desc = paramItem.getDesc();
        BasicBO basicBO = new BasicBO();
        MbeanUtils.copyProperties(paramItem, basicBO);
        MbeanUtils.copyProperties(paramItem, confPath);

        String envTypeName = paramItem.getEnvTypeStr();
        if(AppEnvCst.DEV.equals(envTypeName)){
            paramItem.setEnvType(0);
        }else if(AppEnvCst.TEST.equals(envTypeName)){
            paramItem.setEnvType(1);
        }else if(AppEnvCst.PRE.equals(envTypeName)){
            paramItem.setEnvType(2);
        }else if(AppEnvCst.PROD.equals(envTypeName)){
            paramItem.setEnvType(3);
        }
        // check & create
        dataItPrepareService.prepare(confPath,paramItem.getEnvType(),paramItem.getAppType());

        return ok(confWebAdminService.mergeDataItem(ConfOperatorType.merge,confPath, dataId, content,contentType,desc, basicBO));
    }

    @Override
    @RequestMapping(value = "/dataItem/batch/merge", method = RequestMethod.POST)
    public ResultMessage dataItemBatchMerge(@RequestBody MergeParamBatchDTO batchParam) {

        List<DataKVEntry> dkv = batchParam.getDataIdEntrys();
        if(StringUtils.isAnyBlank(batchParam.getEnvCode(),batchParam.getAppCode())){
            return fails("必要参数不能为空！");
        }
        if(CollectionUtils.isEmpty(dkv)){
            return fails("配置DataKV参数不能为空！");
        }
        String envTypeName = batchParam.getEnvTypeStr();
        if(AppEnvCst.DEV.equals(envTypeName)){
            batchParam.setEnvType(0);
        }else if(AppEnvCst.TEST.equals(envTypeName)){
            batchParam.setEnvType(1);
        }else if(AppEnvCst.PRE.equals(envTypeName)){
            batchParam.setEnvType(2);
        }else if(AppEnvCst.PROD.equals(envTypeName)){
            batchParam.setEnvType(3);
        }
        List<ConfPushDTO> confDataIds = new ArrayList<>();
        for(DataKVEntry e:dkv){
            ConfPushDTO confDataId = new ConfPushDTO();
            MbeanUtils.copyProperties(batchParam, confDataId);
            if(StringUtils.isBlank(e.getDataId())){
                return fails("配置DataKV,Key不能为空！");
            }
            confDataId.setDataId(e.getDataId());
            confDataId.setContent(e.getContent());
            confDataIds.add(confDataId);
        }
        // check & create
        ConfPath confPath = new ConfPath();
        MbeanUtils.copyProperties(batchParam, confPath);
        dataItPrepareService.prepare(confPath,batchParam.getEnvType(),batchParam.getAppType());
        BasicBO basicBO = new BasicBO();
        MbeanUtils.copyProperties((BasicBO)batchParam, basicBO);

        return ok(confWebAdminService.mergeDataItemBatch(ConfOperatorType.merge,confDataIds,basicBO));
    }
    
}

