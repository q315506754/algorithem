package com.jiangli.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;

public class POJOEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        POJO m = (POJO) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);

        encoded.writeInt(m.getLength());//先写长度 4个字节
        encoded.writeInt(m.getVersion());
        encoded.writeCharSequence(m.getName(), Charset.defaultCharset());
        encoded.writeCharSequence(m.getCmd(), Charset.defaultCharset());

        System.out.println("server encoder:"+m);
        ctx.write(encoded, promise); // (1)
    }

}