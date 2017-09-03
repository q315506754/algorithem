package com.jiangli.nio;

import com.jiangli.common.utils.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.Arrays;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class ChannelBytecodeTest {
    public static void main(String[] args) throws Exception {
//        File file = new File("pom.xml");
        File file = PathUtil.getClassFile(ChannelBytecodeTest.class,"com/jiangli/common/model/Person.class");
        System.out.println(file);
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());

        FileInputStream fin = new FileInputStream(file);
        FileChannel channel = fin.getChannel();

        ByteBuffer magic_number = ByteBuffer.allocate(4);
        ByteBuffer minor_version = ByteBuffer.allocate(2);
        ByteBuffer major_version = ByteBuffer.allocate(2);
        ByteBuffer constant_pool_count = ByteBuffer.allocate(2);

        //总共读取字节数
        long read =   read(channel,magic_number, minor_version, major_version, constant_pool_count);

        System.out.println("totalread:"+read);

        print(magic_number);
        print(minor_version);
        print(major_version);
        int print = print(constant_pool_count);
        System.out.println(print);


//        ByteBuffer constant_pool = ByteBuffer.allocate(print);
//        channel.read(new ByteBuffer[]{constant_pool});

//        printString(constant_pool,2);

        for (int i = 0; i < print; i++) {
            ByteBuffer tag = ByteBuffer.allocate(1);
            read(channel,tag);

            int tagN = print(tag);
            switch (tagN) {
                case 9:
                case 10:{
                    ByteBuffer class_index = ByteBuffer.allocate(2);
                    ByteBuffer nameandtype_index = ByteBuffer.allocate(2);
                    read(channel,class_index,nameandtype_index);
                    print(class_index);
                    print(nameandtype_index);
                }break;
                case 12:{
                    ByteBuffer index1 = ByteBuffer.allocate(2);
                    ByteBuffer index2 = ByteBuffer.allocate(2);
                    read(channel,index1,index2);
                    print(index1);
                    print(index2);
                }break;
                case 7:{
                    ByteBuffer name_index = ByteBuffer.allocate(2);
                    read(channel,name_index);
                    print(name_index);
                }break;
                case 1:{
                    ByteBuffer length = ByteBuffer.allocate(2);
                    read(channel, length);
                    int realL = print(length);
                    ByteBuffer bytes = ByteBuffer.allocate(realL);
                    read(channel, bytes);
                    System.out.print("index("+i+"):");
                    printString(bytes);
                }break;
            }
            System.out.println("--------------------");
        }

    }
    private static long read(ScatteringByteChannel channel, ByteBuffer... byteBuffers) {
        try {
            return channel.read(byteBuffers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    private static long write(GatheringByteChannel channel, ByteBuffer... byteBuffers) {
        try {
            return channel.write(byteBuffers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int printString(ByteBuffer bf) {
        return printString(bf,bf.array().length);
    }

    private static int printString(ByteBuffer bf,final int n) {
        int ret = 0;
        byte[] array = bf.array();

        for (int i = 0; i < array.length; i+=n) {
            byte[] b = new byte[n];
            System.arraycopy(array,i,b,0,n);

            System.out.println(new String(b));
        }
        return ret;
    }

    private static int print(ByteBuffer bf) {
        byte[] array = bf.array();
        System.out.println(Arrays.toString(array));
        int count=1;
        int total=0;
        for (byte b : array) {
//            System.out.println(b&0xff);
//            System.out.println((int)b);
            total += b & 0xff << (array.length - count++);
            System.out.print(Integer.toHexString(b&0xff));
//            System.out.print(Integer.toHexString((int)b));
        }
        System.out.println();
        return total;
    }
}
