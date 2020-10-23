package com.cy.ares.common.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

/**
 * @author derek.wq
 * @date 2018-05-16
 * @since v1.0.0
 */
public class ClassUtil {

    private static final String POINT = ".";

    /**
     * 获取传入类的所有字段名
     * 
     * @param clazz
     * @return
     */
    public static String[] getAllFieldName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();

        String[] fieldName = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldName[i] = fields[i].getName();
        }
        return fieldName;
    }

    /**
     * 将驼峰字符串转化成 下划线形式
     * 
     * @param camelStr
     * @return
     */
    public static String camelToUnderline(String camelStr) {
        if (StringUtils.isBlank(camelStr)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(camelStr)) {
            // 将第一个字符处理成小写
            sb.append(camelStr.substring(0, 1).toLowerCase());
            // 循环处理其他字符
            for (int i = 1; i < camelStr.length(); i++) {
                String s = camelStr.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    sb.append("_");
                }
                // 其他字符转成小写
                sb.append(s.toLowerCase());
            }
        }
        return sb.toString();
    }
}
