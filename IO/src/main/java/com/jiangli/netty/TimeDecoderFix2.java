package com.jiangli.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoderFix2 extends ByteToMessageDecoder { // (1)

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            System.out.println("in.readableBytes() < 4...");
            return; // (3)
        }
        
        out.add(in.readBytes(4)); // (4)
    }
}