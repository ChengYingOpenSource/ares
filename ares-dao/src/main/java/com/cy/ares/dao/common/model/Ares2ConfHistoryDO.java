package com.cy.ares.dao.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class Ares2ConfHistoryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column ares2_conf_history.id
     */
    private Long id;

    /**
     * This field corresponds to the database column ares2_conf_history.conf_history_uid
     */
    private String confHistoryUid;

    /**
     * This field corresponds to the database column ares2_conf_history.operation_type
     */
    private String operationType;

    /**
     * This field corresponds to the database column ares2_conf_history.namespace_code
     */
    private String namespaceCode;

    /**
     * This field corresponds to the database column ares2_conf_history.app_code
     */
    private String appCode;

    /**
     * This field corresponds to the database column ares2_conf_history.env_code
     */
    private String envCode;

    /**
     * This field corresponds to the database column ares2_conf_history.cluster_code
     */
    private String clusterCode;

    /**
     * This field corresponds to the database column ares2_conf_history.group
     */
    private String group;

    /**
     * This field corresponds to the database column ares2_conf_history.data_id
     */
    private String dataId;

    /**
     * This field corresponds to the database column ares2_conf_history.content
     */
    private String content;

    /**
     * This field corresponds to the database column ares2_conf_history.content_size
     */
    private Integer contentSize;

    /**
     * This field corresponds to the database column ares2_conf_history.content_type
     */
    private String contentType;

    /**
     * This field corresponds to the database column ares2_conf_history.compress
     */
    private Integer compress;

    /**
     * This field corresponds to the database column ares2_conf_history.digest
     */
    private String digest;

    /**
     * This field corresponds to the database column ares2_conf_history.encrypt
     */
    private Integer encrypt;

    /**
     * This field corresponds to the database column ares2_conf_history.desc
     */
    private String desc;

    /**
     * This field corresponds to the database column ares2_conf_history.rollback_from_huid
     */
    private String rollbackFromHuid;

    /**
     * This field corresponds to the database column ares2_conf_history.create_user
     */
    private Long createUser;

    /**
     * This field corresponds to the database column ares2_conf_history.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column ares2_conf_history.modify_user
     */
    private Long modifyUser;

    /**
     * This field corresponds to the database column ares2_conf_history.gmt_modified
     */
    private Date gmtModified;

    /**
     * This field corresponds to the database column ares2_conf_history.create_user_account
     */
    private String createUserAccount;

    /**
     * This field corresponds to the database column ares2_conf_history.modify_user_account
     */
    private String modifyUserAccount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConfHistoryUid() {
		return confHistoryUid;
	}

	public void setConfHistoryUid(String confHistoryUid) {
		this.confHistoryUid = confHistoryUid;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getContentSize() {
		return contentSize;
	}

	public void setContentSize(Integer contentSize) {
		this.contentSize = contentSize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getCompress() {
		return compress;
	}

	public void setCompress(Integer compress) {
		this.compress = compress;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Integer getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(Integer encrypt) {
		this.encrypt = encrypt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRollbackFromHuid() {
		return rollbackFromHuid;
	}

	public void setRollbackFromHuid(String rollbackFromHuid) {
		this.rollbackFromHuid = rollbackFromHuid;
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