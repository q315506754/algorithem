package com.jiangli.aio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jiangli
 * @date 2018/9/17 13:34
 */
public class AioServer {

    //telnet localhost 8080
    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
            server.bind(new InetSocketAddress(8080));


            CompletionHandler<AsynchronousSocketChannel, Object> handler = new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("completed");

                    //指定后才会接收到下一个连接
                    server.accept(attachment, this);

                    result.write(ByteBuffer.wrap("hello aio...".getBytes()));

                    System.out.println("write over...");

                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            };
            server.accept(null, handler);

            //必须阻塞主线程 不然会执行结束
            LockSupport.park();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
