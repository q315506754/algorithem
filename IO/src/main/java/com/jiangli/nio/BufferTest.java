package com.jiangli.nio;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferTest {
    static int i = 1;
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        ByteBuffer allocateDi = ByteBuffer.allocateDirect(1024);
        IntBuffer intBuffer = IntBuffer.allocate(1024);
        LongBuffer longBuffer = LongBuffer.allocate(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        System.out.println(allocate.isDirect()+" "+allocate.isReadOnly());
        System.out.println(allocateDi.isDirect()+" "+allocateDi.isReadOnly());
        System.out.println(intBuffer.isDirect()+" "+intBuffer.isReadOnly());
        System.out.println(longBuffer.isDirect()+" "+longBuffer.isReadOnly());
        System.out.println(charBuffer.isDirect()+" "+charBuffer.isReadOnly());

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

        //flip后可以读取
        ByteBuffer flip = (ByteBuffer) allocate.flip();
        print(allocate);//5

        //要读的时候flip
        //read
        System.out.println(flip.get());
        System.out.println(flip.get());
        System.out.println(flip.get());//读取后position会+1
        print(allocate);//6


        flip = (ByteBuffer)allocate.flip();
        print(allocate);//7

        //write
        allocate.put((byte)12);
        allocate.put((byte)13);
        allocate.put((byte)14);
        //java.nio.BufferOverflowException  上溢
        //allocate.put((byte)15);
        print(allocate);//8


        System.out.println("sss:"+new String(((ByteBuffer)flip).array(),0,flip.limit())+":eee");

        //要写的时候clear
        allocate.clear();
        print(allocate);//9
    }

    public static void print(ByteBuffer allocate) {
        System.out.println("--------"+(i++)+"---------");
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
    }

}
