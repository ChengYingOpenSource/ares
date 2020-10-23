package com.cy.ares.common.error;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/9/3 13:36
 */
public class AresHandlerTipException extends RuntimeException {

    public AresHandlerTipException() {
        super();
    }

    public AresHandlerTipException(String message) {
        super(message);
    }

    public AresHandlerTipException(String message, Throwable cause) {
        super(message, cause);
    }

    public AresHandlerTipException(Throwable cause) {
        super(cause);
    }

}