package com.cy.ares.dao.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class Ares2MockHttpDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column ares2_mock_http.id
     */
    private Long id;

    /**
     * This field corresponds to the database column ares2_mock_http.app_code
     */
    private String appCode;

    /**
     * This field corresponds to the database column ares2_mock_http.method
     */
    private String method;

    /**
     * This field corresponds to the database column ares2_mock_http.content_type
     */
    private String contentType;

    /**
     * This field corresponds to the database column ares2_mock_http.url
     */
    private String url;

    /**
     * This field corresponds to the database column ares2_mock_http.script
     */
    private String script;

    /**
     * This field corresponds to the database column ares2_mock_http.type
     */
    private String type;

    /**
     * This field corresponds to the database column ares2_mock_http.dataset
     */
    private String dataset;

    /**
     * This field corresponds to the database column ares2_mock_http.remark
     */
    private String remark;

    /**
     * This field corresponds to the database column ares2_mock_http.create_user
     */
    private Long createUser;

    /**
     * This field corresponds to the database column ares2_mock_http.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column ares2_mock_http.modify_user
     */
    private Long modifyUser;

    /**
     * This field corresponds to the database column ares2_mock_http.gmt_modified
     */
    private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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


    @Override
    public String toString(){
        return ReflectionToStringBuilder.toString(this, ToStringStyle.DEFAULT_STYLE);
    }
}