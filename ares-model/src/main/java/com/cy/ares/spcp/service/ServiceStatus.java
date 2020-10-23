package com.cy.ares.spcp.service;

import org.omg.CORBA.UNKNOWN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/11 15:00
 */
public enum ServiceStatus {
    
    UP, // Ready to receive traffic

    DOWN, // Do not send traffic- healthcheck callback failed

    STARTING, // Just about starting- initializations to be done - do not

    // send traffic
    OUT_OF_SERVICE, // Intentionally shutdown for traffic

    UNKNOWN;

    private static final Logger logger = LoggerFactory.getLogger(ServiceStatus.class);

    public static ServiceStatus toEnum(String s) {
        if (s != null) {
            try {
                return ServiceStatus.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                // ignore and fall through to unknown
                logger.debug("illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}", s, UNKNOWN);
            }
        }
        return UNKNOWN;
    }

}
