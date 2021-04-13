package com.jiangli.jni.myjni;

/**
 * @author Jiangli
 * @date 2019/9/11 10:19
 *
 *
 * cd C:\myprojects\algorithem\JNI\src\main\java
 *
 * javah com.jiangli.jni.myjni.MyJNIDemo
 * 执行路径下多了个com_jiangli_jni_myjni_MyJNIDemo.h
 *
 *
 */
public class MyJNIDemo {
    public native void sayHello();

    public native String echo(String str);

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("sun.boot.library.path"));
        System.loadLibrary("libMyJNIDemo");

        MyJNIDemo demo = new MyJNIDemo();
        System.out.println(demo);
        System.out.println(demo.echo("abc"));
        demo.sayHello();
    }
}
