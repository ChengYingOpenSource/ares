package com.cy.ares.deploy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年7月9日 下午8:14:24
 * @version V1.0
 */
@EnableCaching
@ComponentScan(basePackages = { "com.cy.ares"})
@SpringBootApplication(scanBasePackages = { "com.cy.ares","com.cy.onepush"})
public class AresApplication {
    private static final Logger LOG  = LoggerFactory.getLogger(AresApplication.class);

    public static void main(String[] args) {
        LOG.info("AresApplication Server Stating....");
        // 改造Class
        SpringApplication.run(AresApplication.class, args);
        LOG.info("AresApplication Server Start Success !");
    }
}
