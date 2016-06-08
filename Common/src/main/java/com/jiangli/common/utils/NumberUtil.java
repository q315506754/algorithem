package com.jiangli.common.utils;

import java.math.BigDecimal;

/**
 * Created by Jiangli on 2016/6/4.
 */
public class NumberUtil {
    public static String getDoubleString(double left) {
        return getDoubleString(left,2);
    }

    public static String getDoubleString(double left,int length) {
        return new BigDecimal(left).setScale(length,BigDecimal.ROUND_DOWN).toString();
    }

    public static int parseInt(String intStr,int defaultInt) {
        try {
           return  Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
        }
        return defaultInt;
    }

    public static int parseInt(String intStr) {
        return parseInt(intStr,0);
    }

}
