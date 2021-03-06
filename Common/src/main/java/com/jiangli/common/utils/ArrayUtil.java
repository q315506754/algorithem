package com.jiangli.common.utils;

import java.util.Arrays;

/**
 * Created by Jiangli on 2016/6/8.
 */
public class ArrayUtil {
    public static void init(int[] arr,int val){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = val;
        }
    }

    public static <T> void init(T[] arr,Class<T> cls){
        for (int i = 0; i < arr.length; i++) {
            try {
                arr[i] = cls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int maxIdx(int[] arr) {
        int maxIdx = 0;
        int max = arr[maxIdx];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]> max) {
                maxIdx = i;
                max = arr[i];
            }
        }
        return maxIdx;
    }

    public static byte[] getRange(byte[] unicodes, int startIdx, int length) {
        byte[] record = new byte[length];
        int n = 0;
        if (unicodes !=null ) {
            for (int i = startIdx; i < unicodes.length &&  i < startIdx + length; i++) {
                record[n++]=unicodes[i];
            }
        }
        byte[] ret = new byte[n];
        System.arraycopy(record,0,ret,0,n);
        return ret;
    }
    public static void print(byte[] unicodes) {
        print(unicodes,0,unicodes.length);
    }
    public static void print(byte[] unicodes, int startIdx, int length) {
        byte[] range = getRange(unicodes, startIdx, length);

        StringBuilder sb = new StringBuilder();
        if (unicodes !=null ) {
            sb.append("[");
            for (int i = startIdx; i < range.length ; i++) {
                sb.append(range[i]);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
        }

        System.out.println(sb.toString());
    }

    public static String toString(int[][] ints1){
        String[] t = new String[ints1.length];
        for (int i = 0; i < ints1.length; i++) {
            t[i] = Arrays.toString(ints1[i]);
        }
        return Arrays.toString(t);
    }

    public static int[] newArray(int length,int start){
        int[] ret = new int[length];
        while (length-->0) {
            ret[length]=length+start;
        }
        return ret;
    }

    public static int[] substractBiSearch(int[] ints1,int[]  ints2){
        int[] ret = new int[ints1.length-ints2.length];
        int n=0;
        for (int i = 0; i < ints1.length; i++) {
            if (Arrays.binarySearch(ints2,ints1[i]) < 0) {
                ret[n++]=ints1[i];
            }
        }
        return ret;
    }
}
