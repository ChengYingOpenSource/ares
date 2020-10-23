package com.cy.ares.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author derek.wq
 * @date 2018-05-21
 * @since v1.0.0
 */
public class NetworkUtil {

    private static final Logger logger           = LoggerFactory.getLogger(NetworkUtil.class);

    private static final String WINDOWNS_OS_NAME = "windows";

    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return StringUtils.isNotBlank(osName) && osName.toLowerCase().contains(WINDOWNS_OS_NAME);
    }

    public static String getLocalIpStr() {
        try {
            if (isWindows()) {
                return InetAddress.getLocalHost().getHostAddress();
            } else {
                return ipToIPv4Str(getIpWithRule());
            }
        } catch (Exception e) {
            return "unknown";
        }
    }

    public static String ipToIPv4Str(byte[] ip) {
        if (ip == null) {
            return null;
        }
        if (ip.length != 4) {
            return null;
        }
        return new StringBuilder().append(ip[0] & 0xFF)
                                  .append(".")
                                  .append(ip[1] & 0xFF)
                                  .append(".")
                                  .append(ip[2] & 0xFF)
                                  .append(".")
                                  .append(ip[3] & 0xFF)
                                  .toString();
    }

    public static String getHostName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            logger.error("failed to get hostname", e);
            return null;
        }
    }

    /**
     * IP优先级：内网IP > 公网IP > 本地
     * 
     * @return
     */
    public static byte[] getIpWithRule() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            byte[] networkIp = null;
            byte[] localIp = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface == null
                    || !(netInterface.getDisplayName().contains("en") || netInterface.getDisplayName().contains("eth")
                         || netInterface.getDisplayName().contains("lo"))) {
                    continue;
                }
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        byte[] ipByte = ip.getAddress();
                        if (ipByte.length == 4) {
                            if (ipCheck(ipByte)) {
                                if (isInternalIP(ipByte)) {
                                    return ipByte;
                                } else if (isLocalIp(ipByte)) {
                                    localIp = ipByte;
                                } else if (networkIp == null) {
                                    networkIp = ipByte;
                                }
                            }
                        }
                    }
                }
            }
            if (networkIp != null) {
                return networkIp;
            }
            if (localIp != null) {
                return localIp;
            } else {
                throw new RuntimeException("Can not get local ip");
            }
        } catch (Exception e) {
            throw new RuntimeException("Can not get local ip", e);
        }
    }

    /**
     * 获取ip，如果存在非内网ip，则返回非内网ip，否则返回内网ip
     * 内网ip包括：
     * 10.0.0.0~10.255.255.255
     * 172.16.0.0~172.31.255.255
     * 192.168.0.0~192.168.255.255
     * 
     * @return
     */
    public static byte[] getIP() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            byte[] internalIP = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface == null) {
                    continue;
                }
                // 只查询en网卡的ip，虚拟网卡、其他网络接口设备的忽略
                if (!netInterface.getDisplayName().contains("en")) {
                    continue;
                }
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        System.out.println(ip.getHostName() + "\t" + ip.getHostAddress());
                        byte[] ipByte = ip.getAddress();
                        if (ipByte.length == 4) {
                            if (ipCheck(ipByte)) {
                                if (!isInternalIP(ipByte)) {
                                    return ipByte;
                                } else if (internalIP == null) {
                                    internalIP = ipByte;
                                }
                            }
                        }
                    }
                }
            }
            if (internalIP != null) {
                return internalIP;
            } else {
                throw new RuntimeException("Can not get local ip");
            }
        } catch (Exception e) {
            throw new RuntimeException("Can not get local ip", e);
        }
    }

    public static boolean isInternalIP(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

        // 10.0.0.0~10.255.255.255
        // 172.16.0.0~172.31.255.255
        // 192.168.0.0~192.168.255.255
        if (ip[0] == (byte) 10) {

            return true;
        } else if (ip[0] == (byte) 172) {
            if (ip[1] >= (byte) 16 && ip[1] <= (byte) 31) {
                return true;
            }
        } else if (ip[0] == (byte) 192) {
            if (ip[1] == (byte) 168) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLocalIp(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }
        // 127.0.0.1
        if (ip[0] == (byte) 127 && ip[1] == (byte) 0 && ip[2] == (byte) 0 && ip[3] == (byte) 1) {
            return true;
        }
        return false;
    }

    private static boolean ipCheck(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

        // if (ip[0] == (byte)30 && ip[1] == (byte)10 && ip[2] == (byte)163 && ip[3] == (byte)120) {
        // }

        if (ip[0] >= (byte) 1 && ip[0] <= (byte) 126) {
            if (ip[1] == (byte) 1 && ip[2] == (byte) 1 && ip[3] == (byte) 1) {
                return false;
            }
            if (ip[1] == (byte) 0 && ip[2] == (byte) 0 && ip[3] == (byte) 0) {
                return false;
            }
            return true;
        } else if (ip[0] >= (byte) 128 && ip[0] <= (byte) 191) {
            if (ip[2] == (byte) 1 && ip[3] == (byte) 1) {
                return false;
            }
            if (ip[2] == (byte) 0 && ip[3] == (byte) 0) {
                return false;
            }
            return true;
        } else if (ip[0] >= (byte) 192 && ip[0] <= (byte) 223) {
            if (ip[3] == (byte) 1) {
                return false;
            }
            if (ip[3] == (byte) 0) {
                return false;
            }
            return true;
        }
        return false;
    }
}
