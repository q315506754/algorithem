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

}
