package com.jiangli.common.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2016/11/15 8:59
 */
public class SpeedRecorderTest {
    private long startTs;

    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        System.out.println("----------before-----------");
        System.out.println();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        System.out.println("----------after-----------:cost:"+cost+" ms");
        System.out.println();
    }

    @Test
    public void test_asdas() {
        SpeedRecorder recorder = new SpeedRecorder();
        while (true) {
            recorder.record();
            System.out.println(recorder.averageSpeedString(2));
            System.out.println(recorder.getCount());
            try {
                Thread.sleep(RandomUtil.getRandomNum(1,30));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}