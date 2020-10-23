package com.cy.ares.client.spring;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cy.ares.spcp.actor.fetcher.Listener;
import com.cy.ares.spcp.cst.ContentTypeEnum;
import com.cy.ares.spcp.protocol.DataItem;

import ch.qos.logback.classic.Level;


/**
 * 自动扫描 Listener 注解 ?
 * key: spring.logger.config
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年7月8日 上午8:54:35
 * @version V1.0
 */
@Component
@Deprecated
public class LoggerLevelSwitchListener implements Listener {
    
    protected static final Logger logger = LoggerFactory.getLogger(LoggerLevelSwitchListener.class);
    
    @Autowired(required=false)
    private LogbackLoggingSystem logbackLoggingSystem;
    
    @Autowired(required=false)
    private Log4J2LoggingSystem log4J2LoggingSystem;
    
    /**
     * value支持 json,properties风格 
     * 
     * logger.config 
     * [{"loggerName":"","level":""}]
     * cy.name = DEBUG
     */
    @Override
    public void receive(DataItem item) {
        
        String key = item.getDataId();
        if(logbackLoggingSystem == null && log4J2LoggingSystem == null){
            logger.warn("Can't find logging system!,receive key={}",key);
            return ;
        }
        String value = item.getContent();
        if(StringUtils.isBlank(value)){
            return;
        }
        Slf4JLoggingSystem loggingSystem = logbackLoggingSystem ==null?log4J2LoggingSystem:logbackLoggingSystem;
        
        String cntType = item.getContentType();
        cntType = StringUtils.isBlank(cntType)?ContentTypeEnum.json.name():cntType;
        ContentTypeEnum ct = ContentTypeEnum.valueOf(cntType);
        
        List<KVEntry> vList = new ArrayList<>();
        if(ct == ContentTypeEnum.json){
            vList = JSON.parseArray(value,KVEntry.class);
        }else{
            // 不是json,就是 properties 默认按照 解析，解析失败，打印日志;
            Properties p = new Properties();
            StringReader sbi = new StringReader(value);
            try {
                p.load(sbi);
            } catch (IOException e1) {
                logger.error(e1.getMessage(),e1);
                logger.error("receive key={},value={} parser properties happen error!",key,value);
                return;
            } finally {
                sbi.close();
            }
            for(Object loggerName:p.keySet()){
                String logger = loggerName.toString();
                vList.add(new KVEntry(logger,p.getProperty(logger)));
            }
        }
        vList.forEach(e->{
            try {
                if(StringUtils.isNotBlank(e.level)){
                    String upLevel = e.level.trim().toUpperCase();
                    loggingSystem.setLogLevel(e.loggerName,LogLevel.valueOf(upLevel));
                }else{
                    logger.warn("loggerName={}, level config is blank!",e.loggerName);
                }
            } catch (Exception e2) {
                logger.error(e2.getMessage(),e2);
                logger.error("loggerName={}, level={},happen error!",e.loggerName,e.level);
            }
        });
    }
    
    public static class KVEntry{
        public String loggerName;
        public String level;
        public KVEntry(){}
        public KVEntry(String loggerName,String level){
            this.loggerName = loggerName;
            this.level = level;
        }
    }
    
}
