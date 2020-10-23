package com.cy.ares.cluster.error;

public class ServerBootstarpException extends RuntimeException {

    public ServerBootstarpException() {
        super();
    }

    public ServerBootstarpException(String message) {
        super(message);
    }

    public ServerBootstarpException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerBootstarpException(Throwable cause) {
        super(cause);
    }

}
