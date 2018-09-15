package com.jiangli.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class POJODecoder extends ByteToMessageDecoder { // (1)
    Long glength = null;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        //这里没考虑并发情况
        if (glength == null) {
            if (in.readableBytes() < 4) {
                System.out.println("in.readableBytes() < 4...");
                return; // (3)
            }

            //int length = in.readInt();
            long length = in.readUnsignedInt();
            System.out.println("total dto length ："+length);

            glength = length;
        }

        if (in.readableBytes() < glength) {
            System.out.println("in.readableBytes() < totalLengthWithLength..."+glength);
            return; // (3)
        }

        POJO m = new POJO();
        m.setVersion(in.readInt());
        m.setName(String.valueOf(in.readCharSequence("AAA".getBytes().length, Charset.defaultCharset())));
        m.setCmd(String.valueOf(in.readCharSequence("AAA AAA".getBytes().length, Charset.defaultCharset())));
        out.add(m); // (4)

        glength = null;
    }
}