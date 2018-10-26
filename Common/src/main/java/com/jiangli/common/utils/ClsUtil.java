package com.jiangli.common.utils;

import java.lang.reflect.Field;

/**
 * @author Jiangli
 * @date 2018/9/28 9:10
 */
public class ClsUtil {
    public static Object getField(Object obj, String field) {
        try {
            Class cls;
            if (!(obj instanceof Class)) {
                cls = obj.getClass();
            } else {
                cls = ((Class) obj);
            }
            Field declaredField = cls.getDeclaredField(field);
            declaredField.setAccessible(true);
            Object o = declaredField.get(obj);
            return o;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
