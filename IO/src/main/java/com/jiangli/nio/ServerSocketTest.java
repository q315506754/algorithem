package com.jiangli.nio;

import org.apache.commons.beanutils.BeanUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class ServerSocketTest {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //telnet localhost 9999
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        //非阻塞
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        List<SocketChannel> clients = new LinkedList<>();

        while(true){
//            System.out.println("waiting...");

            int select = selector.select();
            if (select > 0) {
                System.out.println(select);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println(selectionKeys);
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                while(keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
//                                SelectableChannel channel = key.channel();
                    keyIterator.remove();

                    if(key.isAcceptable()) {
                        // a connection was accepted by a ServerSocketChannel.
                        System.out.println("isAcceptable");

                        ServerSocketChannel server = (ServerSocketChannel) key
                                .channel();
                        // 获得和客户端连接的通道
                        SocketChannel channel = server.accept();
//                        // 设置成非阻塞
                        channel.configureBlocking(false);

                        //在这里可以给客户端发送信息哦
                        channel.write(ByteBuffer.wrap(new String("向客户端发送了一条信息").getBytes()));
                        //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
                        channel.register(selector, SelectionKey.OP_READ);

                        //在线列表
                        clients.add(channel);
                    }else {
                        SocketChannel channel = (SocketChannel)key.channel();
                        if (channel.isOpen() && key.isConnectable()) {
                            // a connection was established with a remote server.
                            System.out.println("isConnectable");
                        }
                        if (channel.isOpen() && key.isReadable()) {
                            // a channel is ready for reading
                            System.out.println("isReadable:"+ BeanUtils.describe(channel));

                            // 创建读取的缓冲区
                            ByteBuffer buffer = ByteBuffer.allocate(10);
                            int read = channel.read(buffer);

//                            while (read>0) {
//                                read = channel.read(buffer);
//                            }

                            byte[] data = buffer.array();
                            String msg = new String(data).trim();
                            System.out.println("服务端收到信息："+msg);
                            System.out.println("服务端收到信息："+ Arrays.toString(data));

                            //异常退出
                            boolean isAllzero = true;
                            for (byte b : data) {
                                if (b != 0) {
                                    isAllzero = false;
                                    break;
                                }
                            }
                            if (isAllzero) {
                                channel.close();
                            }

                            //broadcast
                            Iterator<SocketChannel> iterator = clients.iterator();
                            while (iterator.hasNext()) {
                                SocketChannel next = iterator.next();
                                if (next.isOpen()) {
                                    buffer.flip();
                                    next.write(buffer);
                                }else {
                                    iterator.remove();
                                }
                            }
                        }

                        if (channel.isOpen() && key.isWritable()) {
                            // a channel is ready for writing
                            System.out.println("isWritable");
                        }
                    }


//                    SocketChannel channel = (SocketChannel)key.channel();
//                                channel.write(ByteBuffer.wrap(new String("向客户端发送了一条信息").getBytes()));


                }
            }

        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("thread running..");
//
//                    //select()方法返回的int值表示有多少通道已经就绪。亦即，自上次调用select()方法后有多少通道变成就绪状态。
//                    //阻塞到至少有一个通道在你注册的事件上就绪了。
////                    int select = selector.select();
////                    System.out.println(select);
//
//                    while (true) {
//                        int i = selector.selectNow();
//
//                        if (i > 0) {
//                            System.out.println("i:" + i);
//
//                            Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
//                            while(keyIterator.hasNext()) {
//                                SelectionKey key = keyIterator.next();
////                                SelectableChannel channel = key.channel();
//                                SocketChannel channel = (SocketChannel)key.channel();
//
//                                if(key.isAcceptable()) {
//                                    // a connection was accepted by a ServerSocketChannel.
//                                    System.out.println("isAcceptable");
//                                }
//                                if (key.isConnectable()) {
//                                    // a connection was established with a remote server.
//                                    System.out.println("isConnectable");
//                                }
//                                if (key.isReadable()) {
//                                    // a channel is ready for reading
//                                    System.out.println("isReadable");
//                                }
//                                if (key.isWritable()) {
//                                    // a channel is ready for writing
//                                    System.out.println("isWritable");
//                                }
//
////                                channel.write(ByteBuffer.wrap(new String("向客户端发送了一条信息").getBytes()));
//
//                                keyIterator.remove();
//                            }
////                        Thread.sleep(500);
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("thread over!");
//            }
//        }).start();
//
//
//        while(true){
//            System.out.println("waiting...");
//
//            SocketChannel socketChannel =
//                    serverSocketChannel.accept();
//
//            //非阻塞时会为空  不阻塞accept
//            if (socketChannel != null) {
//                socketChannel.configureBlocking(false);
//
//                System.out.println("accept new..");
////                SelectionKey key = socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                SelectionKey key = socketChannel.register(selector, SelectionKey.OP_CONNECT );
//                System.out.println(key);
//            }
//
////            Thread.sleep(500);
//            //do something with socketChannel...
//        }
    }
}
