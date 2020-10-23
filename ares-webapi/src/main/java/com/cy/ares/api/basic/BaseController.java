package com.cy.ares.api.basic;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getParameterMap(HttpServletRequest request) {
        // 参数Map  
        Map properties = request.getParameterMap();
        // 返回值Map  
        Map<String, Object> returnMap = new HashMap<>();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry = null;
        String name = "";
        Object value = null;

        while (entries.hasNext()) {
            entry = (Map.Entry)entries.next();
            name = (String)entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                continue;
            } else if (valueObj instanceof String[]) {
                String[] values = (String[])valueObj;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < values.length; i++) {
                    sb.append(values[i]);
                    sb.append(",");
                }
                String strvalue = sb.toString();
                if (StringUtils.isNotBlank(strvalue)) {
                    strvalue = strvalue.substring(0, strvalue.length() - 1);
                }
                // 数组判断
                if (StringUtils.isNotBlank(strvalue) && strvalue.startsWith("[") && strvalue.endsWith("]")) {
                    try {
                        List<Object> list = JSON.parseArray(strvalue, Object.class);
                        value = list;
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        if (StringUtils.isNotBlank(strvalue)) {
                            value = JSON.parseObject(strvalue, Object.class);
                        } else {
                            value = strvalue;
                        }
                    } catch (Exception e) {
                        value = strvalue;
                    }
                }
            } else {
                value = valueObj;
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    public void sc(HttpServletResponse response, int httpStatus) {
        try {
            //response.sendError(400);这行代码会默认返回给浏览器一个400错误的HTTP页面；
            response.setStatus(httpStatus);
            PrintWriter pw = response.getWriter();
            pw.write("");  // 空 
            pw.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    public void writePicStream(HttpServletResponse response, String fileName, byte[] buffer) {
        if (buffer == null) {
            return;
        }
        ServletOutputStream out = null;
        try {
            //Date date = new Date();
            //response.setDateHeader("Last-Modified", date.getTime()); // Last-Modified:页面的最后生成时间
            //response.setDateHeader("Expires", date.getTime() + 86400000); // Expires:过时期限值
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/octet-stream");
            out = response.getOutputStream();

            out.write(buffer);
            out.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

}
