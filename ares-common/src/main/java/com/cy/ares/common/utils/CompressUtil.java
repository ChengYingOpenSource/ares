package com.cy.ares.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import com.cy.ares.common.error.CompressException;

/**
 * 先压缩 再 加解密
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月8日 上午11:04:03
 * @version V1.0
 */
public class CompressUtil {

    private static final int defSize = 1024;

    public static byte[] uncompress(byte[] compressContent) throws Exception {

        if (compressContent == null || compressContent.length == 0) {
            return new byte[0];
        }

        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        GzipCompressorInputStream gzippedIn = null;
        try {
            int len = compressContent.length;
            bais = new ByteArrayInputStream(compressContent);
            int msize = len * 3;
            msize = msize < defSize ? defSize : msize;
            baos = new ByteArrayOutputStream(msize);
            gzippedIn = (GzipCompressorInputStream)new CompressorStreamFactory().createCompressorInputStream(CompressorStreamFactory.GZIP, bais);
            
            byte[] b = new byte[defSize];
            while (true) {
                int lne = gzippedIn.read(b);
                if (lne <= 0) {
                    break;
                }
                baos.write(b, 0, lne);
            }
            gzippedIn.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new CompressException(e);
        } finally {
            if (gzippedIn != null) {
                gzippedIn.close();
            }
            if (baos != null) {
                baos.close();
            }
            if (bais != null) {
                bais.close();
            }
        }
    }
    
    public static byte[] compress(byte[] content) throws Exception {
        if (content == null || content.length == 0) {
            return new byte[0];
        }
        ByteArrayOutputStream baos = null;
        GzipCompressorOutputStream gzippedOut = null;
        try {
            int len = content.length;
            int msize = len / 10;
            msize = msize < defSize ? defSize : msize;
            msize = msize >= len ? len : msize;
            baos = new ByteArrayOutputStream(msize);

            gzippedOut = (GzipCompressorOutputStream)new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, baos);
            gzippedOut.write(content);
            gzippedOut.flush();
            gzippedOut.finish();
            byte[] b = baos.toByteArray();
            return b;
        } catch (Exception e) {
            throw new CompressException(e);
        } finally {
            if (gzippedOut != null) {
                gzippedOut.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }

}
