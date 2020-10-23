package com.cy.ares.common.domain;


public class ResultMessage {

    
    public static ResultMessage build(int code,String msg ,Object data){
        ResultMessage rm = new ResultMessage();
        rm.setCode(code).setMsg(msg).setData(data).setSuccess(code==0?true:false);
        return rm;
    }
    
    public static ResultMessage build(boolean success,String msg ,Object data){
        ResultMessage rm = new ResultMessage();
        rm.setCode(success?0:1).setMsg(msg).setData(data).setSuccess(success);
        return rm;
    }
    
    public static ResultMessage build(int code,Object data){
        ResultMessage rm = new ResultMessage();
        rm.setCode(code).setData(data);
        return rm;
    }
    
    public static ResultMessage build(int code){
        ResultMessage rm = new ResultMessage();
        rm.setCode(code);
        return rm;
    }
    
    
    private long total;
    
    private int code;

    private String msg;

    private Object data;
    
    private Object params;
    
    private boolean success;
    
    private long time;
    
    public Object getParams() {
        return params;
    }

    
    public ResultMessage setParams(Object params) {
        this.params = params;
        return this;
    }

    public long getTotal() {
        return total;
    }
    
    public ResultMessage setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResultMessage setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultMessage setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean getSuccess() {
        return success;
    }
    
    
    public boolean isSuccess() {
        return success;
    }

    
    public ResultMessage setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public long getTime() {
        return time;
    }

    public ResultMessage setTime(long time) {
        this.time = time;
        return this;
    }
    
    

}
