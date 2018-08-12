package com.jiangli.thread.main;

/**
 * @author Jiangli
 * @date 2018/5/22 13:56
 */
public class ConditionSynchronizedMain {
    public static void main(String[] args) {
        Object condition357 = new Object();
        Object conditionPrint = new Object();

        Integer[] ii = new Integer[1];

        //producer
        new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (i % 13579 == 0) {
                    synchronized (conditionPrint) {
                        try {
                            while (ii[0] != null)
                                conditionPrint.wait();

                            System.out.println("produce:" + i);
                            ii[0] = i;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        synchronized (condition357) {
                            System.out.println("xxx");
                            condition357.notify();
                        }
                    }


                }
            }
        }).start();

        //consumer
        new Thread(() -> {
            while (true) {
                try {
                    synchronized (condition357) {
                        while (ii[0] == null)
                            condition357.wait();

                        System.out.println("consume():" + ii[0]);
                        ii[0] = null;
                        Thread.sleep(1000);


                        synchronized (conditionPrint) {
                            System.out.println("yyy");//死锁时锁定该行
                            conditionPrint.notify();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }).start();
    }

}
