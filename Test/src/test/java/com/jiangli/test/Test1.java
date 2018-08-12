package com.jiangli.test;

import com.jiangli.junit.spring.RepeatFixedDuration;
import com.jiangli.junit.spring.StatisticsJunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

/**
 * @author Jiangli
 * @date 2018/5/30 17:01
 */
@RunWith(StatisticsJunitRunner.class)
public class Test1 {

    @RepeatFixedDuration
    @Test
    public void test_divd() {
        int x = 432423434%(new Random().nextInt(10000)+1);
    }

    @RepeatFixedDuration
    @Test
    public void test_and() {
        int x = 432423434&(new Random().nextInt(10000)+1);
    }


}
