package com.cy.ares.dao.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class Ares2ClusterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column ares2_cluster.id
     */
    private Long id;

    /**
     * This field corresponds to the database column ares2_cluster.namespace_code
     */
    private String namespaceCode;

    /**
     * This field corresponds to the database column ares2_cluster.app_code
     */
    private String appCode;

    /**
     * This field corresponds to the database column ares2_cluster.env_code
     */
    private String envCode;

    /**
     * This field corresponds to the database column ares2_cluster.cluster_code
     */
    private String clusterCode;

    /**
     * This field corresponds to the database column ares2_cluster.create_user
     */
    private Long createUser;

    /**
     * This field corresponds to the database column ares2_cluster.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column ares2_cluster.modify_user
     */
    private Long modifyUser;

    /**
     * This field corresponds to the database column ares2_cluster.gmt_modified
     */
    private Date gmtModified;

    /**
     * This field corresponds to the database column ares2_cluster.create_user_account
     */
    private String createUserAccount;

    /**
     * This field corresponds to the database column ares2_cluster.modify_user_account
     */
    private String modifyUserAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNamespaceCode() {
		return namespaceCode;
	}

	public void setNamespaceCode(String namespaceCode) {
		this.namespaceCode = namespaceCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getEnvCode() {
		return envCode;
	}

	public void setEnvCode(String envCode) {
		this.envCode = envCode;
	}

	public String getClusterCode() {
		return clusterCode;
	}

	public void setClusterCode(String clusterCode) {
		this.clusterCode = clusterCode;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Long getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getCreateUserAccount() {
		return createUserAccount;
	}

	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}

	public String getModifyUserAccount() {
		return modifyUserAccount;
	}

	public void setModifyUserAccount(String modifyUserAccount) {
		this.modifyUserAccount = modifyUserAccount;
	}


    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }
}