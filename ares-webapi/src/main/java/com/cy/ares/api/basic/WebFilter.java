package com.cy.ares.api.basic;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author maoxq
 */
public class WebFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(WebFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest hr  = (HttpServletRequest)request;
        try {
            Map<String, String>  m = getAllRequestParam(hr);
            logger.info("request url={},args={}", hr.getRequestURL(),JSON.toJSONString(m));
        } catch(Exception e){
            logger.error("request args parse exception={}",e.getMessage());
            logger.error(e.getMessage(),e);
        }

        long t1 = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            long t2 = System.currentTimeMillis();
            PageHelper.clearPage();
            logger.info("this request url={} exhaust time={}",hr.getRequestURL(),(t2-t1));
        }

    }

    private static final String LOGIN_HEADER = "x-cy-login-info-4t";

    private Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
        while (temp.hasMoreElements()) {
        String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
}