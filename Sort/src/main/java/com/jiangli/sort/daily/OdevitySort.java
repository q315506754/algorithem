package com.jiangli.sort.daily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 对一个数组排序，达到目的——奇数在前，偶数在后
 * 要求——空间复杂度o（1），时间复杂度o（n）
 *
 * @author Jiangli
 *
 *         CreatedTime  2016/7/5 0005 10:33
 */
public class OdevitySort {

    public static void main(String[] args) {
//        int n = 10;
        int n = 11;
        int[] param = new int[n];
        while (n-- > 0) {
            param[n] = n;
        }

        List list = new ArrayList<>();
        for (int i : param) {
            list.add(i);
        }
        Collections.shuffle(list);

        System.out.println("org:"+Arrays.toString(param));

        for (int i = 0; i < list.size(); i++) {
            param[i] = (int)list.get(i);
        }
        System.out.println("shuffled:" + Arrays.toString(param));

        int[] ints = odevitySort(param);
        System.out.println("final:" + Arrays.toString(ints));

    }

    public static int[] odevitySort(final int[] arr) {
        int[] ret = new int[arr.length];
        System.arraycopy(arr,0,ret,0,arr.length);

        int lowIdx = 0;
        int highIdx = ret.length-1;
        while (lowIdx < highIdx) {
            while (ret[lowIdx]%2!=0 && (lowIdx < highIdx)){
                lowIdx++;
            }
            int t = ret[lowIdx];
            while (ret[highIdx]%2==0 && (lowIdx < highIdx)){
                highIdx--;
            }
            if (lowIdx < highIdx) {
                ret[lowIdx] = ret[highIdx];
                ret[highIdx] = t;
            }
        }

        return ret;
    }

}
