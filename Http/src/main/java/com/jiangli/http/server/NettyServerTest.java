package com.jiangli.http.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiangli
 * @date 2017/10/12 17:12
 */
public class NettyServerTest {

    public static void main(String[] args) throws InterruptedException {
        NettyHelper.setNettyLoggerFactory();
        ExecutorService boss = Executors.newCachedThreadPool(new NamedThreadFactory("NettyServerBoss", true));
        ExecutorService worker = Executors.newCachedThreadPool(new NamedThreadFactory("NettyServerWorker", true));
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(boss, worker, 33);
        ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);


        //add handler
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                /*int idleTimeout = getIdleTimeout();
                if (idleTimeout > 10000) {
                    pipeline.addLast("timer", new IdleStateHandler(timer, idleTimeout / 1000, 0, 0));
                }*/
//                pipeline.addLast("decoder", adapter.getDecoder());
//                pipeline.addLast("encoder", adapter.getEncoder());
                pipeline.addLast("handler", new SimpleChannelHandler(){
                    @Override
                    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
                        super.messageReceived(ctx, e);
                        System.out.println("messageReceived:"+ctx+" msg:" + e.getMessage() + " " +e.getRemoteAddress());
                    }

                    @Override
                    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
                        super.channelConnected(ctx, e);
                        System.out.println("channelConnected:"+ctx+" " + e);
                    }

                    @Override
                    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
                        super.channelDisconnected(ctx, e);
                        System.out.println("channelDisconnected:"+ctx+" " + e);
                    }

                    @Override
                    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
                        super.channelClosed(ctx, e);
                        System.out.println("channelClosed:"+ctx+" " + e);
                    }
                });
                return pipeline;
            }
        });

        //localhost:6655
        // bind telnet localhost 6655
        org.jboss.netty.channel.Channel channel = bootstrap.bind(new InetSocketAddress("localhost", 6655));
        System.out.println("over...");

        new CountDownLatch(1).await();
    }
}
