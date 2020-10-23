package com.cy.ares.spcp.client.errors;
import java.util.*;



public class NetException extends RuntimeException {

    public NetException() {
        super();
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetException(Throwable cause) {
        super(cause);
    }

}
