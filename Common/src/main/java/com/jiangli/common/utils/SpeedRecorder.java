package com.jiangli.common.utils;

import java.math.BigDecimal;

/**
 * @author Jiangli
 * @date 2017/4/20 17:24
 */
public class SpeedRecorder {
    private long start;
    private volatile long count=0;

    public static SpeedRecorder build() {
        SpeedRecorder ret = new SpeedRecorder();
        ret.start=System.currentTimeMillis();
        return ret;
    }


    public SpeedRecorder record() {
        this.count++;
        return this;
    }

    public double averageSpeed() {
        double cost =(System.currentTimeMillis() - start)/1000.0;
        if (cost==0) {
            return 0d;
        }
        return count/cost;
    }
    public String averageSpeedString(int n) {
        return new BigDecimal(averageSpeed()).setScale(n,BigDecimal.ROUND_UP).toString();
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
