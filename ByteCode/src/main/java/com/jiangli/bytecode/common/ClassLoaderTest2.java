package com.jiangli.bytecode.common;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 14:42
 */

public class ClassLoaderTest2 {

    public static void main(String[] args) throws Exception{
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);

        ClassLoader parent = systemClassLoader;
        do {
            ClassLoader child = parent;
            parent =   child.getParent();
            System.out.println(child+"->"+parent);
        } while (parent != null);


    }

}
