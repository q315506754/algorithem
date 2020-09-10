package com.jiangli.common.utils;

/**
 * @author Jiangli
 * @date 2020/6/10 16:37
 */
public class CompareUtil {

    public static final boolean compare(Object a ,Object b) {
        if (a != null && a != null) {
            a.equals(b);
        }
        return a == b;
    }

}
