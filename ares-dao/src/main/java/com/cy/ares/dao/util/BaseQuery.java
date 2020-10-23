package com.cy.ares.dao.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    // 默认页大小
    protected static final int DEFAULT_PAGE_SIZE = 20;
    // 最大页大小
    private static final int MAX_PAGE_SIZE = 100;
    // 默认分页
    protected static final int DEFAULT_PAGE = 1;
    /**
     * order by clause.
     */
    protected String orderByClause;

    /**
     * table index
     */
    private String tableIndex;

    private String tableIndexQuery;

    /**
     * distinct
     */
    protected boolean distinct;

    /**
     * criteria list
     */
    protected List<BaseCriteria> oredCriteria;

    /**
     * page
     */
    protected Integer pageOffset;

    protected Integer pageNo;

    /**
     * page size
     */
    protected Integer pageSize;                // = DEFAULT_PAGE_SIZE ;

    public BaseQuery() {
        oredCriteria = new ArrayList<BaseCriteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<BaseCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(BaseCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    private void clearPage() {
        this.pageSize = null;
        this.pageNo = null;
        this.pageOffset = null;
    }

    private void calPageOffset() {
        if (null == pageSize || null == pageNo) {
            pageOffset = null;
        } else {
            pageOffset = (pageNo - 1) * pageSize;
        }
    }

    public boolean isValid() {
        for (int i = 0; i < oredCriteria.size(); i++) {
            if (oredCriteria.get(i).isValid()) {
                return true;
            }
        }
        return false;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            this.clearPage();
        } else if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MAX_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
        this.calPageOffset();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo == null || pageNo < DEFAULT_PAGE) {
            this.clearPage();
        } else {
            this.pageNo = pageNo;
        }
        this.calPageOffset();
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
        this.setTableIndexQuery(tableIndex);
    }

    public String getTableIndexQuery() {
        return tableIndexQuery;
    }

    private void setTableIndexQuery(String tableIndexQuery) {
        this.tableIndexQuery = tableIndexQuery;
    }
}
