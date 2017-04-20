package com.jiangli.common.utils;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * @author Jiangli
 * @date 2017/4/20 17:24
 */
public class SpeedRecorder {
    private long start;
    private Long interval;
    private Long intervalNext;
    private int intervalTimes=0;
    private Consumer<Integer> function;
    private volatile long count=0;

    public SpeedRecorder() {
        resetTime();
    }

    private  void resetTime() {
        this.start = System.currentTimeMillis();
    }

    public static SpeedRecorder build() {
        SpeedRecorder ret = new SpeedRecorder();
        ret.resetTime();
        return ret;
    }


    public SpeedRecorder record() {
        this.count++;

        if (interval != null && this.function != null) {
            if (intervalNext ==null || System.currentTimeMillis()>intervalNext) {
                function.accept(++intervalTimes);
                intervalNext = System.currentTimeMillis()+interval;
            }

        }

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
    public String estimateRestTime(long total) {
        long rest = total - count;
        double v = averageSpeed();
        double seconds = rest/v;
        String cnString = TimeUtil.getCNString(seconds * 1000);
        return cnString;
    }
    public void setInterval(Consumer<Integer> function, long ts) {
        this.interval=ts;
        this.function=function;
    }
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
