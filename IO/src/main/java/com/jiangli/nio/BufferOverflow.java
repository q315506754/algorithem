package com.jiangli.nio;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferOverflow {
    static int i = 1;
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuffer allocate = ByteBuffer.allocate(1);
//        ByteBuffer allocate = ByteBuffer.allocate(1);

        allocate.put((byte) 1323);

        //java.nio.BufferOverflowException
        allocate.put((byte) 1323);

    }

    public static void print(ByteBuffer allocate) {
        System.out.println("--------"+(i++)+"---------");
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
    }

}
