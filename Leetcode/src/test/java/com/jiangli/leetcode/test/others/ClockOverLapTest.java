package com.jiangli.leetcode.test.others;

import com.jiangli.common.utils.TimeUtil;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * 环长测试
 * 时钟重合
 *
 * @author Jiangli
 * @date 2020/5/9 9:47
 */
public class ClockOverLapTest {
    //角速度 d/s
    public static final double HOUR_SPEED = 360/(12*3600);// 1/120 d/s
    public static final double MIN_SPEED = 360/(3600);//0.1 d/s
    public static final double SEC_SPEED = 360/(60);//6 d/s

    //计算24小时内 重合时的秒数
    public List<Integer> calcOverLap(double wf, double ws) {
        List<Integer> ret = new LinkedList<>();

        int totalLoops = (int) (24 *3600 * wf /360);

        //隐藏条件
        // 总角度差相等，速度差计算，圈数
        //  wf*t - ws * t = 360*k - [ws/wf*k]*360

        for (int i = 0; i < totalLoops; i++) {
            double slowLoops = ws / wf * i;

            //if ( (int) slowLoops == slowLoops) {
            double t =  (i - (slowLoops)) * 360 / (wf - ws);
            //ret.add(t);
            //}
        }

        return ret;
    }

    @Test
    public void test_() {
        List<Integer> integers = calcOverLap(SEC_SPEED, MIN_SPEED);
        //List<Integer> integers = calcOverLap(MIN_SPEED, HOUR_SPEED);
        //List<Integer> integers = calcOverLap(SEC_SPEED, HOUR_SPEED);

        int maxLength = (integers.size() + "").length();

        for (int i = 0; i < integers.size(); i++) {
            System.out.println(TimeUtil.pad(i + 1, " ", maxLength) + " " + TimeUtil.getClockString(integers.get(i)));
        }

        p();

    }

    private void p() {
        System.out.println("------------");
    }

}
