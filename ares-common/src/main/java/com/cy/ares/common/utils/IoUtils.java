package com.cy.ares.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hll176411
 * @date 2018-12-26
 * @since v2.0.0
 */
public class IoUtils {

    public static final Logger logger = LoggerFactory.getLogger(IoUtils.class);

    public static void write(InputStream in, OutputStream out, int bufferSize) {
        if (bufferSize <= 0) {
            return;
        }
        // 不能超过 MAX bufferSize = 1024 = 1K
        try {
            byte[] buffer = new byte[bufferSize * 1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void write(String in, OutputStream out) {

        // 不能超过 MAX bufferSize = 1024 = 1K

        try {
            out.write(in.getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void write(byte[] in, OutputStream out) {

        // 不能超过 MAX bufferSize = 1024 = 1K

        try {
            out.write(in);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String read(InputStream in, int bufferSize) {
        StringBuilder sBuilder = new StringBuilder();
        try {
            byte[] buffer = new byte[bufferSize * 1024];
            int len = -1;

            while ((len = in.read(buffer)) != -1) {
                sBuilder.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return sBuilder.toString();
    }

    public static void closeInputStream(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            logger.error("closeInputStream failed", e);
            throw new RuntimeException(e);
        }
    }

    public static void closeOutputStream(OutputStream out) {
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
