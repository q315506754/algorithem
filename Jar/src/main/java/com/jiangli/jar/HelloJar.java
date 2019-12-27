package com.jiangli.jar;

/**
 * @author Jiangli
 * @date 2019/5/20 13:48
 */
public class HelloJar {
    public void sayHello() {
        String x = HelloUtil.func1();
        System.out.println(x+" said hello");
    }

    public static void main(String[] args) {
        HelloJar helloJar = new HelloJar();
        helloJar.sayHello();
    }
}
