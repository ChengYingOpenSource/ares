package com.cy.ares.dao.model.dto;
import lombok.Data;


@Data
public class ConfPath {
    
    private String namespaceCode;
    
    private String appCode;
    
    private String envCode;
    
    private String clusterCode;
    
    private String group;
    
    private String dataId;
    
}
