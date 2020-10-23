package com.cy.ares.api.rpc.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/14 15:37
 */
@Data
public class DataKVEntry {

    /**
     * 配置的key
     */
    private String dataId;

    /**
     * key的内容
     */
    private String content;
}
