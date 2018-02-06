package com.jiangli.common.lib;

import java.util.Collection;

/**
 * value
 *
 * @author Jiangli
 * @date 2018/2/6 10:05
 */
public class Val {
    public static String str(Object str) {
        if (str == null) {
            return null;
        }
        return str.toString();
    }

    public static Long getLong(String courseId) {
        if (courseId == null) {
            return null;
        }
        try {
            return Long.parseLong(courseId);
        } catch (Exception e) {
        }
        return null;
    }
    public static Integer getInteger(String courseId) {
        if (courseId == null) {
            return null;
        }
        try {
            return Integer.parseInt(courseId);
        } catch (Exception e) {
        }
        return null;
    }

    public static String nullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    public static boolean isNotEmpty(Collection str) {
        return !isEmpty(str);
    }
    public static boolean isEmpty(String str) {
        return str == null || str.toString().length()==0;
    }
    public static boolean isEmpty(Collection str) {
        return str == null || str.size() == 0;
    }
}
