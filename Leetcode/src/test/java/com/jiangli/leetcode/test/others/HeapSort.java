package com.jiangli.leetcode.test.others;

import com.jiangli.leetcode.test.practice.PracticeBase;
import org.junit.Test;

/**
 *

 */
public class HeapSort extends PracticeBase  {

    //插入
    public void heapfyUp(int[] arr,int length,int n) {


    }

    //删除堆顶
    //已有数组建堆 对非叶子节点调用即可
    public void heapfyDown(int[] arr,int length,int n) {
        int minIdx = n;
        int idx = 2*n+1;
        if (idx < length && arr[idx] < arr[n]) {
            minIdx = idx;
        }

        idx = 2*n+2;
        if (idx < length && arr[idx] < arr[minIdx]) {
            minIdx = idx;
        }

        if (minIdx != n) {
            swap(arr,minIdx,n);

            heapfyDown(arr,length,minIdx);
        }

    }

    private void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    public int[] topK(int[] arr,int k) {
        if (arr.length <= k) {
            return arr;
        }

        //生成最小堆堆化
        for (int i = 0; i < k; i++) {
            heapfyDown(arr,k,i);
        }

        //堆顶比较
        for (int i = k; i < arr.length; i++) {
        //
            if (arr[i] > arr[0]) {
                swap(arr,i,0);

                heapfyDown(arr,k,0);
            }

        }

        int[] ret = new int[k];
        System.arraycopy(arr,0,ret,0,k);
        return ret;
    }


    @Test
    public void test_() {
        int[] arr = a(7, 3, 5);
        heapfyDown(arr,3,0);
        ae(arr,a(3,7,5));

        ae(topK(a(5,3,1,2,7,9,6),3),a(9,7,6));

    }


}
