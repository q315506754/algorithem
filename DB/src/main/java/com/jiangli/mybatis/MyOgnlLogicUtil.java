package com.jiangli.mybatis;

/**
 * @author Jiangli
 * @date 2020/1/13 16:44
 */
public class MyOgnlLogicUtil {
    public static boolean isEmpty(Number obj) {
        if (obj == null) {
            return true;
        }
        if (obj.intValue() < 0) {
            return true;
        }
        return false;
    }

}
