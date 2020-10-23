package com.cy.ares.common.error;
import java.util.*;



public class CompressException  extends RuntimeException {

    public CompressException() {
        super();
    }

    public CompressException(String message) {
        super(message);
    }

    public CompressException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressException(Throwable cause) {
        super(cause);
    }

}
