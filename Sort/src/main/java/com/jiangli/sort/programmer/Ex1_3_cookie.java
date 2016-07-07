package com.jiangli.sort.programmer;

import com.jiangli.common.utils.ArrayUtil;

import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/4 0004 10:14
 */
public class Ex1_3_cookie {
    public static void main(String[] args) {
        int[] arr = new int[]{5, 2,7, 1,6,8, 3, 4};
        int i = arr.length;
        int count = 0;
        System.out.println("init:" + Arrays.toString(arr));
        while (--i > 0) {
            int calLen = i + 1;
            int[] copiedArr = cloneArr(arr, calLen);
//            System.out.println("copiedArr:" + Arrays.toString(copiedArr));
            int maxIdx = ArrayUtil.maxIdx(copiedArr);
            System.out.println("maxIdx:" + maxIdx);

            if (maxIdx != i) {
                if (maxIdx > 0) {
                    int[] rotate1 = cloneArr(copiedArr, maxIdx+1);
                    reverse(rotate1);
                    copyInto(rotate1, copiedArr);
                    copyInto(copiedArr, arr);
                    System.out.println("rotate1:" + Arrays.toString(arr));
                    count++;
                }

                reverse(copiedArr);
                copyInto(copiedArr, arr);
                count++;
            }
            System.out.println("rotate2:" + Arrays.toString(arr));
        }

        System.out.println("finish:" + Arrays.toString(arr));
        System.out.println("count:" + count);
    }

    public static void copyInto(int[] src, int[] dest) {
//        System.arraycopy(src,0,dest,0,dest.length);
        int srcLen,destLen;
        int minLen = (srcLen = src.length) < (destLen = dest.length) ? srcLen : destLen;
        for (int i = 0; i < minLen; i++) {
            dest[i] = src[i];
        }
    }


    public static int[] cloneArr(int[] arr, int calLen) {
        int[] copiedArr = new int[calLen];
//        System.arraycopy(arr, 0, copiedArr,0 , copiedArr.length);
        copyInto(arr, copiedArr);
        return copiedArr;
    }

    public static void reverse(int[] arr) {
        for (int i = 0; i <= (arr.length - 1) / 2; i++) {
            int t = arr[i];
            arr[i] = arr[arr.length -1  - i];
            arr[arr.length-1 - i] = t;
        }
    }


}
