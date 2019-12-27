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

        InetSocketAddress address = new InetSocketAddress("www.mi.com", 80);
        //InetSocketAddress address = new InetSocketAddress("www.baidu.com", 80);

        System.out.println(address.isUnresolved());

        socketChannel.connect(address);
        System.out.println(address);

        while(!socketChannel.finishConnect() ){
            //wait, or do something else...
            System.out.println("wait...");
        }

        String string = "GET / HTTP/1.1\r\n" +
                "Host: www.mi.com\r\n" +
                "Connection: keep-alive\r\n" +
                "Pragma: no-cache\r\n" +
                "Cache-Control: no-cache\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: zh-CN,zh;q=0.8,en;q=0.6\r\n" +
                "\r\n";
        //        String string = "GET / HTTP/1.1\r\n\r\n";


        byte[] bytes = string.getBytes();
        ByteBuffer wbuffer = ByteBuffer.allocate(bytes.length);
        wbuffer.put(bytes);
        wbuffer.flip();
        while (wbuffer.hasRemaining()) {
            socketChannel.write(wbuffer);
        }

        ByteBuffer allocate = ByteBuffer.allocate(4096);
        int read = socketChannel.read(allocate);
        System.out.println("read:"+read);
        System.out.println(allocate.limit());
        System.out.println(allocate.position());

        System.out.println(new String(allocate.array()));
        System.out.println("finishConnect~");
    }
}
