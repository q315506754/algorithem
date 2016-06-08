package com.jiangli.common.utils;

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
}
