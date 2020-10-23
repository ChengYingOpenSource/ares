package com.cy.ares.api;

import com.cy.ares.common.domain.ResultMessage;
import com.cy.ares.common.error.AresHandlerTipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMessage handleException(Exception e) {
        logger.error(e.getMessage(),e);
        ResultMessage r = ResultMessage.build(false,e.getMessage(),null);
        if(e instanceof AresHandlerTipException ){
            r.setCode(-400);
        }
        return r;
    }


}
