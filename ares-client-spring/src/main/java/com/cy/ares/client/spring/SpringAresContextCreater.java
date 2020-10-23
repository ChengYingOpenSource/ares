package com.cy.ares.client.spring;

import com.alibaba.fastjson.JSON;
import com.cy.ares.spcp.context.ClientInstanceFactory;
import com.cy.ares.spcp.context.SpcpConfig;
import com.cy.ares.spcp.context.SpcpContext;
import com.cy.ares.spcp.cst.ConfigCst;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@AutoConfigureAfter(SpringAresConfig.class)
@Component
public class SpringAresContextCreater {

    protected static final Logger logger = LoggerFactory.getLogger(SpringAresContextCreater.class);

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected SpringAresConfig aresConfig;

    @Bean(name = "spcpContext")
    public SpcpContext spcpContext() {

        if (!aresConfig.isEnable()) {
            logger.info("ares client is not enable!SpcpContext will be null!");
            return null;
        }
        SpcpConfig conf = new SpcpConfig();
        conf.setServerAddr(aresConfig.getServerAddr());

        String appCode = aresConfig.getAppCode();
        String envCode = aresConfig.getEnvCode();
        Environment env = context.getEnvironment();
        appCode = StringUtils.isBlank(appCode) ? env.getProperty("spring.application.name") : appCode;
        envCode = StringUtils.isBlank(envCode) ? env.getProperty("spring.cloud.config.profile") : envCode;
        if (StringUtils.isAnyBlank(appCode, envCode)) {
            logger.error("appCode={},envCode={},someOne is blank!", appCode, envCode);
            throw new IllegalArgumentException("appCode,envCode不能为空!");
        }
        String clusterCode = aresConfig.getClusterCode();
        clusterCode = StringUtils.isBlank(clusterCode) ? ConfigCst.CLUSTER_DEF : clusterCode;
        String namespaceCode = aresConfig.getNamespaceCode();
        namespaceCode = StringUtils.isBlank(namespaceCode) ? ConfigCst.NAMESPACE_DEF : namespaceCode;
        // 配置实例信息, appCode encCode clusterCode
        ClientInstanceInfo instanceInfo = ClientInstanceFactory.instanceClient(namespaceCode, appCode, envCode,
            clusterCode);

        SpcpContext sc = new SpcpContext(conf, instanceInfo);

        /**
         *   do not register
         *  <p> sc.getConfFetchActor().addListener("spring.logger.config",loggerLevelSwitchListener);</p>
         */

        if (aresConfig.isBootstrap()) {
            sc.bootstrap(); // 启动;
        } else {
            logger.warn("aresContext is config to Not Auto Boostrap!");
        }
        logger.info("create spcpContext,config={},instanceInfo={}", JSON.toJSONString(conf),
            JSON.toJSONString(instanceInfo));

        return sc;
    }

}
