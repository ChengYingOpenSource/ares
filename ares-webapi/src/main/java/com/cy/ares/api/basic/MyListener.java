package com.cy.ares.api.basic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cy.ares.biz.cluster.AresCluster;

import java.util.concurrent.LinkedBlockingQueue;

@Component("myListener")  
public class MyListener implements ServletContextListener{  
    
    @Override  
    public void contextDestroyed(ServletContextEvent arg0) {  
        System.out.println("contextDestroyed...");  
    }  
    
    public static final Logger logger = LoggerFactory.getLogger(MyListener.class);
    
    @Autowired
    private ApplicationContext context;
    
    @Override  
    public void contextInitialized(ServletContextEvent sce) {  
        System.out.println("listener contextInitialized ...");  
        
        AresCluster cluster = context.getBean(AresCluster.class);
        cluster.start();
        
        String logPath = context.getEnvironment().getProperty("LOG_HOME");
        System.out.println("###--log="+logPath+"--###");
        System.out.println("###--服务启动初始化完成OK-!###");

        LinkedBlockingQueue<String> s = new LinkedBlockingQueue<>();
        
    }  
       
}  