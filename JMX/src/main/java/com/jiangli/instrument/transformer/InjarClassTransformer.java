package com.jiangli.instrument.transformer;

import org.apache.commons.io.IOUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.security.ProtectionDomain;

public class InjarClassTransformer implements ClassFileTransformer {

    public static final String clsName = "com.jiangli.instrument.TestBean";

    public static byte[] getBytesFromFile(String fileName) {
        try {
            String realFile = pathClsName(fileName);
            realFile += ".class";
            URL url = ClassLoader.getSystemResource(realFile);
            System.out.println("url:"+url);
            byte[] bytes1 = IOUtils.toByteArray(url);
            return bytes1;
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                    + e.getClass().getName());
            return null;
        }
    }

    private static String pathClsName(String fileName) {
        return fileName.replaceAll("\\.", "/");
    }

    public byte[] transform(ClassLoader l, String className, Class<?> c,
                            ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {
        //注意 名称格式为 sun/net/spi/DefaultProxySelector$NonProxyInfo
        System.out.println(className);
        if (!className.equals(pathClsName(clsName))) {
            System.out.println("no:"+className);
            return null;
        } else {
            System.out.println("yes:"+className);
        }
        return getBytesFromFile(clsName);

    }
}