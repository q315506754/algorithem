package com.jiangli.bytecode;

import java.lang.management.ManagementFactory;

/**
 * @author Jiangli
 * @date 2019/11/26 9:38
 */
public class ByteDtoMain  {
    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        while (true) {
            try {
                System.out.println(pid);
                Thread.sleep(5000);
                new ByteDto().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
