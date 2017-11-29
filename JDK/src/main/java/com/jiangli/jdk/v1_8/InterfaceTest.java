package com.jiangli.jdk.v1_8;

/**
 * @author Jiangli
 * @date 2017/11/24 9:22
 */
public class InterfaceTest {

    interface A{
        default void print() {
            print("hello");
        }
        void print(String hello);
    }


    public static void main(String[] args) {
        A a = hello -> System.out.println(hello);
        a.print();
    }
}
