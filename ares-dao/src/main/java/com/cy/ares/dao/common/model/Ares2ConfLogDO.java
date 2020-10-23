package com.cy.ares.dao.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class Ares2ConfLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column ares2_conf_log.id
     */
    private Long id;

    /**
     * This field corresponds to the database column ares2_conf_log.trace_id
     */
    private String traceId;

    /**
     * This field corresponds to the database column ares2_conf_log.instance_id
     */
    private String instanceId;

    /**
     * This field corresponds to the database column ares2_conf_log.ip_addr
     */
    private String ipAddr;

    /**
     * This field corresponds to the database column ares2_conf_log.hostname
     */
    private String hostname;

    /**
     * This field corresponds to the database column ares2_conf_log.app_code
     */
    private String appCode;

    /**
     * This field corresponds to the database column ares2_conf_log.data_id
     */
    private String dataId;

    /**
     * This field corresponds to the database column ares2_conf_log.push_status
     */
    private String pushStatus;

    /**
     * This field corresponds to the database column ares2_conf_log.push_info
     */
    private String pushInfo;

    /**
     * This field corresponds to the database column ares2_conf_log.create_user
     */
    private Long createUser;

    /**
     * This field corresponds to the database column ares2_conf_log.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column ares2_conf_log.modify_user
     */
    private Long modifyUser;

    /**
     * This field corresponds to the database column ares2_conf_log.gmt_modified
     */
    private Date gmtModified;

    /**
     * This field corresponds to the database column ares2_conf_log.create_user_account
     */
    private String createUserAccount;

    /**
     * This field corresponds to the database column ares2_conf_log.modify_user_account
     */
    private String modifyUserAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getPushInfo() {
		return pushInfo;
	}

	public void setPushInfo(String pushInfo) {
		this.pushInfo = pushInfo;
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