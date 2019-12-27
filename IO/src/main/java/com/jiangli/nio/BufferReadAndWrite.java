package com.jiangli.nio;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferReadAndWrite {
    static int i = 1;
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuffer allocate = ByteBuffer.allocate(10);
//        ByteBuffer allocate = ByteBuffer.allocate(1);

        allocate.put((byte) 123);
        allocate.put((byte) 44);

        System.out.println( allocate.get());
        System.out.println( allocate.get());

        allocate.put((byte) 33);
        allocate.put((byte) 22);

        //证明对于一个position，无需切换模式，读或写都会改变它的值，
        System.out.println(Arrays.toString(allocate.array()));
    }


}
