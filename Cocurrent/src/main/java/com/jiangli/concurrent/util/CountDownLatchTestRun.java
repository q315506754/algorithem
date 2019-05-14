package com.jiangli.concurrent.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Jiangli on 2016/7/8.
 */
public class CountDownLatchTestRun {
    public static void main(String[] args) throws InterruptedException {

        int count = 10;
        CountDownLatch runDownLatch = new CountDownLatch(count);
        CountDownLatch tellCoachLatch = new CountDownLatch(1);
        CountDownLatch dissolveLatch = new CountDownLatch(1);

        while (count-- > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String name = "学生"+Thread.currentThread().getId();
                        System.out.println("学生正在跑步中.."+ name);

                        Thread.sleep(new Random().nextInt(3999)+1000);

                        System.out.println("学生已跑完.."+ name);

                        runDownLatch.countDown();

                        if (runDownLatch.getCount() > 0) {
                            System.out.println("等待其他学生跑完.."+ name);
                            dissolveLatch.await();
                        } else {
                            System.out.println("最后一名学生跑完，通知教练，然后等待.."+ name);
                            tellCoachLatch.countDown();
                            dissolveLatch.await();
                        }

                        System.out.println("开开心心回家.."+ name);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("教练等待学生跑完..");
        tellCoachLatch.await();

        System.out.println("教练计算成绩..");
        Thread.sleep(1000);
        System.out.println("成绩计算完毕，通知学生解散..");
        dissolveLatch.countDown();

    }
}
