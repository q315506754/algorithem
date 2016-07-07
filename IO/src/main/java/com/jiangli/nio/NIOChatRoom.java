package com.jiangli.nio;

import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/24 0024 11:21
 */
public class NIOChatRoom {
    public static final String ENTER_DELI = "\r\n";
    public static String consoleCharset = "gbk";
    private static List<SocketChannel> clients = new LinkedList<>();
    private static  Map<String,SocketChannel> clientsMap = new HashMap<>();
    private static  Map<SocketChannel,String> clientsCmd = new HashMap<>();

    static class ReadThread implements Runnable {
        private Selector msgSelector;

        public ReadThread(Selector msgSelector) {
            this.msgSelector = msgSelector;
        }

        @Override
        public void run() {
            System.out.println("ReadThread start...");
            while (true) {
                try {
                    int select = msgSelector.select();
                    System.out.println("msgSelector select rs:" + select);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //telnet localhost 8000
    //chcp 65001
    public static void main(String[] args) throws Exception {
        int port = 8000;
        Selector connectSelector = Selector.open();
        Selector msgSelector = Selector.open();
        System.out.println(connectSelector);
        System.out.println(msgSelector);


        ServerSocketChannel serverChannel = ServerSocketChannel.open();

        System.out.println("validOps rs:" + serverChannel.validOps());
        serverChannel.configureBlocking(false);
        System.out.println("validOps rs:" + serverChannel.validOps());
        serverChannel.socket().bind(new InetSocketAddress(port));
//        serverChannel.register(connectSelector, SelectionKey.OP_CONNECT);
//        serverChannel.register(connectSelector, SelectionKey.OP_READ);
//        serverChannel.register(connectSelector, SelectionKey.OP_WRITE);
        serverChannel.register(connectSelector, SelectionKey.OP_ACCEPT);
//        serverChannel.register(msgSelector, SelectionKey.OP_ACCEPT);

        Thread readThread = new Thread(new ReadThread(msgSelector));
        System.out.println("init over~" + serverChannel.validOps());

        while (true) {
            System.out.println("--------------loop----------------");
            int select = connectSelector.select();
            System.out.println("select rs:" + select);

            Set<SelectionKey> selectionKeys = connectSelector.selectedKeys();
            System.out.println("selectionKeys:" + selectionKeys);
            System.out.println("validOps rs:" + serverChannel.validOps());

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
//            for (SelectionKey selectionKey : selectionKeys) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                SelectableChannel channel = selectionKey.channel();
                if (selectionKey.isAcceptable()) {
                    System.out.println("selectionKey.isAcceptable()");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;

                    // 获得和客户端连接的通道
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    System.out.println("channel:" + channel.validOps());
                    System.out.println("socketChannel:" + socketChannel.validOps());
                    System.out.println("socketChannel OP_ACCEPT:" + (socketChannel.validOps() & SelectionKey.OP_ACCEPT));
                    System.out.println("socketChannel OP_CONNECT:" + (socketChannel.validOps() & SelectionKey.OP_CONNECT));
                    System.out.println("socketChannel OP_READ:" + (socketChannel.validOps() & SelectionKey.OP_READ));
                    System.out.println("socketChannel OP_WRITE:" + (socketChannel.validOps() & SelectionKey.OP_WRITE));

                    writeToChannel(socketChannel, "欢迎进入聊天室~");

//                    设置成非阻塞
                    socketChannel.configureBlocking(false);
                    socketChannel.register(connectSelector, SelectionKey.OP_READ);
//                    socketChannel.register(msgSelector, SelectionKey.OP_READ);

                    clients.add(socketChannel);
//                    readThread.start();

                } else if (selectionKey.isReadable()) {
                    System.out.println("selectionKey.isReadable()");
                    SocketChannel socketChannel = (SocketChannel) channel;

                    String oneChar = readFromChannel(socketChannel);
                    if (StringUtils.isEmpty(oneChar)) {
                        System.out.println("closed?");
                        continue;
                    }

                    clientsCmd.compute(socketChannel, (SocketChannel key, String str) ->
                             (str==null?"":str) + oneChar);

                    if (oneChar.equals(ENTER_DELI)) {
                        System.out.println("firing:"+oneChar);
                        writeToChannels(clients, ENTER_DELI+"somebody said:" + clientsCmd.get(socketChannel) + "\r\n");
                        clientsCmd.remove(socketChannel);
                    } else {
//                        System.out.println("oneChar:"+oneChar + " cmd:"+ clientsCmd.get(socketChannel) );
                    }

//                    writeToChannel(socketChannel, "somebody said:" + s + "\r\n");
//                    writeToChannels(clients, "somebody said:" + s + "\r\n");

//                    System.out.println("from client:" + s);
                }

            }
        }

    }


    public static void writeToChannel(SocketChannel channel, String msg) {
        try {
            ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes(consoleCharset));
            channel.write(outBuffer);// 将消息回送给客户端
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeToChannels(Collection<SocketChannel> channels, String msg) {
        Iterator<SocketChannel> iterator = channels.iterator();
        while (iterator.hasNext()) {
            SocketChannel channel = iterator.next();
            if( channel.isOpen()){
                writeToChannel(channel, msg);
            }

        }


    }
    public static String readFromChannel(SocketChannel channel) {
        StringBuilder sb = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int loopSize = 0;
        //文件内容的大小

        try {
            do {
                System.out.println("read loop...");
                //            ByteBuffer buffer = ByteBuffer.allocate(2560);
                //第三步 将通道中的数据读取到缓冲区中
                channel.read(buffer);

                Buffer bf = buffer.flip();
                System.out.println("read size:" + bf.limit());
                loopSize = bf.limit();
                byte[] bt = buffer.array();
                String strByBuffer = new String(bt, 0, bf.limit());
                //            System.out.println(strByBuffer);
                sb.append(strByBuffer);

                buffer.clear();
            } while (loopSize > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();

    }
}
