package com.jiangli.nio;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author Jiangli
 * @date 2017/9/4 18:12
 */
public class AsyncServerlChannelTest {
    public static void main(String[] args) throws Exception {

//        telnet localhost 9999
//        NIO是同步非阻塞的
//
//        AIO是异步非阻塞的
//
//        由于NIO的读写过程依然在应用线程里完成，所以对于那些读写过程时间长的，NIO就不太适合。
//
//        而AIO的读写过程完成后才被通知，所以AIO能够胜任那些重量级，读写过程长的任务。

        AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(9999));

        AcceptHandler handler = new AcceptHandler();
        handler.ref = channel;
        channel.accept(null, handler);

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

   static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
       public AsynchronousServerSocketChannel ref;

       @Override
        public void completed(AsynchronousSocketChannel result, Object attachment) {
            System.out.println("completed...");

            //监听下一个
           ref.accept(null,this);

            //创建新的Buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //异步读  第三个参数为接收消息回调的业务Handler
           EchoHandler handler = new EchoHandler();
           handler.ref = result;
           result.read(buffer, buffer, handler);
        }

        @Override
        public void failed(Throwable exc, Object attachment) {

        }
    }

    static class EchoHandler implements CompletionHandler<Integer, ByteBuffer> {
        public AsynchronousSocketChannel ref;

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            String expression = null;
            try {
                expression = new String(attachment.array(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("服务器收到消息: " + expression);
            String calrResult = null;

            attachment.clear();
            ref.read(attachment, attachment, this);
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

        }
    }
}
