package com.cy.cuirass.net;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.concurrent.*;

/**
 * @author Spencer Gibb
 */

public class InetUtils implements Closeable {

    private Logger log = LoggerFactory.getLogger(InetUtils.class);

    // TODO: maybe shutdown the thread pool if it isn't being used?
    private final ExecutorService executorService;
    private final InetUtilsProperties properties;
    private static final InetUtils instance = new InetUtils(new InetUtilsProperties());

    public InetUtils(final InetUtilsProperties properties) {
        this.properties = properties;
        this.executorService = Executors
            .newSingleThreadExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName(InetUtilsProperties.PREFIX);
                    thread.setDaemon(true);
                    return thread;
                }
            });
    }

    @Override
    public void close() {
        executorService.shutdown();
    }

    public HostInfo findFirstNonLoopbackHostInfo() {
        InetAddress address = findFirstNonLoopbackAddress();
        if (address != null) {
            return convertAddress(address);
        }
        HostInfo hostInfo = new HostInfo();
        hostInfo.setHostname(this.properties.getDefaultHostname());
        hostInfo.setIpAddress(this.properties.getDefaultIpAddress());
        return hostInfo;
    }

    public InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                .getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    log.trace("Testing interface: " + ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }

                    // @formatter:off
					if (!ignoreInterface(ifc.getDisplayName())) {
						for (Enumeration<InetAddress> addrs = ifc
								.getInetAddresses(); addrs.hasMoreElements();) {
							InetAddress address = addrs.nextElement();
							if (address instanceof Inet4Address
									&& !address.isLoopbackAddress()
									&& !ignoreAddress(address)) {
								log.trace("Found non-loopback interface: "
										+ ifc.getDisplayName());
								result = address;
							}
						}
					}
					// @formatter:on
                }
            }
        } catch (IOException ex) {
            log.error("Cannot get first non-loopback address", ex);
        }

        if (result != null) {
            return result;
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.warn("Unable to retrieve localhost");
        }

        return null;
    }

    /**
     * for testing
     */
    boolean ignoreAddress(InetAddress address) {

        if (this.properties.isUseOnlySiteLocalInterfaces() && !address.isSiteLocalAddress()) {
            log.trace("Ignoring address: " + address.getHostAddress());
            return true;
        }

        for (String regex : this.properties.getPreferredNetworks()) {
            if (!address.getHostAddress().matches(regex) && !address.getHostAddress().startsWith(regex)) {
                log.trace("Ignoring address: " + address.getHostAddress());
                return true;
            }
        }
        return false;
    }

    /**
     * for testing
     */
    boolean ignoreInterface(String interfaceName) {
        for (String regex : this.properties.getIgnoredInterfaces()) {
            if (interfaceName.matches(regex)) {
                log.trace("Ignoring interface: " + interfaceName);
                return true;
            }
        }
        return false;
    }

    public HostInfo convertAddress(final InetAddress address) {
        HostInfo hostInfo = new HostInfo();
        Future<String> result = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return address.getHostName();
            }
        });

        String hostname;
        try {
            hostname = result.get(this.properties.getTimeoutSeconds(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.info("Cannot determine local hostname");
            hostname = "localhost";
        }
        hostInfo.setHostname(hostname);
        hostInfo.setIpAddress(address.getHostAddress());
        return hostInfo;
    }

    /**
     * Find the first non-loopback host info. If there were errors return a host info with 'localhost' and '127.0.0.1'
     * for hostname and ipAddress respectively.
     *
     * @deprecated use the non-static findFirstNonLoopbackHostInfo() instead
     */
    @Deprecated
    public static HostInfo getFirstNonLoopbackHostInfo() {
        return instance.findFirstNonLoopbackHostInfo();
    }

    /**
     * Convert an internet address to a HostInfo.
     *
     * @deprecated use the non-static convertAddress() instead
     */
    @Deprecated
    public static HostInfo convert(final InetAddress address) {
        return instance.convertAddress(address);
    }

    public static int getIpAddressAsInt(String host) {
        return new HostInfo(host).getIpAddressAsInt();
    }

    @Data
    public static final class HostInfo {
        private boolean override;
        private String ipAddress;
        private String hostname;

        HostInfo(String hostname) {
            this.hostname = hostname;
        }

        HostInfo() {
        }

        public int getIpAddressAsInt() {
            InetAddress inetAddress = null;
            String host = this.ipAddress;
            if (host == null) {
                host = this.hostname;
            }
            try {
                inetAddress = InetAddress.getByName(host);
            } catch (final UnknownHostException e) {
                throw new IllegalArgumentException(e);
            }
            return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
        }
    }

}
