package com.cy.ares.api.rpc;

import com.cy.ares.api.rpc.dto.MergeParamBatchDTO;
import com.cy.ares.api.rpc.dto.MergeParamDTO;
import com.cy.ares.common.domain.ResultMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/14 15:06
 */
public interface RpcConfControllerApi {

    /**
     * @api {POST} /ares/rpc/conf/dataItem/merge 单条推送
     * @apiVersion 1.0.0
     * @apiGroup RpcConfController
     * @apiName dataItemMerge
     * @apiParam (请求体) {String} namespaceCode 命名空间
     * - 为空默认为public
     * @apiParam (请求体) {String} appCode 应用编码
     * @apiParam (请求体) {String} envCode 环境编码
     * @apiParam (请求体) {String} clusterCode 集群code
     * - 为空默认default
     * @apiParam (请求体) {String} group 配置key的分组编码
     * - 为空默认default
     * @apiParam (请求体) {String} dataId 配置的key
     * @apiParam (请求体) {String} content 配置key的值
     * @apiParam (请求体) {String} contentType 内容类型
     * - text json xml html properties
     * - 为空默认是 text
     * @apiParam (请求体) {String} desc
     * @apiParam (请求体) {Number} envType 环境类型数字类型
     * - 0 dev 1 test 2 pre 3 prod
     * @apiParam (请求体) {String} envTypeStr 环境类型枚举字符
     * - 枚举值：(envTypeStr &envType 可2选一传值)
     * - dev test pre prod
     * @apiParam (请求体) {String} appType
     * - 枚举值: backend fronted unknow
     * - 为空则默认unknow
     * @apiParamExample 请求体示例
     * {"clusterCode":"default",
     *  "appCode":"SndnD",
     *  "content":"v",
     *  "envTypeStr":"pre",
     *  "envCode":"HM4K86f6","dataId":"Ixx","envType":1,
     *  "appType":"fronted",
     *  "namespaceCode":"public",
     *  "contentType":"text",
     *  "group":"default"}
     * @apiSuccess (响应结果) {Number} total
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} data
     * @apiSuccess (响应结果) {Object} params
     * @apiSuccess (响应结果) {Boolean} success
     * @apiSuccess (响应结果) {Number} time
     * @apiSuccessExample 响应结果示例
     * {"msg":"xxxx","total":0,"code":9881,"data":{},"success":false,"time":3832}
     */
    ResultMessage dataItemMerge(@RequestBody MergeParamDTO paramItem);


    /**
     * @api {POST} /ares/rpc/conf/dataItem/batch/merge 批量推送
     * @apiVersion 1.0.0
     * @apiGroup RpcConfController
     * @apiName dataItemBatchMerge
     * @apiParam (请求体) {String} namespaceCode 命名空间
     * - 默认为public
     * @apiParam (请求体) {String} appCode 应用编码
     * @apiParam (请求体) {String} envCode 环境编码
     * @apiParam (请求体) {String} clusterCode 集群code
     * - 为空默认default
     * @apiParam (请求体) {String} group 配置key的分组编码
     * - 为空默认default
     * @apiParam (请求体) {Array} dataIdEntrys 配置List
     * @apiParam (请求体) {String} dataIdEntrys.dataId 配置的key
     * @apiParam (请求体) {String} dataIdEntrys.content key的内容
     * @apiParam (请求体) {String} contentType
     * - text json xml html properties
     * - 为空默认是 text
     * @apiParam (请求体) {String} desc
     * @apiParam (请求体) {Number} envType 环境类型数字类型
     * - 0 dev 1 test 2 pre 3 prod
     * @apiParam (请求体) {String} envTypeName 环境类型枚举字符值
     * - 枚举值：envTypeName &envType 可2选一传值
     * - dev test pre prod
     * @apiParam (请求体) {String} appType
     * - backend fronted unknow
     * - 为空则默认unknow
     * @apiParamExample 请求体示例
     * { "appCode":"xxx","envTypeStr":"dev",
     *   "dataIdEntrys":[{"dataId":"p","content":"SRr59q"}],
     *  "envCode":"xxx","envType":1,"appType":"backend",
     *  "namespaceCode":"public","contentType":"text","group":"default"}
     * @apiSuccess (响应结果) {Number} total
     * @apiSuccess (响应结果) {Number} code
     * @apiSuccess (响应结果) {String} msg
     * @apiSuccess (响应结果) {Object} data
     * @apiSuccess (响应结果) {Object} params
     * @apiSuccess (响应结果) {Boolean} success
     * @apiSuccess (响应结果) {Number} time
     * @apiSuccessExample 响应结果示例
     * {"msg":"nwre","total":8391,"code":3547,"data":{},"success":false,"time":268}
     */
    ResultMessage dataItemBatchMerge(@RequestBody MergeParamBatchDTO batchParam);



}


