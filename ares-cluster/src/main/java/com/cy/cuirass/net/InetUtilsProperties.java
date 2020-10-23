package com.cy.cuirass.net;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Spencer Gibb
 */
@Data
public class InetUtilsProperties {
	public static final String PREFIX = "spring.cloud.inetutils";

	/**
	 * The default hostname. Used in case of errors.
	 */
	private String defaultHostname = "localhost";

	/**
	 * The default ipaddress. Used in case of errors.
	 */
	private String defaultIpAddress = "127.0.0.1";

	/**
	 * Timeout in seconds for calculating hostname.
	 */
	private int timeoutSeconds = 1;

	/**
	 * List of Java regex expressions for network interfaces that will be ignored.
	 */
	private List<String> ignoredInterfaces = new ArrayList<>();
	
	/**
	 * Use only interfaces with site local addresses. See {@link InetAddress#isSiteLocalAddress()} for more details.
	 */
	private boolean useOnlySiteLocalInterfaces = false;
	
	/**
	 * List of Java regex expressions for network addresses that will be ignored.
	 */
	private List<String> preferredNetworks = new ArrayList<>();
}
