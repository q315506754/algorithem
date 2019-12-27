package com.jiangli.thread.main.executors.collection.block;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Jiangli
 * @date 2018/9/18 15:36
 */
public class LinkedBlockingQueueMain {
    public static void main(String[] args) {
        //不设置的话则无界Integer.MAX_VALUE  链表
        LinkedBlockingQueue qu = new LinkedBlockingQueue(1);
        System.out.println("start");
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Object take = qu.take();
                    System.out.println("take:"+take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            qu.put("111");
            System.out.println("put 111");

            //blocked
            qu.put("222");
            System.out.println("put 222");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
