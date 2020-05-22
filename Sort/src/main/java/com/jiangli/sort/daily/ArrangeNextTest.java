package com.jiangli.sort.daily;

import java.util.Set;

/**
 最后一位开始找到一个最大非升序序列S，位置为i

 在S中找到比i-1位大的最小值，互换元素，将S倒序后改为升序

 * 12345 -> 12354
 * 12354 -> 12435
 * 12453 -> 12534
 * 12534 -> 12543
 * 54321 -> 12345
 * 23455 -> 23545
 * 23545 -> 23554
 * 23554 -> 24355
 *
 */
public class ArrangeNextTest {

    //
    public static void main(String[] args) {
        //int n = 225; //3 6/2!
        //int n = 2225; //4 24/3!

        printNextArrange(12345);
        printNextArrange(23455);
        printNextArrange(54321);
        printNextArrange(12534);
    }

    public static String printNextArrange(int n) {
        int oldN = n;
        int l = String.valueOf(n).length();

        int[] arr = new int[l];
        int c = 0;
        while (n != 0) {
            arr[c++] = n%10;
            n/=10;
        }

        int i;
        if (arr.length>1) {
            for (i = 0; i < arr.length-1; i++) {
                if (arr[i+1]<arr[i]) {
                    break;
                }
            }

            //整个都为降序
            if (i == arr.length - 1) {

            } else {
                int t = arr[0];
                arr[0] = arr[i+1];
                arr[i+1] = t;
            }

        //    从i开始前的数组反转顺序
            reverse(arr,0,i);

        } else {
            i = 0;
        }

        String ret = "";
        for (int j = 0; j < arr.length; j++) {
            ret = arr[j] + ret;
        }

        System.out.println(oldN+"->"+ret);
        return ret;
    }

    private static void reverse(int[] arr, int from, int to) {
        int size = to - from + 1;
        for (int i=from, mid=from + size>>1, j=to; i<mid; i++, j--){
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
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
