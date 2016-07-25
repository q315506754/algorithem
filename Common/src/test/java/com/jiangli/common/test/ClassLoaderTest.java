package com.jiangli.common.test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/17 0017 10:33
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);

        ClassLoader parent = contextClassLoader.getParent();
        System.out.println(parent);

        parent = parent.getParent();
        System.out.println(parent);
    }

}
