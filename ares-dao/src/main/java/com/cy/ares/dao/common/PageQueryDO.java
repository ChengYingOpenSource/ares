package com.cy.ares.dao.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author derek.wq
 * @date 2018-05-15
 * @since v1.0.0
 */
public class PageQueryDO implements Serializable {

    public static final int   DEFAULT_START    = 0;
    public static final int   DEFAULT_SIZE     = 20;
    private static final long serialVersionUID = 2611153631137455437L;
    /**
     * 查询起始位置
     */
    private Integer           start;
    /**
     * limit查询叶大小
     */
    private Integer           pageSize;
    /**
     * 页号
     */
    private Integer           pageNo;
    /**
     * 排序项
     */
    private String            orderColumn;
    /**
     * 排序类型
     */
    private String            orderType;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
