package com.jiangli.sort.daily;

/**
 * @author Jiangli
 * @date 2020/5/19 10:39
 */
public class XorTest {
    public static void main(String[] args) {

        System.out.println(3^3);

        int n = 123;
        int sum = 0;
        while (n-->0) {
            sum^=n;
        }
        System.out.println(sum);
        sum = 0;
        while (n-->0) {
            sum^=n;
        }
        System.out.println(sum);

        int a = 3333;
        int b = 4444;
        int x = a ^ b;
        System.out.println(x);
        System.out.println(a ^ x);
        System.out.println(b ^ x);
    }

}
