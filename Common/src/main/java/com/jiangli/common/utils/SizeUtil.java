package com.jiangli.common.utils;

import java.math.BigDecimal;

/**
 * @author Jiangli
 * @date 2018/5/25 14:45
 */
public class SizeUtil {
    public String[] unitStr = new String[]{"b","Kb","Mb","Gb"};

    class SizeUnit{
        String size;
        String unit;
    }

    public SizeUnit parse(long bytes) {
        SizeUnit ret = new SizeUnit();

        double c = bytes;

        for (int i = 0; i < unitStr.length; i++) {
            if (c/1024<1 || i==unitStr.length-1) {
                ret.unit = unitStr[i];
                BigDecimal bigDecimal = new BigDecimal(c).setScale(1, BigDecimal.ROUND_UP);
                BigDecimal floatPart = bigDecimal.subtract(new BigDecimal(bigDecimal.intValue()));

                if(floatPart.doubleValue() > 0){
                    ret.size = bigDecimal.toString();
                } else {
                    ret.size = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN).toString();
                }

                return ret;
            }

            c/=1024;
        }

        return ret;
    }
}
