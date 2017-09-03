package com.jiangli.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class SocketChannelTest {
    public static void main(String[] args) throws Exception {
        //SocketChannel 两种创建方式

        //1.打开一个SocketChannel并连接到互联网上的某台服务器。
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("www.mi.com", 80);
        System.out.println(address.isUnresolved());
        socketChannel.connect(address);
        System.out.println(address);

//        2.一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。

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
        System.out.println(wbuffer.position());
        System.out.println(wbuffer.limit());
        System.out.println(wbuffer.capacity());
        int write = socketChannel.write(wbuffer);
        System.out.println("total write:"+write);
        System.out.println(wbuffer.hasRemaining());
        while (wbuffer.hasRemaining()) {
            socketChannel.write(wbuffer);
        }

        System.out.println("read");
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int read = socketChannel.read(buffer);
        while (read>0) {
            System.out.println("responseN");
            System.out.println(new String(buffer.array()));

            read = socketChannel.read(buffer);
        }

    }
}
