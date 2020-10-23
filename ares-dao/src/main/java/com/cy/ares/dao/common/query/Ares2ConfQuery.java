package com.cy.ares.dao.common.query;


import com.cy.ares.dao.util.BaseCriteria;
import com.cy.ares.dao.util.BaseQuery;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ares2ConfQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    public Ares2ConfQuery() {
        super();
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        super.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This class corresponds to the database table ares2_conf
     */
         protected abstract static class GeneratedCriteria extends BaseCriteria {

        protected GeneratedCriteria() {
            super();
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria anIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeIsNull() {
            addCriterion("namespace_code is null");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeIsNotNull() {
            addCriterion("namespace_code is not null");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeEqualTo(String value) {
            addCriterion("namespace_code =", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeNotEqualTo(String value) {
            addCriterion("namespace_code <>", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeGreaterThan(String value) {
            addCriterion("namespace_code >", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("namespace_code >=", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeLessThan(String value) {
            addCriterion("namespace_code <", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeLessThanOrEqualTo(String value) {
            addCriterion("namespace_code <=", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeLike(String value) {
            addCriterion("namespace_code like", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeNotLike(String value) {
            addCriterion("namespace_code not like", value, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeIn(List<String> values) {
            addCriterion("namespace_code in", values, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeNotIn(List<String> values) {
            addCriterion("namespace_code not in", values, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeBetween(String value1, String value2) {
            addCriterion("namespace_code between", value1, value2, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andNamespaceCodeNotBetween(String value1, String value2) {
            addCriterion("namespace_code not between", value1, value2, "namespaceCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNull() {
            addCriterion("app_code is null");
            return (Criteria) this;
        }

        public Criteria andAppCodeIsNotNull() {
            addCriterion("app_code is not null");
            return (Criteria) this;
        }

        public Criteria andAppCodeEqualTo(String value) {
            addCriterion("app_code =", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotEqualTo(String value) {
            addCriterion("app_code <>", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThan(String value) {
            addCriterion("app_code >", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeGreaterThanOrEqualTo(String value) {
            addCriterion("app_code >=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThan(String value) {
            addCriterion("app_code <", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLessThanOrEqualTo(String value) {
            addCriterion("app_code <=", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeLike(String value) {
            addCriterion("app_code like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotLike(String value) {
            addCriterion("app_code not like", value, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeIn(List<String> values) {
            addCriterion("app_code in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotIn(List<String> values) {
            addCriterion("app_code not in", values, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeBetween(String value1, String value2) {
            addCriterion("app_code between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andAppCodeNotBetween(String value1, String value2) {
            addCriterion("app_code not between", value1, value2, "appCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeIsNull() {
            addCriterion("env_code is null");
            return (Criteria) this;
        }

        public Criteria andEnvCodeIsNotNull() {
            addCriterion("env_code is not null");
            return (Criteria) this;
        }

        public Criteria andEnvCodeEqualTo(String value) {
            addCriterion("env_code =", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeNotEqualTo(String value) {
            addCriterion("env_code <>", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeGreaterThan(String value) {
            addCriterion("env_code >", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeGreaterThanOrEqualTo(String value) {
            addCriterion("env_code >=", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeLessThan(String value) {
            addCriterion("env_code <", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeLessThanOrEqualTo(String value) {
            addCriterion("env_code <=", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeLike(String value) {
            addCriterion("env_code like", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeNotLike(String value) {
            addCriterion("env_code not like", value, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeIn(List<String> values) {
            addCriterion("env_code in", values, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeNotIn(List<String> values) {
            addCriterion("env_code not in", values, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeBetween(String value1, String value2) {
            addCriterion("env_code between", value1, value2, "envCode");
            return (Criteria) this;
        }

        public Criteria andEnvCodeNotBetween(String value1, String value2) {
            addCriterion("env_code not between", value1, value2, "envCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeIsNull() {
            addCriterion("cluster_code is null");
            return (Criteria) this;
        }

        public Criteria andClusterCodeIsNotNull() {
            addCriterion("cluster_code is not null");
            return (Criteria) this;
        }

        public Criteria andClusterCodeEqualTo(String value) {
            addCriterion("cluster_code =", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeNotEqualTo(String value) {
            addCriterion("cluster_code <>", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeGreaterThan(String value) {
            addCriterion("cluster_code >", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_code >=", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeLessThan(String value) {
            addCriterion("cluster_code <", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeLessThanOrEqualTo(String value) {
            addCriterion("cluster_code <=", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeLike(String value) {
            addCriterion("cluster_code like", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeNotLike(String value) {
            addCriterion("cluster_code not like", value, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeIn(List<String> values) {
            addCriterion("cluster_code in", values, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeNotIn(List<String> values) {
            addCriterion("cluster_code not in", values, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeBetween(String value1, String value2) {
            addCriterion("cluster_code between", value1, value2, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andClusterCodeNotBetween(String value1, String value2) {
            addCriterion("cluster_code not between", value1, value2, "clusterCode");
            return (Criteria) this;
        }

        public Criteria andGroupIsNull() {
            addCriterion("`group` is null");
            return (Criteria) this;
        }

        public Criteria andGroupIsNotNull() {
            addCriterion("`group` is not null");
            return (Criteria) this;
        }

        public Criteria andGroupEqualTo(String value) {
            addCriterion("`group` =", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotEqualTo(String value) {
            addCriterion("`group` <>", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupGreaterThan(String value) {
            addCriterion("`group` >", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupGreaterThanOrEqualTo(String value) {
            addCriterion("`group` >=", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLessThan(String value) {
            addCriterion("`group` <", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLessThanOrEqualTo(String value) {
            addCriterion("`group` <=", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupLike(String value) {
            addCriterion("`group` like", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotLike(String value) {
            addCriterion("`group` not like", value, "group");
            return (Criteria) this;
        }

        public Criteria andGroupIn(List<String> values) {
            addCriterion("`group` in", values, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotIn(List<String> values) {
            addCriterion("`group` not in", values, "group");
            return (Criteria) this;
        }

        public Criteria andGroupBetween(String value1, String value2) {
            addCriterion("`group` between", value1, value2, "group");
            return (Criteria) this;
        }

        public Criteria andGroupNotBetween(String value1, String value2) {
            addCriterion("`group` not between", value1, value2, "group");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNull() {
            addCriterion("data_id is null");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNotNull() {
            addCriterion("data_id is not null");
            return (Criteria) this;
        }

        public Criteria andDataIdEqualTo(String value) {
            addCriterion("data_id =", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotEqualTo(String value) {
            addCriterion("data_id <>", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThan(String value) {
            addCriterion("data_id >", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThanOrEqualTo(String value) {
            addCriterion("data_id >=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThan(String value) {
            addCriterion("data_id <", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThanOrEqualTo(String value) {
            addCriterion("data_id <=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLike(String value) {
            addCriterion("data_id like", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotLike(String value) {
            addCriterion("data_id not like", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdIn(List<String> values) {
            addCriterion("data_id in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotIn(List<String> values) {
            addCriterion("data_id not in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdBetween(String value1, String value2) {
            addCriterion("data_id between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotBetween(String value1, String value2) {
            addCriterion("data_id not between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentSizeIsNull() {
            addCriterion("content_size is null");
            return (Criteria) this;
        }

        public Criteria andContentSizeIsNotNull() {
            addCriterion("content_size is not null");
            return (Criteria) this;
        }

        public Criteria andContentSizeEqualTo(Integer value) {
            addCriterion("content_size =", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotEqualTo(Integer value) {
            addCriterion("content_size <>", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeGreaterThan(Integer value) {
            addCriterion("content_size >", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_size >=", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeLessThan(Integer value) {
            addCriterion("content_size <", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeLessThanOrEqualTo(Integer value) {
            addCriterion("content_size <=", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeLike(Integer value) {
            addCriterion("content_size like", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotLike(Integer value) {
            addCriterion("content_size not like", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeIn(List<Integer> values) {
            addCriterion("content_size in", values, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotIn(List<Integer> values) {
            addCriterion("content_size not in", values, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeBetween(Integer value1, Integer value2) {
            addCriterion("content_size between", value1, value2, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("content_size not between", value1, value2, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNull() {
            addCriterion("content_type is null");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNotNull() {
            addCriterion("content_type is not null");
            return (Criteria) this;
        }

        public Criteria andContentTypeEqualTo(String value) {
            addCriterion("content_type =", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotEqualTo(String value) {
            addCriterion("content_type <>", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThan(String value) {
            addCriterion("content_type >", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("content_type >=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThan(String value) {
            addCriterion("content_type <", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThanOrEqualTo(String value) {
            addCriterion("content_type <=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLike(String value) {
            addCriterion("content_type like", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotLike(String value) {
            addCriterion("content_type not like", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeIn(List<String> values) {
            addCriterion("content_type in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotIn(List<String> values) {
            addCriterion("content_type not in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeBetween(String value1, String value2) {
            addCriterion("content_type between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotBetween(String value1, String value2) {
            addCriterion("content_type not between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andCompressIsNull() {
            addCriterion("compress is null");
            return (Criteria) this;
        }

        public Criteria andCompressIsNotNull() {
            addCriterion("compress is not null");
            return (Criteria) this;
        }

        public Criteria andCompressEqualTo(Integer value) {
            addCriterion("compress =", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressNotEqualTo(Integer value) {
            addCriterion("compress <>", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressGreaterThan(Integer value) {
            addCriterion("compress >", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressGreaterThanOrEqualTo(Integer value) {
            addCriterion("compress >=", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressLessThan(Integer value) {
            addCriterion("compress <", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressLessThanOrEqualTo(Integer value) {
            addCriterion("compress <=", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressLike(Integer value) {
            addCriterion("compress like", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressNotLike(Integer value) {
            addCriterion("compress not like", value, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressIn(List<Integer> values) {
            addCriterion("compress in", values, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressNotIn(List<Integer> values) {
            addCriterion("compress not in", values, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressBetween(Integer value1, Integer value2) {
            addCriterion("compress between", value1, value2, "compress");
            return (Criteria) this;
        }

        public Criteria andCompressNotBetween(Integer value1, Integer value2) {
            addCriterion("compress not between", value1, value2, "compress");
            return (Criteria) this;
        }

        public Criteria andDigestIsNull() {
            addCriterion("digest is null");
            return (Criteria) this;
        }

        public Criteria andDigestIsNotNull() {
            addCriterion("digest is not null");
            return (Criteria) this;
        }

        public Criteria andDigestEqualTo(String value) {
            addCriterion("digest =", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestNotEqualTo(String value) {
            addCriterion("digest <>", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestGreaterThan(String value) {
            addCriterion("digest >", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestGreaterThanOrEqualTo(String value) {
            addCriterion("digest >=", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestLessThan(String value) {
            addCriterion("digest <", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestLessThanOrEqualTo(String value) {
            addCriterion("digest <=", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestLike(String value) {
            addCriterion("digest like", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestNotLike(String value) {
            addCriterion("digest not like", value, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestIn(List<String> values) {
            addCriterion("digest in", values, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestNotIn(List<String> values) {
            addCriterion("digest not in", values, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestBetween(String value1, String value2) {
            addCriterion("digest between", value1, value2, "digest");
            return (Criteria) this;
        }

        public Criteria andDigestNotBetween(String value1, String value2) {
            addCriterion("digest not between", value1, value2, "digest");
            return (Criteria) this;
        }

        public Criteria andEncryptIsNull() {
            addCriterion("encrypt is null");
            return (Criteria) this;
        }

        public Criteria andEncryptIsNotNull() {
            addCriterion("encrypt is not null");
            return (Criteria) this;
        }

        public Criteria andEncryptEqualTo(Integer value) {
            addCriterion("encrypt =", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptNotEqualTo(Integer value) {
            addCriterion("encrypt <>", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptGreaterThan(Integer value) {
            addCriterion("encrypt >", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptGreaterThanOrEqualTo(Integer value) {
            addCriterion("encrypt >=", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptLessThan(Integer value) {
            addCriterion("encrypt <", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptLessThanOrEqualTo(Integer value) {
            addCriterion("encrypt <=", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptLike(Integer value) {
            addCriterion("encrypt like", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptNotLike(Integer value) {
            addCriterion("encrypt not like", value, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptIn(List<Integer> values) {
            addCriterion("encrypt in", values, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptNotIn(List<Integer> values) {
            addCriterion("encrypt not in", values, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptBetween(Integer value1, Integer value2) {
            addCriterion("encrypt between", value1, value2, "encrypt");
            return (Criteria) this;
        }

        public Criteria andEncryptNotBetween(Integer value1, Integer value2) {
            addCriterion("encrypt not between", value1, value2, "encrypt");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("`desc` like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("`desc` not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNull() {
            addCriterion("trace_id is null");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNotNull() {
            addCriterion("trace_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraceIdEqualTo(String value) {
            addCriterion("trace_id =", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotEqualTo(String value) {
            addCriterion("trace_id <>", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThan(String value) {
            addCriterion("trace_id >", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThanOrEqualTo(String value) {
            addCriterion("trace_id >=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThan(String value) {
            addCriterion("trace_id <", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThanOrEqualTo(String value) {
            addCriterion("trace_id <=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLike(String value) {
            addCriterion("trace_id like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotLike(String value) {
            addCriterion("trace_id not like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdIn(List<String> values) {
            addCriterion("trace_id in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotIn(List<String> values) {
            addCriterion("trace_id not in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdBetween(String value1, String value2) {
            addCriterion("trace_id between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotBetween(String value1, String value2) {
            addCriterion("trace_id not between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidIsNull() {
            addCriterion("rollback_from_huid is null");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidIsNotNull() {
            addCriterion("rollback_from_huid is not null");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidEqualTo(String value) {
            addCriterion("rollback_from_huid =", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidNotEqualTo(String value) {
            addCriterion("rollback_from_huid <>", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidGreaterThan(String value) {
            addCriterion("rollback_from_huid >", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidGreaterThanOrEqualTo(String value) {
            addCriterion("rollback_from_huid >=", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidLessThan(String value) {
            addCriterion("rollback_from_huid <", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidLessThanOrEqualTo(String value) {
            addCriterion("rollback_from_huid <=", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidLike(String value) {
            addCriterion("rollback_from_huid like", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidNotLike(String value) {
            addCriterion("rollback_from_huid not like", value, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidIn(List<String> values) {
            addCriterion("rollback_from_huid in", values, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidNotIn(List<String> values) {
            addCriterion("rollback_from_huid not in", values, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidBetween(String value1, String value2) {
            addCriterion("rollback_from_huid between", value1, value2, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andRollbackFromHuidNotBetween(String value1, String value2) {
            addCriterion("rollback_from_huid not between", value1, value2, "rollbackFromHuid");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(Long value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(Long value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Long> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Long> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Long value1, Long value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Long value1, Long value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserIsNull() {
            addCriterion("modify_user is null");
            return (Criteria) this;
        }

        public Criteria andModifyUserIsNotNull() {
            addCriterion("modify_user is not null");
            return (Criteria) this;
        }

        public Criteria andModifyUserEqualTo(Long value) {
            addCriterion("modify_user =", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserNotEqualTo(Long value) {
            addCriterion("modify_user <>", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserGreaterThan(Long value) {
            addCriterion("modify_user >", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserGreaterThanOrEqualTo(Long value) {
            addCriterion("modify_user >=", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserLessThan(Long value) {
            addCriterion("modify_user <", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserLessThanOrEqualTo(Long value) {
            addCriterion("modify_user <=", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserLike(Long value) {
            addCriterion("modify_user like", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserNotLike(Long value) {
            addCriterion("modify_user not like", value, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserIn(List<Long> values) {
            addCriterion("modify_user in", values, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserNotIn(List<Long> values) {
            addCriterion("modify_user not in", values, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserBetween(Long value1, Long value2) {
            addCriterion("modify_user between", value1, value2, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andModifyUserNotBetween(Long value1, Long value2) {
            addCriterion("modify_user not between", value1, value2, "modifyUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountIsNull() {
            addCriterion("create_user_account is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountIsNotNull() {
            addCriterion("create_user_account is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountEqualTo(String value) {
            addCriterion("create_user_account =", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountNotEqualTo(String value) {
            addCriterion("create_user_account <>", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountGreaterThan(String value) {
            addCriterion("create_user_account >", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_account >=", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountLessThan(String value) {
            addCriterion("create_user_account <", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountLessThanOrEqualTo(String value) {
            addCriterion("create_user_account <=", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountLike(String value) {
            addCriterion("create_user_account like", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountNotLike(String value) {
            addCriterion("create_user_account not like", value, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountIn(List<String> values) {
            addCriterion("create_user_account in", values, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountNotIn(List<String> values) {
            addCriterion("create_user_account not in", values, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountBetween(String value1, String value2) {
            addCriterion("create_user_account between", value1, value2, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andCreateUserAccountNotBetween(String value1, String value2) {
            addCriterion("create_user_account not between", value1, value2, "createUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountIsNull() {
            addCriterion("modify_user_account is null");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountIsNotNull() {
            addCriterion("modify_user_account is not null");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountEqualTo(String value) {
            addCriterion("modify_user_account =", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountNotEqualTo(String value) {
            addCriterion("modify_user_account <>", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountGreaterThan(String value) {
            addCriterion("modify_user_account >", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountGreaterThanOrEqualTo(String value) {
            addCriterion("modify_user_account >=", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountLessThan(String value) {
            addCriterion("modify_user_account <", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountLessThanOrEqualTo(String value) {
            addCriterion("modify_user_account <=", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountLike(String value) {
            addCriterion("modify_user_account like", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountNotLike(String value) {
            addCriterion("modify_user_account not like", value, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountIn(List<String> values) {
            addCriterion("modify_user_account in", values, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountNotIn(List<String> values) {
            addCriterion("modify_user_account not in", values, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountBetween(String value1, String value2) {
            addCriterion("modify_user_account between", value1, value2, "modifyUserAccount");
            return (Criteria) this;
        }

        public Criteria andModifyUserAccountNotBetween(String value1, String value2) {
            addCriterion("modify_user_account not between", value1, value2, "modifyUserAccount");
            return (Criteria) this;
        }

    }

    /**
     * This class corresponds to the database table ares2_conf
    */
    public  static class Criteria extends GeneratedCriteria{
        protected Criteria() {
            super();
        }
    }



    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
