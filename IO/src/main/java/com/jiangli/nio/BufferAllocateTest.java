package com.jiangli.nio;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferAllocateTest {
    static int i = 1;
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer=ByteBuffer.allocateDirect(0);
        Class<?> c = Class.forName("java.nio.Bits");
        Field maxMemory = c.getDeclaredField("maxMemory");
        maxMemory.setAccessible(true);
        synchronized (c) {
            Long maxMemoryValue = (Long)maxMemory.get(null);
            System.out.println("maxMemoryValue:"+maxMemoryValue);
        }
        System.out.println("maxMemoryValue:"+sun.misc.VM.maxDirectMemory());

//        一种是heap ByteBuffer,该类对象分配在JVM的堆内存里面，直接由Java虚拟机负责垃圾回收，
//        JVM堆内存大小可以通过-Xmx来设置
//        ByteBuffer allocate = ByteBuffer.allocate(1234567891);


//        一种是direct ByteBuffer是通过jni在虚拟机外内存中分配的 通过jmap无法查看该快内存的使用情况。只能通过top来看它的内存使用情况。
//        通过-XX:MaxDirectMemorySize来设置，此参数的含义是当Direct ByteBuffer分配的堆外内存到达指定大小后，即触发Full GC。


        ByteBuffer allocate = ByteBuffer.allocateDirect(1234567891);
//        ByteBuffer allocate = ByteBuffer.allocate(1);
//        System.out.println(BeanUtils.describe(allocate));
        System.out.println(allocate);


//        direct ByteBuffer通过full gc来回收内存的，direct ByteBuffer会自己检测情况而调用system.gc()，
// 但是如果参数中使用了DisableExplicitGC那么就无法回收该快内存了，
// -XX:+DisableExplicitGC标志自动将System.gc()调用转换成一个空操作，
// 就是应用中调用System.gc()会变成一个空操作。那么如果设置了就需要我们手动来回收内存了


//        1:多用于网络编程中，实现zero copy,数据不需要再native memory和jvm memory中来回copy
//        2:由于构造和析构Direct Buffer时间成本高，建议使用缓冲池，参见netty的实现
    }


}
