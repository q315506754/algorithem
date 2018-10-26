package com.jiangli.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author Jiangli
 * @date 2018/9/17 11:13
 */
public class NioClient2 {
    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();

            SocketChannel client = SocketChannel.open();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_CONNECT);


            client.connect(new InetSocketAddress(8080));

            while (true) {
                int select = selector.select();
                //System.out.println("select:"+select);

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    System.out.println(next.readyOps());

                    if (next.isConnectable()) {
                        System.out.println("connect...");

                        SocketChannel channel = (SocketChannel) next.channel();
                        if (channel.isConnectionPending()) {
                            System.out.println("isConnectionPending:true");
                            //结束连接
                            channel.finishConnect();
                        }


                        channel.write(ByteBuffer.wrap("aaccbb".getBytes()));
                    }
                }
            }


            //ByteBuffer wrap = ByteBuffer.wrap("abc".getBytes());
            //client.write(wrap);
            //client.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
