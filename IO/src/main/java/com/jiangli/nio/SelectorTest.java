package com.jiangli.nio;

import com.jiangli.common.utils.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class SelectorTest {
    public static void main(String[] args) throws Exception {
        File file = PathUtil.getClassFile(ChannelBytecodeTest.class,"com/jiangli/common/model/Person.class");
        System.out.println(file);
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());

        FileInputStream fin = new FileInputStream(file);
        FileChannel channel = fin.getChannel();

        //FileChannel不能切换到非阻塞模式。而套接字通道都可以。
//        channel.configureBlocking(false);

        int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;


    }
}
