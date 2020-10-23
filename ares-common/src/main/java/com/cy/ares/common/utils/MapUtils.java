package com.cy.ares.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
public class MapUtils {

	public static Map<String, Object> build(Object... param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (param == null)
			return map;
		int len = param.length;
		for (int i = 0; i < len;) {
			Object key = param[i];
			if ((i + 1) < len) {
				Object value = param[i + 1];
				map.put((String) key, value);
				i += 2;
			}
		}

		return map;
	}
	
	
	public static <T> Map<String, Object> beanToMap(T obj) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null)
			return map;

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					Method getter = property.getReadMethod(); // 得到property对应的getter方法
					if(getter!=null){// 这个属性未必有这个方法 
						Object value = getter.invoke(obj);
						map.put(key, value);
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return map;

	}
	
}
