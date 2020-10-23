package com.cy.ares.dao.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class Ares2AppDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column ares2_app.id
     */
    private Long id;

    /**
     * This field corresponds to the database column ares2_app.namespace_code
     */
    private String namespaceCode;

    /**
     * This field corresponds to the database column ares2_app.app_name
     */
    private String appName;

    /**
     * This field corresponds to the database column ares2_app.app_code
     */
    private String appCode;

    /**
     * This field corresponds to the database column ares2_app.type
     */
    private String type;

    /**
     * This field corresponds to the database column ares2_app.desc
     */
    private String desc;

    /**
     * This field corresponds to the database column ares2_app.create_user
     */
    private Long createUser;

    /**
     * This field corresponds to the database column ares2_app.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column ares2_app.modify_user
     */
    private Long modifyUser;

    /**
     * This field corresponds to the database column ares2_app.gmt_modified
     */
    private Date gmtModified;

    /**
     * This field corresponds to the database column ares2_app.create_user_account
     */
    private String createUserAccount;

    /**
     * This field corresponds to the database column ares2_app.modify_user_account
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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