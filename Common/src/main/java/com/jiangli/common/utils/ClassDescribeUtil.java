package com.jiangli.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, Object> describeFields(Class cls) {
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] declaredFields = cls.getDeclaredFields();
            Object o = cls.newInstance();
            for (Field declaredField : declaredFields) {
    //            Class<?> type = declaredField.getType();

                declaredField.setAccessible(true);

                Object val = declaredField.get(o);
                if (val != null ) {
                    boolean valid=true;

                    if (val instanceof Collection) {
                        valid=!CollectionUtil.isEmpty((Collection)val);
                    }

                    if (valid) {
                        map.put(declaredField.getName(),val);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
