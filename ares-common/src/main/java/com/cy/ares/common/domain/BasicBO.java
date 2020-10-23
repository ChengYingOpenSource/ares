package com.cy.ares.common.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author maoxq
 */
public class BasicBO {
    
    /**
    * 
    */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最后更新时间
     */
    private Date gmtModified;

    /**
     * 记录创建者ID
     */
    private Long createUser = new Long(-1);

    /**
     * entity最后修改者id
     */
    private Long modifyuser = new Long(-1);

    protected String createUserAccount;

    protected String modifyUserAccount;

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

    public void setUserAccount(String userAccount){
        this.createUserAccount = userAccount;
        this.modifyUserAccount = userAccount;
    }


    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getModifyuser() {
        return modifyuser;
    }

    public void setModifyuser(Long modifyuser) {
        this.modifyuser = modifyuser;
    }

    public BasicBO setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public BasicBO setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public BasicBO setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
