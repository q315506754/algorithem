package com.jiangli.aio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jiangli
 * @date 2018/9/17 13:39
 */
public class AioClient {
    public static void main(String[] args) {
        try {
            AsynchronousSocketChannel open = AsynchronousSocketChannel.open();
            Future<Void> connect = open.connect(new InetSocketAddress(8080));
            while (!connect.isDone()) {
                //System.out.println("wait");
            }
            System.out.println("connect over...");
            System.out.println(open.isOpen());//false?
            //System.out.println(open.getLocalAddress());

            open.write(ByteBuffer.wrap("aabc".getBytes()), null, new CompletionHandler<Integer, Object>() {

                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("write over...");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });

            //open.connect(new InetSocketAddress(8080), null, new CompletionHandler<Void, Object>() {
            //    @Override
            //    public void completed(Void result, Object attachment) {
            //        System.out.println("connected...");
            //    }
            //
            //    @Override
            //    public void failed(Throwable exc, Object attachment) {
            //
            //    }
            //});

            LockSupport.park();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
