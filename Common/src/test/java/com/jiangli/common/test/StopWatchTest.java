package com.jiangli.common.test;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.Random;

/**
 * @author Jiangli
 * @date 2020/9/8 10:11
 */
public class StopWatchTest {
    @Test
    public void test_() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("调用风控系统接口");
        sleep();
        // 调用风控系统接口, http调用方式
        stopWatch.stop();

        stopWatch.start("获取拼团活动信息"); //
        // 获取拼团活动基本信息. 查询缓存
        sleep();
        stopWatch.stop();

        stopWatch.start("获取用户基本信息");
        // 获取用户基本信息。http调用用户服务
        sleep();
        stopWatch.stop();

        stopWatch.start("判断是否是新用户");
        // 判断是否是新用户。查询订单数据库
        sleep();
        stopWatch.stop();

        stopWatch.start("生成订单并入库");
        sleep();
        // 生成订单并入库
        stopWatch.stop();

        // 打印task报告
        String s = stopWatch.prettyPrint();
        System.out.println(s);

    }

    private void sleep() {
        try {
            Thread.sleep(new Random().nextInt(800)+100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
