package com.cy.ares.api.basic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class WebInitListener implements ServletContextListener{
    
    private static Logger logger =  LoggerFactory.getLogger(WebInitListener.class);
    
	/**
	 * 初始化方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("服务器开启");
		
	}
 
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("服务器停止！");
		
	}
	
}