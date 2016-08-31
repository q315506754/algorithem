package com.jiangli.common.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
public class ThreadTest2 {
    private static Logger logger = LoggerFactory.getLogger(ThreadTest2.class);


    public static void main(String[] args) {
        ThreadTest2 s = new ThreadTest2();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (s) {
                    try {
                        Thread.sleep(9999999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        synchronized (s){
//            try {
//                s.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        try {
            Thread.sleep(3000);
            synchronized (s) {
              System.out.println("good");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
