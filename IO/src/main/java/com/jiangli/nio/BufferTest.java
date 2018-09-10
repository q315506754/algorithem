package com.jiangli.nio;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferTest {
    static int i = 1;
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
//        ByteBuffer allocate = ByteBuffer.allocate(1);
        System.out.println(BeanUtils.describe(allocate));

        print(allocate);//1
        allocate.put((byte)12);
        allocate.put((byte)12);
        allocate.put((byte)12);
        print(allocate);//2

        allocate.mark();
        allocate.put((byte) 23);
        allocate.put((byte) 34);
        allocate.put((byte)45);
        print(allocate);//3

        allocate.reset();
        print(allocate);//4

        ByteBuffer flip = (ByteBuffer) allocate.flip();
        print(allocate);//5

        //read
        System.out.println(flip.get());
        System.out.println(flip.get());
        System.out.println(flip.get());
        print(allocate);//6


        flip = (ByteBuffer)allocate.flip();
        print(allocate);//7

        //write
        allocate.put((byte)12);
        allocate.put((byte)13);
        allocate.put((byte)14);
        //java.nio.BufferOverflowException
        //allocate.put((byte)15);
        print(allocate);//8


        System.out.println("sss:"+new String(((ByteBuffer)flip).array(),0,flip.limit())+":eee");

    }

    public static void print(ByteBuffer allocate) {
        System.out.println("--------"+(i++)+"---------");
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
    }

}
