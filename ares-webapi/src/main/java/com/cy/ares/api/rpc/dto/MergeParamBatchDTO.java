package com.cy.ares.api.rpc.dto;

import com.cy.ares.common.domain.BasicBO;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/14 15:31
 */
@Data
public class MergeParamBatchDTO extends BasicBO {

    /**
     * 命名空间
     * 默认为public
     */
    private String namespaceCode;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 环境编码
     */
    private String envCode;

    /**
     * 集群code
     * 为空默认default
     */
    private String clusterCode;

    /**
     *  配置key的分组编码
     * 为空默认default
     */
    private String group;

    /**
     * 修改的配置List和内容
     */
    private List<DataKVEntry> dataIdEntrys;

    /**
     * text json xml html properties
     * 为空默认是 text
     */
    private String contentType;

    private String desc;

    /**
     * 0 dev 1 test 2 pre 3 prod
     */
    private int envType;

    /**
     * 枚举值：envTypeStr & envType 可2选一传值
     * dev test pre prod
     */
    private String envTypeStr;

    /**
     * backend fronted unknow
     * 为空则默认unknow
     */
    private String appType;

}