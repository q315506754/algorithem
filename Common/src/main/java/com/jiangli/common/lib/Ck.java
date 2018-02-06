package com.jiangli.common.lib;

/**
 *
 * check
 *
 * @author Jiangli
 * @date 2018/2/6 10:00
 */
public class Ck {
    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message+" is null!");
        }
    }

    public static void assertStringSize(String str,int size, String message) {
        if (str != null && str.length() > size) {
            throw new IllegalArgumentException(String.format(message,size));
        }
    }
}
