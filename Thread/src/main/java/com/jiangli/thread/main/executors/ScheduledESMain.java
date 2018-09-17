package com.jiangli.thread.main.executors;

import com.jiangli.common.utils.DateUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 * @date 2018/9/17 15:31
 */
public class ScheduledESMain {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        //2s一次 1次1s
        //输出间隔2s
        //ses.scheduleAtFixedRate(() -> {
        //    System.out.println("beep:"+ DateUtil.getCurrentDate_YMDHmsSS());
        //    sleep(1000);
        //}, 1, 2, TimeUnit.SECONDS);

        ////2s一次 1次1s
        //输出间隔3s
        //ses.scheduleWithFixedDelay(() -> {
        //    System.out.println("beep:"+ DateUtil.getCurrentDate_YMDHmsSS());
        //    sleep(1000);
        //}, 1, 2, TimeUnit.SECONDS);

        //2s一次
        //追尾现象  不会开新线程,老的一直阻塞执行
        //输出间隔6s
        ses.scheduleAtFixedRate(() -> {
            System.out.println("beep:"+ DateUtil.getCurrentDate_YMDHmsSS());
            sleep(6000);
        }, 1, 2, TimeUnit.SECONDS);

        System.out.println("over..");
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
