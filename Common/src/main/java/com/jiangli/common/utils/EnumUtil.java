package com.jiangli.common.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/20 0020 11:11
 */
public class EnumUtil {

    public static <T extends Enum> T getSpecialByOrdinal(Class<T> cls,int num) {
        if (cls.isEnum()) {
            try {
                Method values = cls.getMethod("values");
                T[] invoke = (T[])values.invoke(cls);

                int maxSize=invoke.length;
               return invoke[num%maxSize];

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
