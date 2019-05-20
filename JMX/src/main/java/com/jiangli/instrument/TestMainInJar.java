package com.jiangli.instrument;

/**
 * @author Jiangli
 * @date 2019/5/20 17:43
 */
public class TestMainInJar {
    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(3000L);

                System.out.println("asfas");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
