package com.jiangli.concurrent.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class SleepTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start...");

        List<Object> objects = new ArrayList<Object>();
        while (true) {
            objects.add(new SleepTest());
            Thread.sleep(100);
        }
        //Thread.sleep(9999999);
    }
}
