package com.jiangli.nio;

import com.jiangli.common.utils.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class ChannelTest {
    public static void main(String[] args) throws Exception {
//        File file = new File("pom.xml");
        File file = PathUtil.getClassFile(ChannelTest.class,"props/application.properties");
        System.out.println(file);
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());

        FileInputStream fin = new FileInputStream(file);
        FileChannel channel = fin.getChannel();

        System.out.println("文件大小:"+channel.size());

        ByteBuffer buffer = ByteBuffer.allocate(48);


//        分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。


//        聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。


        //返回的是实际读取数量
        int read = channel.read(buffer);


        while (read >0) {
            buffer.flip();

            System.out.println("------------本次读取："+read);

            //这里不用flip
//            System.out.println(new String(buffer.array()));

            //使用get操作需要flip
//            StringBuilder sb = new StringBuilder();
//            //
//            while (buffer.hasRemaining()) {
//                sb.append((char)buffer.get());//这么写中文会乱码
//            }
//            System.out.println(sb);



            //使用get操作需要flip
            StringBuilder sb = new StringBuilder();
            byte[] bytes = new byte[buffer.limit()];//使用bytes  最后编码成string 中文才不会乱码
            int count=0;
            while (buffer.hasRemaining()) {
                bytes[count++] = buffer.get();
            }
            System.out.println(new String(bytes));




            buffer.clear();
            read = channel.read(buffer);
        }
        System.out.println("read:"+read); //最后为-1

//        FileChannel fileChannel = FileChannel.open();
    }
}
