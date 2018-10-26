package com.jiangli.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Jiangli
 * @date 2018/9/17 11:13
 */
public class NioClient {
    public static void main(String[] args) {
        try {
            SocketChannel client = SocketChannel.open();
            client.connect(new InetSocketAddress(8080));

            ByteBuffer wrap = ByteBuffer.wrap("abc".getBytes());
            client.write(wrap);
            //client.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
