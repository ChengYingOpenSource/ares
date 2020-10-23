package com.cy.ares.dao.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Date;

public class Ares2ConfDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column ares2_conf.id
     */
    private long id;

    /**
     * This field corresponds to the database column ares2_conf.namespace_code
     */
    private String namespaceCode;

    /**
     * This field corresponds to the database column ares2_conf.app_code
     */
    private String appCode;

    /**
     * This field corresponds to the database column ares2_conf.env_code
     */
    private String envCode;

    /**
     * This field corresponds to the database column ares2_conf.cluster_code
     */
    private String clusterCode;

    /**
     * This field corresponds to the database column ares2_conf.group
     */
    private String group;

    /**
     * This field corresponds to the database column ares2_conf.data_id
     */
    private String dataId;

    /**
     * This field corresponds to the database column ares2_conf.content
     */
    private String content;

    /**
     * This field corresponds to the database column ares2_conf.content_size
     */
    private int contentSize;

    /**
     * This field corresponds to the database column ares2_conf.content_type
     */
    private String contentType;

    /**
     * This field corresponds to the database column ares2_conf.compress
     */
    private int compress;

    /**
     * This field corresponds to the database column ares2_conf.digest
     */
    private String digest;

    /**
     * This field corresponds to the database column ares2_conf.encrypt
     */
    private int encrypt;

    /**
     * This field corresponds to the database column ares2_conf.desc
     */
    private String desc;

    /**
     * This field corresponds to the database column ares2_conf.trace_id
     */
    private String traceId;

    /**
     * This field corresponds to the database column ares2_conf.rollback_from_huid
     */
    private String rollbackFromHuid;

    /**
     * This field corresponds to the database column ares2_conf.create_user
     */
    private long createUser;

    /**
     * This field corresponds to the database column ares2_conf.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column ares2_conf.modify_user
     */
    private long modifyUser;

    /**
     * This field corresponds to the database column ares2_conf.gmt_modified
     */
    private Date gmtModified;

    /**
     * This field corresponds to the database column ares2_conf.create_user_account
     */
    private String createUserAccount;

    /**
     * This field corresponds to the database column ares2_conf.modify_user_account
     */
    private String modifyUserAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getContentSize() {
        return contentSize;
    }

    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getCompress() {
        return compress;
    }

    public void setCompress(int compress) {
        this.compress = compress;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRollbackFromHuid() {
        return rollbackFromHuid;
    }

    public void setRollbackFromHuid(String rollbackFromHuid) {
        this.rollbackFromHuid = rollbackFromHuid;
    }

    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(long modifyUser) {
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
