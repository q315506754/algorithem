package com.jiangli.instrument.transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class AbsTransformer implements ClassFileTransformer {

    public static final String classNumberReturns2 = "/home/conquer/Desktop/aaaa/TransClass.class";

    public static byte[] getBytesFromFile(String fileName) {
        try {
            System.out.println(System.getProperty("user.home"));
            // precondition
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            is.close();
            return bytes;
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!"
                    + e.getClass().getName());
            return null;
        }
    }

    public byte[] transform(ClassLoader l, String className, Class<?> c,
                            ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {
        //注意 名称格式为 sun/net/spi/DefaultProxySelector$NonProxyInfo
        System.out.println(className);
        if (!className.contains("TestBean")) {
            System.out.println("no:"+className);
            return null;
        } else {
            System.out.println("yes:"+className);
        }
        return getBytesFromFile(classNumberReturns2);

    }
}