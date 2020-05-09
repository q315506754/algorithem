package com.jiangli.common.utils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/18 0018 11:33
 */
public class TimeUtil {
    public static final long ONEDAYTIME = TimeUnit.DAYS.toMillis(1);
    public static final long ONEHOURTIME = TimeUnit.HOURS.toMillis(1);
    public static final long ONEMINUTETIME = TimeUnit.MINUTES.toMillis(1);
    public static String getCNString(long ts) {
        return getCNString((double)ts);
    }
    public static String getSpeedCNString(double ts) {
        return new BigDecimal(ts/1000).setScale(4,BigDecimal.ROUND_UP).toString();
    }

    public static String pad(int t,String pad,int length) {
        String ret = ""+t;

        if (pad == null ||pad.length() == 0) {
            pad = "0";
        }

        while (ret.length() < length) {
            ret = pad + ret;
        }

        return ret;
    }

    public static String getClockString(int t) {
        int sec = t %60;
        t/=60;

        int min = t %60;
        t/=60;


        StringBuilder sb = new StringBuilder();
        sb.append(pad(t,"0",2));
        sb.append(":");
        sb.append(pad(min,"0",2));
        sb.append(":");
        sb.append(pad(sec,"0",2));
        return sb.toString();
    }

    public static String getCNString(double ts) {
        int t = (int)(ts/1000);
        int sec = t %60;
        t/=60;
        int min = t %60;
        t/=60;
        int hour = t %24;
        t/=24;
        StringBuilder sb = new StringBuilder();
        if (t > 0 ) {
            sb.append(t+"天");
        }
        if (hour > 0 ) {
            sb.append(hour+"时");
        }
        if (min > 0 ) {
            sb.append(min+"分");
        }
        if (sec > 0 ) {
            sb.append(sec+"秒");
        }

        if (sb.length() == 0) {
            sb.append("0秒");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getClockString(0));
        System.out.println(getClockString(12));
        System.out.println(getClockString(123));
        System.out.println(getClockString(1234));
        System.out.println(getClockString(12345));
        System.out.println(getClockString(123456));

    }
}
