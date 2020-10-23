package com.cy.ares.dao.common.query;


import com.cy.ares.dao.util.BaseCriteria;
import com.cy.ares.dao.util.BaseQuery;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ares2ClusterQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    public Ares2ClusterQuery() {
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
     * This class corresponds to the database table ares2_cluster
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
     * This class corresponds to the database table ares2_cluster
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