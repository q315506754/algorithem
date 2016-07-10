package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class InterruptTest {

    private static Thread thread;

    public static void main(String[] args) throws InterruptedException {
        thread = new Thread(new Runnable() {
           @Override
           public void run() {
//               try {
                   int i = 0;
                   while (true) {
                       System.out.println("i:" + ++i);

//                       Thread.sleep(100);
                       if (i > 20) {
//                           thread.interrupt();
                       }
                       System.out.println(thread.isInterrupted());

//                       if (i > 30) {
//                           break;
//                       }
                   }
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
           }
       });
        thread.start();

        Thread.sleep(200);
        thread.interrupt();
    }
}
