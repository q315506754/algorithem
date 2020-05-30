package com.jiangli.sort.daily;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Jiangli
 * @date 2020/5/23 10:20
 */
public class RedEnvelopTest {

    public static  String[] splitRedEnv(double total,double min,double n) {
        if (n<0) {
            return  null;
        }
        double avg = total / n;
        if (avg< min) {
            return null;
        }

        BigDecimal prev = bd(0);
        Random random = new Random();


        //  s   |         |         e
        //    1*avg    2*avg       n*avg
        List<String> ret = new ArrayList<>();
        List<Double> nexts = new ArrayList<>();
        BigDecimal sum = new BigDecimal(total);
        for (int i = 1; i < n; i++) {
            //double next = (i+1)*avg+random.nextDouble()*avg - avg/2;
            BigDecimal next = bd( avg*(i+random.nextDouble() - 0.5) );
            //double next = i*avg+(random.nextDouble() - 0.5)*avg;

            BigDecimal value = next.subtract(prev);

            //最小值限制
            if (value.compareTo(bd(min)) < 0) {
                next = prev.add(bd(min));
                value = bd(min);
            }

            nexts.add(next.doubleValue());

            ret.add(value.toString());
            sum = sum.subtract(value);

            prev = next;
        }

        ret.add(sum.toString());

        //System.out.println(nexts);

        return ret.toArray(new String[ret.size()]);
    }
    public static BigDecimal bd(double d) {
        return new BigDecimal(d).setScale(2,BigDecimal.ROUND_FLOOR);
    }

    public static void main(String[] args) {
        for (int i = 2; i < 101; i++) {
            System.out.println(Arrays.toString(splitRedEnv(100.0,0.01,i)));
        }
        System.out.println(Arrays.toString(splitRedEnv(1,0.01,99)));
        System.out.println(Arrays.toString(splitRedEnv(1,0.01,100)));
        System.out.println(Arrays.toString(splitRedEnv(1,0.01,101)));
    }

}
