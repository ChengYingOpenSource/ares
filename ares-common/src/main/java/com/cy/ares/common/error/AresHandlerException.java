package com.cy.ares.common.error;
import java.util.*;



public class AresHandlerException  extends RuntimeException {

    public AresHandlerException() {
        super();
    }

    public AresHandlerException(String message) {
        super(message);
    }

    public AresHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AresHandlerException(Throwable cause) {
        super(cause);
    }

}