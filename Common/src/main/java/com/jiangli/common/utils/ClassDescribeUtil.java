package com.jiangli.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Jiangli on 2016/6/1.
 */
public class ClassDescribeUtil {
    public static String delimeter = "\r\n";
    public static String delimeter_bet_kv = " = ";

    public static String describeStaticFields(Class cls){
        Field[] declaredFields = cls.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        String simpleName = cls.getSimpleName();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            try {
                Object o = declaredField.get(cls);
                sb.append(simpleName+"."+declaredField.getName()+delimeter_bet_kv+ String.valueOf(o));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (i != declaredFields.length - 1) {
                sb.append(delimeter);
            }
        }
        return sb.toString();

    }
}
