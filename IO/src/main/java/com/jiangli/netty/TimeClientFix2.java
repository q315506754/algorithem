package com.jiangli.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClientFix2 {
    public static void main(String[] args) throws Exception {
        //String host = args[0];
        String host = "localhost";
        //int port = Integer.parseInt(args[1]);
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            //跟server的区别在此  一个ServerBootstrap 一个Bootstrap
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2) 不分 boss和worker
            ///跟server的区别在此  一个NioServerSocketChannel 一个NioSocketChannel
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeDecoderFix2(), new TimeClientHandler());
                    //ch.pipeline().addLast(new TimeClientHandler(), new TimeDecoderFix2());
                }
            });
            
            // Start the client. connect() instead of bind()
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}