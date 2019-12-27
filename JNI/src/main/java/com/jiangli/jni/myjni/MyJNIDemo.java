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

    public static void main(String[] args) {
        System.loadLibrary("libMyJNIDemo");

        MyJNIDemo demo = new MyJNIDemo();
        demo.sayHello();
    }
}
