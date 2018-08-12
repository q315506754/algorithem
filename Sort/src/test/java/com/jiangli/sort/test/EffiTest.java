package com.jiangli.sort.test;

import com.jiangli.junit.spring.RepeatFixedDuration;
import com.jiangli.junit.spring.StatisticsJunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jiangli
 * @date 2018/5/29 10:44
 */
@RunWith(StatisticsJunitRunner.class)
public class EffiTest {

    @RepeatFixedDuration
    @Test
    public void test_1() {
        int x = Integer.MAX_VALUE % 423423423;
    }

    @RepeatFixedDuration
    @Test
    public void test_2() {
        int x = Integer.MAX_VALUE / 423423423;
    }

    @RepeatFixedDuration
    @Test
    public void test_3() {
        int x = Integer.MAX_VALUE >> 3;
    }



}
