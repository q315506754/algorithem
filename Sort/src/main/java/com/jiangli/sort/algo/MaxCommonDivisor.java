package com.jiangli.sort.algo;

/**
 * @author Jiangli
 * @date 2018/5/29 10:26
 */
public class MaxCommonDivisor {

    public static void main(String[] args) {
        System.out.println(iter(25,10));
        System.out.println(iter(1000,1001));

        System.out.println(iter2(25,10));
        System.out.println(iter2(1000,1001));

        System.out.println(recur(25,10));
        System.out.println(recur(1000,1001));

        System.out.println(recur2(25,10));
        System.out.println(recur2(1000,1001));

        System.out.println(combine(25,10));
        System.out.println(combine(1000,1001));

        System.out.println(isOdd(23));
        System.out.println(isOdd(24));
        System.out.println(isOdd(1));
        System.out.println(isOdd(0));
        System.out.println(isOdd(-1));//true
        System.out.println(isOdd(-2));//false

    }

    public static boolean isOdd(int a) {
        return (a&1) == 1;
    }


    /**
     * 当a和b均为偶数，gcb(a,b) = 2*gcb(a/2, b/2) = 2*gcb(a>>1, b>>1)

     当a为偶数，b为奇数，gcb(a,b) = gcb(a/2, b) = gcb(a>>1, b)

     当a为奇数，b为偶数，gcb(a,b) = gcb(a, b/2) = gcb(a, b>>1)

     当a和b均为奇数，利用更相减损术运算一次，gcb(a,b) = gcb(b, a-b)， 此时a-b必然是偶数，又可以继续进行移位运算。
     */
    //combine 辗转相除法 + 更相减损术
    public static int combine(int a,int b) {
        if (a == b ) {
            return a;
        }
        if (a < b ) {
            //永远 a > b
            return combine(b,a);
        }

        if (isOdd(a) && isOdd(b)) {
            return combine(b,a-b);
        } else if (!isOdd(a) && isOdd(b)) {
            return combine(a>>1,b);
        } else if (isOdd(a) && !isOdd(b)) {
            return combine(a,b>>1);
        } else {
            return combine(a>>1,b>>1);
        }
    }

    /**
     * 辗转相除法， 又名欧几里得算法（Euclidean algorithm），目的是求出两个正整数的最大公约数。
     * 它是已知最古老的算法， 其可追溯至公元前300年前。

     这条算法基于一个定理：两个正整数a和b（a>b），它们的最大公约数等于a除以b的余数c和b之间的最大公约数。
     比如10和25，25除以10商2余5，那么10和25的最大公约数，等同于10和5的最大公约数。

     */
    //辗转相除法
    //时间复杂度不太好计算，可以近似为O(log(max(a, b)))，但是取模运算性能较差。
    public static int recur(int a,int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        if (max % min == 0) {
            return min;
        }

        return recur(min,max % min);
    }


    /**
     * 更相减损术， 出自于中国古代的《九章算术》，也是一种求最大公约数的算法。

     他的原理更加简单：两个正整数a和b（a>b），它们的最大公约数等于a-b的差值c和较小数b的最大公约数。
     比如10和25，25减去10的差是15,那么10和25的最大公约数，等同于10和15的最大公约数。
     */
    //更相减损术  减法效率高，但不稳定，比如1000和1计算，需递归999次
    //避免了取模运算，但是算法性能不稳定，最坏时间复杂度为O(max(a, b)))
    public static int recur2(int a,int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        if (max == min) {
            return min;
        }

        return recur2(min,max - min);
    }

    //暴力枚举法 时间复杂度是O(min(a, b)))
    public static int iter(int a,int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        int times = 0;
        for (int i = min; i >= 1; i--) {
            times++;
            if (min % i == 0 && max % i == 0 ) {
                System.out.println("times:"+times);
                return  i;
            }
        }
        return 1;
    }
    public static int iter2(int a,int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        if (max % min == 0) {
            return min;
        }

        int times = 0;
        int ret = 1;
        for (int i = 2; i <= min/2; i++) {
            times++;

            if (min % i == 0 && max % i == 0 ) {
                ret = i ;
            }
        }

        System.out.println("times 2:"+times);
        return ret;
    }

}
