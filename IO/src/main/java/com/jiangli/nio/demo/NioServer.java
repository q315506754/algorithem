package com.jiangli.nio.demo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2018/9/17 10:21
 */
public class NioServer {
    //telnet localhost 8080
    public static void main(String[] args) {
        try {

            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();

            serverChannel.configureBlocking(false);

            //register必须在设置  Blocking=false 之后 不然 java.nio.channels.IllegalBlockingModeException
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            serverChannel.bind(new InetSocketAddress(8080));
            //serverChannel.socket().bind(new InetSocketAddress(8080));


            //这种无法连接上
            //serverChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(),8080));

            System.out.println(InetAddress.getLocalHost());

            while (true) {
                int select = selector.select();
                System.out.println("select:"+select);

                if (select == 0) {
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                System.out.println("keys:"+keys);

                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();

                    System.out.println("interestOps:"+next.interestOps());
                    int i = next.readyOps();
                    System.out.println(i&SelectionKey.OP_READ);
                    System.out.println(i&SelectionKey.OP_ACCEPT);
                    System.out.println(i&SelectionKey.OP_CONNECT);
                    System.out.println(i&SelectionKey.OP_WRITE);

                    if (next.isAcceptable()) {
                        System.out.println("accept...");
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();

                        //注意 若不accept  则selector.select()会一直不为0 ，因为数据还在信道里没取出来
                        SocketChannel client = channel.accept();
                        //这里也必须设置 Blocking=false  不然 java.nio.channels.IllegalBlockingModeException
                        client.configureBlocking(false);

                        //client.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
                        client.register(selector,  SelectionKey.OP_READ);
                    } else if (next.isConnectable()) {
                        System.out.println("connect...");
                        //SelectableChannel client = next.channel();
                        SocketChannel client = (SocketChannel)next.channel();
                        //SocketChannel;

                        if (client.isConnectionPending()) {
                            System.out.println("isConnectionPending:true");
                            //结束连接
                            client.finishConnect();
                        }
                        //throw  new Error();
                        //注意 若不accept  则selector.select()会一直不为0 ，因为数据还在信道里没取出来
                    } else if (next.isReadable()) {
                        System.out.println("read...");

                        SocketChannel client = (SocketChannel)next.channel();
                        if (!client.isOpen()) {
                            System.out.println("isClosed...");
                            break;
                        }
                        //SocketChannel;
                        //ByteBuffer allocate = ByteBuffer.allocate(1);//若set过小  则会多次进入循环
                        ByteBuffer allocate = ByteBuffer.allocate(30);//
                        System.out.println(client);

                        int read = client.read(allocate);

                        System.out.println("read..:"+read);
                        String s = new String(allocate.array());
                        System.out.println("read msg..:"+ s);

                        if (s.equalsIgnoreCase("q")) {
                            client.close();
                        } else {
                            allocate.flip();
                            client.write(allocate);
                        }
                    } else {
                        System.out.println("unkown...");
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
