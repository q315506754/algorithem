package com.jiangli.thread.main;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Jiangli
 * @date 2019/8/15 9:28
 */
public class ExchangeTest {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(()->{
            try {
                String exchange = exchanger.exchange("先锋线程发来捷报");
                System.out.println("先锋线程收到了："+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String exchange = exchanger.exchange("步兵已经跟上");
                System.out.println("步兵线程收到了："+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String exchange = exchanger.exchange("骑兵已发起冲锋",5000, TimeUnit.MILLISECONDS);
                System.out.println("骑兵线程收到了："+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
