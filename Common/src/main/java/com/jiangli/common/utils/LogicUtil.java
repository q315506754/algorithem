package com.jiangli.common.utils;

/**
 * Created by Jiangli on 2016/6/7.
 */
public class LogicUtil {
    public static boolean checkStartEnd(int start,int end){
        if (start > 0 && end > 0 && end >= start) {
            return true;
        }
        return false;
    }
    public static boolean anyNull(Object... args){
        if (args!=null) {
            for (Object arg : args) {
                if (arg == null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean anyTrue(boolean... args){
        if (args!=null) {
            for (boolean arg : args) {
                if (arg) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean anyFalse(boolean... args){
        if (args!=null) {
            for (boolean arg : args) {
                if (!arg) {
                    return true;
                }
            }
        }
        return false;
    }
}
