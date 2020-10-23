package com.cy.ares.biz.admin.domain;

import java.util.function.Supplier;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@SuppressWarnings("all")
public class OptionBO<T> {

    private T       data;

    private boolean success;

    private String  msg;

    private String  code;

    /**
     * 异常错误信息
     */
    private String  exceptionMsg;

    public OptionBO() {

    }

    public static <F> OptionBO<F> nullOf(Object assertObj, Supplier<F> supplier) {
        if (assertObj == null) {
            return (OptionBO) OptionBO.fail();
        }
        OptionBO<F> optionBO = new OptionBO<F>("", true);
        optionBO.setData(supplier.get());
        return optionBO;
    }

    public static OptionBO ok() {
        OptionBO optionBO = new OptionBO("", true);
        return optionBO;
    }
    
    public static OptionBO oks(String msg) {
        OptionBO optionBO = new OptionBO(msg, true);
        return optionBO;
    }
    
    public static OptionBO ok(String msg,Object data) {
        OptionBO optionBO = new OptionBO("", true);
        optionBO.setData(data);
        optionBO.setMsg(msg);
        return optionBO;
    }
    
    public static OptionBO ok(Object data) {
        OptionBO optionBO = new OptionBO("", true);
        optionBO.setData(data);
        return optionBO;
    }

    public static <T> OptionBO<T> ok(T data, Class<T> type) {
        OptionBO<T> optionBO = new OptionBO<T>("", true);
        optionBO.setData(data);
        return optionBO;
    }

    public static OptionBO<Object> fail() {
        OptionBO<Object> optionBO = new OptionBO<Object>("", false);
        return optionBO;
    }

    public static OptionBO fail(String msg) {
        OptionBO optionBO = new OptionBO(msg, false);
        return optionBO;
    }

    public static OptionBO fail(String code, String msg) {
        OptionBO optionBO = new OptionBO(msg, false);
        optionBO.setCode(code);
        return optionBO;
    }
    
    public OptionBO(T data) {
        this.data = data;
        this.success = true;
    }
    
    public OptionBO(boolean success) {
        this.success = success;
    }

    public OptionBO(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public OptionBO<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public OptionBO<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public OptionBO<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public OptionBO<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public OptionBO<T> setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
