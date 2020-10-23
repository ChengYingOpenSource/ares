package com.cy.ares.api.basic;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


@Configuration("AresFilterConfig")
@SuppressWarnings("all")
public class FilterConfig {
    
    @Bean(name = "WebAresFilterRegister")
    public FilterRegistrationBean onlineFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(webFilter());
        registration.addUrlPatterns("/ares/*");
        registration.setName("WebFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

    @Bean(name = "WebFilter")
    public WebFilter webFilter() {
        WebFilter wf =  new WebFilter();
       
        return wf;
    }
    

}
