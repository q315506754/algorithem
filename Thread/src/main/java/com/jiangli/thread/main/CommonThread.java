package com.jiangli.thread.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiangli
 * @date 2017/7/11 16:45
 */
public class CommonThread {
    private static Logger logger = LoggerFactory.getLogger(CommonThread.class);


    public static void main(String[] args) {
        System.out.println("saaa");
        logger.debug("hahah");


        CommonThread s = new CommonThread();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (s) {
                    try {
                        Thread.sleep(999999999);
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
