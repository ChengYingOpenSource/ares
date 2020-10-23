package com.cy.ares.api.basic;

import com.cy.ares.biz.admin.domain.OptionBO;
import com.cy.ares.common.domain.CodeCst;
import com.cy.ares.common.domain.ResultMessage;

public class DefaultController extends BaseController{
    
    
    public ResultMessage println(OptionBO op){
        if(op.isSuccess()){
            return ok(op.getMsg(),op.getData());
        }
        return fail(op.getMsg(),op.getData());
    }
    
    public ResultMessage ok(){
        return ResultMessage.build(CodeCst.OK,null,null).setSuccess(true);
    }
    
    public ResultMessage ok(String msg,Object data){
        return ResultMessage.build(CodeCst.OK,msg,data).setSuccess(true);
    }
    
    public ResultMessage page(Object data,long total){
        ResultMessage r = ResultMessage.build(CodeCst.OK,null,data);
        r.setTotal(total);
        return r.setSuccess(true);
    }
    
    public ResultMessage ok(Object data){
        
        return ResultMessage.build(CodeCst.OK,null,data).setSuccess(true);
    }
    
    public ResultMessage ok(Object data,int total){
        ResultMessage rm = ResultMessage.build(CodeCst.OK,null,data);
        rm.setTotal(total);
        return rm.setSuccess(true);
    }

    public ResultMessage ok(String msg,Object data,int total){
        ResultMessage rm = ResultMessage.build(CodeCst.OK,msg,data);
        rm.setTotal(total);
        return rm.setSuccess(true);
    }

    public ResultMessage oks(String msg){
        
        return ResultMessage.build(CodeCst.OK,msg,null).setSuccess(true);
    }
    
    public ResultMessage fail(String msg,Object data){
        
        return ResultMessage.build(CodeCst.ERROR,msg,data).setSuccess(false);
    }
    
    public ResultMessage fail(Object data){
        
        return ResultMessage.build(CodeCst.ERROR,null,data).setSuccess(false);
    }
    
    public ResultMessage fails(String msg){
        
        return ResultMessage.build(CodeCst.ERROR,msg,null).setSuccess(false);
    }
    
    public ResultMessage fail(){
        return ResultMessage.build(CodeCst.ERROR,null,null).setSuccess(false);
    }
    
    
    public ResultMessage build(String msg,boolean success){
        if(success){
            return ResultMessage.build(CodeCst.OK,msg,null).setSuccess(success);
        }else{
            return ResultMessage.build(CodeCst.ERROR,msg,null).setSuccess(success);
        }
        
    }
    
    public ResultMessage build(String msg,boolean success,Object data){
        if(success){
            return ResultMessage.build(CodeCst.OK,msg,data).setSuccess(success);
        }else{
            return ResultMessage.build(CodeCst.ERROR,msg,data).setSuccess(success);
        }
        
    }
    
   
    
}
