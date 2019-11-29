package com.jiangli.bytecode.instrument;

import java.lang.management.ManagementFactory;

/**
 * @author Jiangli
 * @date 2019/11/28 17:42
 */
public class JmxPractice {
    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);
    }

}
