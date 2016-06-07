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
}
