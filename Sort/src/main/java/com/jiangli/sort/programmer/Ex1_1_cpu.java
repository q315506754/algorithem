package com.jiangli.sort.programmer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/4 0004 10:14
 */
public class Ex1_1_cpu {
    public static void main(String[] args) {
//        deadLoop();

        for (; ; ){
            for (int i = 0; i < 9600000; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void deadLoop() {
        while (true) {

        }
    }

}
