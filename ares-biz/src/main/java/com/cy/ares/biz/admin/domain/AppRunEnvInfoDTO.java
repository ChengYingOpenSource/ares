package com.cy.ares.biz.admin.domain;
import java.util.*;

import com.cy.ares.dao.common.model.Ares2EnvDO;

import lombok.Data;


@Data
public class AppRunEnvInfoDTO {
    
    private String namespaceCode;
    
    private String appCode;
    
    private List<Ares2EnvDO> envList;
    
    private Map<String, List<String>> envCluster = new TreeMap<>();
    
}
