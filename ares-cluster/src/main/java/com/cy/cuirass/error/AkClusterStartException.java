package com.cy.cuirass.error;
import java.util.*;



public class AkClusterStartException extends RuntimeException {

    public AkClusterStartException() {
        super();
    }

    public AkClusterStartException(String message) {
        super(message);
    }

    public AkClusterStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public AkClusterStartException(Throwable cause) {
        super(cause);
    }

}
