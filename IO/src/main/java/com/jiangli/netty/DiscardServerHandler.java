package com.jiangli.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        //((ByteBuf) msg).release(); // (3)
        ctx.write("say:"); // (1)
        ctx.write(msg); // (1)
        ctx.flush(); // (2)

        //ByteBuf in = (ByteBuf) msg;
        //try {
        //    while (in.isReadable()) { // (1)
        //        byte b = in.readByte();
        //        System.out.print((char) b);
        //        System.out.flush();
        //
        //        ctx.write("hhhhsdsdsd"); // (1)
        //        ctx.flush(); // (2)
        //    }
        //} finally {
        //    //ReferenceCountUtil.release(msg); // (2)

        //io.netty.util.IllegalReferenceCountException: refCnt: 0
        //}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}