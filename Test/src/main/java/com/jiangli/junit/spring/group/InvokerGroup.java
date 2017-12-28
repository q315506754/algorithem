package com.jiangli.junit.spring;

/**
 * @author Jiangli
 * @date 2017/12/28 9:24
 */
public @interface InvokerGroup {
    AvailableGroup value();

    String[] params();
}
