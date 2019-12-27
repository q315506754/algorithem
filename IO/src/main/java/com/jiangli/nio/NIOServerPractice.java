package com.jiangli.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2019/5/23 16:07
 */
public class NIOServerPractice {
    public static void main(String[] args) {
        try {
            //telnet localhost 886
            Selector selector = Selector.open();

            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(886));

            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int select = selector.select();
                //int select = selector.select(5000);
                //System.out.println("select:"+select);
                if (select == 0) {
                    //System.out.println("select 0");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();


                    int i = selectionKey.readyOps();

                    if ( (i&SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        System.out.println("OP_ACCEPT");
                        ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();


                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ|SelectionKey.OP_CONNECT|SelectionKey.OP_WRITE);
                    }
                    else if ( (i&SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT) {
                        System.out.println("OP_CONNECT");
                        //ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();
                        //SocketChannel accept = channel.accept();
                        //accept.register(selector, SelectionKey.OP_READ|SelectionKey.OP_CONNECT);
                    }
                    else if ( (i&SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        System.out.println("OP_READ");
                        SocketChannel channel = (SocketChannel)selectionKey.channel();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
                        //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1);

                        System.out.println(byteBuffer.getClass());

                        String total = "";
                        int read = channel.read(byteBuffer);
                        while (read > 0) {
                            byte[] array = byteBuffer.array();
                            String s = new String(array);
                            total+=s;

                            //ascIi
                            //03
                            //3
                            //0x03
                            //ETX (end of text)
                            //正文结束
                            byte b = array[0];
                            System.out.println("from channel:"+s + "array[0]:"+ b);

                            //ctrl+c
                            if (b==3) {
                                System.out.println("close..."+channel);
                                channel.close();
                                break;
                            }

                            read = channel.read(byteBuffer);
                        }


                        if (channel.isOpen()) {
                            String wt = "\r\n\u001b[32myou just said:\u001b[44m"+total+"\u001B[0m\r\n";
                            channel.write(ByteBuffer.wrap(wt.getBytes()));

                            if (total.equals("%")) {
                                for (int j = 0; j <= 100; j++) {
                                    try {
                                        Thread.sleep(100);
                                        channel.write(ByteBuffer.wrap(("\u001b[1000D"+j+"%").getBytes()));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                        else if ( (i&SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
                            System.out.println("OP_WRITE");
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
