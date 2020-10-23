package com.cy.ares.spcp.protocol;

import java.util.Date;

import static com.cy.ares.spcp.cst.ConfigCst.*;

public class DataItem {

    private String action;
    private String namespaceCode;
    private String appCode;
    private String envCode;
    private String clusterCode;

    private String group;

    private String dataId;
    private String content;
    private int contentSize;
    
    private String contentType;
    
    private String desc;
    private String rollbackFromHuid;
    private String traceId;

    protected Date gmtModified;
    private long lastTimeModify;

    // 配置原始内容的摘要
    private String digest;

    // 是否加密, 1加密 0 不加密
    private int encrypt;

    // 是否压缩, 0不压缩 1代表压缩 , 当前仅在内存和网络中进行压缩
    private int compress;
    // 压缩之后的内容
    private byte[] compressContent;

    // 第一次放入缓存的时间;
    private long signTime;
    
    public DataClusterKey clusterKey(){
        DataClusterKey dk = new DataClusterKey();
        dk.setNamespaceCode(namespaceCode);
        dk.setAppCode(appCode);
        dk.setEnvCode(envCode);
        dk.setClusterCode(clusterCode);
        return dk;        
    }
    public DataGroupKey groupKey(){
        DataGroupKey dg = new DataGroupKey();
        dg.setNamespaceCode(namespaceCode);
        dg.setAppCode(appCode);
        dg.setEnvCode(envCode);
        dg.setClusterCode(clusterCode);
        dg.setGroup(group);
        return dg;
    }
    
    
    public String key() {
        StringBuilder sb = new StringBuilder();
        sb.append(namespaceCode);
        sb.append(semicolon);
        sb.append(appCode);
        sb.append(semicolon);
        sb.append(envCode);
        sb.append(semicolon);
        sb.append(clusterCode);
        sb.append(semicolon);
        sb.append(group);
        sb.append(semicolon);
        sb.append(dataId);
        return sb.toString();
    }
    
    // 已知namespaceCode appCode envCode clusterCode情况下
    public static String keyWithGroup(String dataId, String group) {
        StringBuilder sb = new StringBuilder();
        sb.append(group);
        sb.append(semicolon);
        sb.append(dataId);
        return sb.toString();
    }
    
    
    public DataItem copy(){
        DataItem newItem =new DataItem();
        newItem.setNamespaceCode(namespaceCode);
        newItem.setAppCode(appCode);
        newItem.setEnvCode(envCode);
        newItem.setClusterCode(clusterCode);
        newItem.setGroup(group);
        
        newItem.setDataId(dataId);
        newItem.setContent(content);
        newItem.setDesc(desc);
        newItem.setDigest(digest);
        newItem.setCompress(compress);
        newItem.setContentSize(contentSize);
        newItem.setContentType(contentType);
        newItem.setEncrypt(encrypt);
        newItem.setLastTimeModify(lastTimeModify);
        newItem.setGmtModified(gmtModified);
        newItem.setSignTime(signTime);
        newItem.setRollbackFromHuid(rollbackFromHuid);
        
        return newItem;
    }
    
    
    public String getNamespaceCode() {
        return namespaceCode;
    }
    public void setNamespaceCode(String namespaceCode) {
        this.namespaceCode = namespaceCode;
    }
    public int getContentSize() {
        return contentSize;
    }
    public void setContentSize(int contentSize) {
        this.contentSize = contentSize;
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
    public byte[] getCompressContent() {
        return compressContent;
    }

    public void setCompressContent(byte[] compressContent) {
        this.compressContent = compressContent;
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

    public int getCompress() {
        return compress;
    }

    public void setCompress(int compress) {
        this.compress = compress;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getLastTimeModify() {
        return lastTimeModify;
    }

    public void setLastTimeModify(long lastTimeModify) {
        this.lastTimeModify = lastTimeModify;
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
    
    public Date getGmtModified() {
        return gmtModified;
    }
    
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        this.lastTimeModify = gmtModified.getTime();
    }
    
    public String getTraceId() {
        return traceId;
    }
    
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
