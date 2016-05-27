package com.jiangli.bytecode.common;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 14:53
 */
public class ClassUtil {
    protected static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    public static int bytesToInt(byte[] bytes,int start,int len){
        int sum = 0;
        int end = start + len;
        for(int i =start;i<end;i++) {
            int n = (int)(bytes[i]&0xff);
            n = n << (--len) * 8;
            sum += n ;
        }

        return sum;
    }

    public static byte[] getBytes(String cls){
        ClassLoader classLoader = ClassUtil.class.getClassLoader();
        String orgCls = cls;
        cls = cls.replaceAll("\\.", "/");
        cls += ".class";
        logger.debug(orgCls+"->"+cls);
        InputStream resourceAsStream = classLoader.getResourceAsStream(cls);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            IOUtils.copyLarge(resourceAsStream,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static String[] byteCharView(byte[] bytes){
        String[] ret = new String[bytes.length ];

        int count=  0;
        for (byte aByte : bytes) {
            ret[count++] = byteToHexString(aByte);
        }

        return ret;
    }

    public static String byteToHexString(byte byteOne) {
        String s = Integer.toHexString(byteOne & 0xff).toUpperCase();
        int pad = 2 - s.length();
        while (pad-- > 0) {
            s = "0" + s;
        }
        return s;
    }

    public static String bytesToString(byte[] bytes,int start,int len){
        return new String(bytes,start,len);
    }
}
