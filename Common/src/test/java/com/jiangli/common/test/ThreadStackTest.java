package com.jiangli.common.test;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2017/5/5 10:12
 */
public class ThreadStackTest {

    public static void main(String[] args) throws Exception {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println(Arrays.toString(stackTrace));
        StackTraceElement invoker = stackTrace[1];
        System.out.println(BeanUtils.describe(invoker));
    }

}
