package com.cy.ares.spcp.locator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author derek.wq
 * @date 2018-05-23
 * @since v1.0.0
 */
@Deprecated
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AresValue {

    /**
     * 配置所属应用名。默认取：客户端端appName
     */
    String appName() default "";

    /**
     * 配置所属环境。默认取：客户端当前profile
     */
    String profile() default "";

    /**
     * 配置所属group。
     * 当appName, profile 未显示指定时（即取默认值），则group取 客户端所属group。
     * 当appName, profile 显示指定时（即appName，profile）显示指定时，group未指定，默认取：default
     */
    String group() default "";

    /**
     * 配置的dataId，不能为空
     */
    String dataId();

    /**
     * 配置变化触发的事件
     */
    // Class<? extends AbstractChangedEvent> changedEvent() default DefaultChangedEvent.class;

    /**
     * 配置变化触发的事件
     */
    // Class<? extends AbstractChangedEvent>[] changedEvents() default { DefaultChangedEvent.class };
}
