package com.cy.ares.biz.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年5月20日 上午12:01:09
 * @version V1.0
 */
public class MbeanUtils extends org.springframework.beans.BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(MbeanUtils.class);

    public Object cloneBean(Object bean)
        throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return BeanUtilsBean.getInstance().cloneBean(bean);
    }

    public static void copyPropertiesList(List sourceList, List targetList, Class clazz) {
        if (sourceList == null || sourceList.isEmpty()) {
            return;
        }
        for (Object items : sourceList) {
            Object target;
            try {
                target = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            copyProperties(items, target);
            targetList.add(target);
        }
    }

}
