package com.cy.ares.biz.admin.domain;
import lombok.Data;

@Data
public class ConfPushDTO {
    
    private String namespaceCode;
    
    private String appCode;

    private String envCode;

    private String clusterCode;

    private String group;
    
    private String dataId;

    private String content;
    
    private String contentType;
    
    private int encrypt;
    
    private String desc;
    
}
