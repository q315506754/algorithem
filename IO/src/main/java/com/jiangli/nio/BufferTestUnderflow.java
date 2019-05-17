package com.jiangli.nio;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferTestUnderflow {
    static int i = 1;
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuffer allocate = ByteBuffer.allocate(1);
//        ByteBuffer allocate = ByteBuffer.allocate(1);
        ByteBuffer flip = (ByteBuffer)allocate.flip();

        //要读的时候flip
        //read
        //因为没有数据
        //java.nio.BufferUnderflowException  下溢
        System.out.println(flip.get());
        System.out.println(flip.get());
        System.out.println(flip.get());//读取后position会+1
        print(allocate);//6
    }

    public static void print(ByteBuffer allocate) {
        System.out.println("--------"+(i++)+"---------");
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
    }

}
