package com.jiangli.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Jiangli
 * @date 2018/5/29 10:25
 */
public class SortTest {
    /**
     * Swaps x[a] with x[b].
     */
    private static void swap(int[] x, int a, int b) {
        int t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    public static void main(String[] args) {
        Collections.sort(new ArrayList());

        int[] dest = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int low = 0;
        int high = dest.length;
        // Insertion sort on smallest arrays
        for (int i=low; i<high; i++)
            for (int j=i; j>low && ((Comparable) dest[j-1]).compareTo(dest[j])>0; j--){
                System.out.println( dest[j-1] + " cmp "+  dest[j]);
                swap(dest, j, j-1);
            }

        System.out.println(Arrays.toString(dest));

    //    归并排序优化  mid的左右比较一次

    }

}
