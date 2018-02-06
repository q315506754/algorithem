package com.jiangli.common.test;

import com.jiangli.common.utils.RandomUtil;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by Jiangli on 2016/6/7.
 */
public class RandomTest {
    @Test
    public void func() {
        int n = 10000;
        while (n-- > 0) {
            int randomNum = RandomUtil.getRandomNum(30, 100);
//            System.out.println(randomNum);

            if (randomNum <= 30) {
                System.out.println(randomNum);
            }
            if (randomNum >= 100) {
                System.out.println(randomNum);
            }
        }

        System.out.println(RandomUtil.random.nextInt());
    }

    @Test
    public void test_() {
        long times = 100000000;
        double min = 1d;
        double max = 0d;
        while (times-- > 0) {
            double v = RandomUtil.random.nextDouble();

            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }
        //System.out.println(RandomUtil.random.nextDouble());
        System.out.println(min);
        System.out.println(max);
    }

    @Test
    public void test_22() {
        //Function<Double,Boolean> producer = aDouble -> {
        //    return RandomUtil.random.nextDouble() + aDouble > 1;
        //};
        Function<Double,Boolean> producer = aDouble -> {
            double v = RandomUtil.random.nextDouble();
            return v <= aDouble;
        };

        //double chance = 0.13d;
        //double chance = 1d;
        double chance = 0d;
        long times = 100000000;
        long t = 0;
        long f = 0;
        while (times-- > 0) {
            Boolean apply = producer.apply(chance);

            if (apply.booleanValue()) {
                t++;
            }
            else {
                f++;
            }
        }
        System.out.println("t:"+t);
        System.out.println("f:"+f);
    }

}
