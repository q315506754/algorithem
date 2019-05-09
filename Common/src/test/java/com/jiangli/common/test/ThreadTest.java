package com.jiangli.common.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
public class ThreadTest {
    private static Logger logger = LoggerFactory.getLogger(ThreadTest.class);


    public static void main(String[] args) {
        System.out.println("saaa");
        logger.debug("hahah");
        //throw new NullPointerException();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println();
                throw new NullPointerException();
            }
        });
        thread1.start();
//        thread1.

        Thread thread2 = new Thread();
        thread2.start();

    }

}
