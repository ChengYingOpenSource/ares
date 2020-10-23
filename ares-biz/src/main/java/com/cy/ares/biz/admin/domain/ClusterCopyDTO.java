package com.cy.ares.biz.admin.domain;

import lombok.Data;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/10/2 9:10
 */
@Data
public class ClusterCopyDTO {

    private String namespaceCode;

    private String appCode;

    private String envCode;

    private String clusterCode;

    private String group;

    private String fromClusterCode;

    private String fromEnvCode;

    private String fromGroup;

    /**
     * 是否覆盖
     */
    private boolean coverage;

}
