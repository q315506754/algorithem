package com.jiangli.sort.daily;

/**
 *黄金分割数列
 *“兔子数列”
 *
 * F（0）=0，F（1）=1，F（n）=F(n-1)+F(n-2)（n≥2，n∈N*）
 *
 *
 * @author Jiangli
 *
 *         CreatedTime  2016/7/5 0005 11:24
 */
public class FibonacciSequence {
    public static void main(String[] args) {
        System.out.println(fibonacci(0));
        System.out.println(fibonacci(1));
        System.out.println(fibonacci(2));
        System.out.println(fibonacci(3));
        System.out.println(fibonacci(4));
        System.out.println(fibonacci(5));

        int n =20;
        for (int i = 1; i <=n; i++) {
            System.out.print(fibonacci(i)+",");
        }


    }

    public static  int  fibonacci(int n) {
        switch (n) {
            case 0:return 0;
            case 1:return 1;
            default:
                return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

}
