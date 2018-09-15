package com.jiangli.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class SocketChannelAsyncTest {
    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//重要  非阻塞模式

        //InetSocketAddress address = new InetSocketAddress("www.mi.com", 80);
        InetSocketAddress address = new InetSocketAddress("www.baidu.com", 80);

        System.out.println(address.isUnresolved());

        socketChannel.connect(address);
        System.out.println(address);

        while(!socketChannel.finishConnect() ){
            //wait, or do something else...
            System.out.println("wait...");
        }
        ByteBuffer allocate = ByteBuffer.allocate(4096);
        socketChannel.read(allocate);

        System.out.println(allocate.limit());
        System.out.println(allocate.position());
        System.out.println(new String(allocate.array()));
        System.out.println("finishConnect~");
    }
}
