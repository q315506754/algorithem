package com.jiangli.sort.daily;

import java.util.HashSet;
import java.util.Set;

/**
 * 如 abbcccdde 共有 9! / (1! * 2! * 3! * 2! * 1!) = 15120 种不同排列。
 * @author Jiangli
 * @date 2020/5/14 14:00
 */
public class ArrangeTest {

    //234 243 324 342 423 432
    public static void main(String[] args) {
        //int n = 225; //3 6/2!
        //int n = 2225; //4 24/3!

        printAllArrange(2345);
        printAllArrange(2225);
        printAllArrange(12354);
    }

    public static void printAllArrange(int n) {
        int l = String.valueOf(n).length();

        int[] arr = new int[l];
        int c = 0;
        while (n != 0) {
            arr[c++] = n%10;
            n/=10;
        }

        int size = printStringArrange("",arr,new HashSet<>());
        System.out.println("total:"+size);
    }

    public static int printStringArrange(String prefix, int[] arr, Set<String> noDup) {
        if (arr.length == 1) {
            String x = prefix + arr[0];
            if (!noDup.contains(x)) {
                noDup.add(x);
                System.out.println(x);
                return 1;
            } else {
                return 0;
            }
        }

        int sum = 0;

        for (int i = arr.length-1; i >=0; i--) {
            int[] removeiArr = new int[arr.length-1];
            System.arraycopy(arr,0,removeiArr,0,i);
            System.arraycopy(arr,i+1,removeiArr,i,arr.length-i-1);

            sum+=printStringArrange(prefix+ arr[i],removeiArr,noDup);
        }
        return sum;
    }

}
