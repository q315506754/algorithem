package com.jiangli.thread.main;

import com.jiangli.common.utils.ClsUtil;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Jiangli
 * @date 2018/9/28 9:08
 */
public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"COUNT_BITS"));
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"CAPACITY"));
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"RUNNING"));// -1 << COUNT_BITS;
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"SHUTDOWN"));//0 << COUNT_BITS;
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"STOP"));//1 << COUNT_BITS;
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"TIDYING"));// 2 << COUNT_BITS;
        System.out.println(ClsUtil.getField(ThreadPoolExecutor.class,"TERMINATED"));//3 << COUNT_BITS;


        System.out.println(Integer.toBinaryString((int)ClsUtil.getField(ThreadPoolExecutor.class,"RUNNING")));
        System.out.println(Integer.toBinaryString((int)ClsUtil.getField(ThreadPoolExecutor.class,"SHUTDOWN")));
        System.out.println(Integer.toBinaryString((int)ClsUtil.getField(ThreadPoolExecutor.class,"STOP")));
        System.out.println(Integer.toBinaryString((int)ClsUtil.getField(ThreadPoolExecutor.class,"TIDYING")));
        System.out.println(Integer.toBinaryString((int)ClsUtil.getField(ThreadPoolExecutor.class,"TERMINATED")));
    }
}
