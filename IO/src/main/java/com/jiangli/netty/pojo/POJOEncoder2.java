package com.jiangli.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class POJOEncoder2 extends MessageToByteEncoder<POJO> {

    @Override
    public void encode(ChannelHandlerContext ctx, POJO m, ByteBuf encoded) {
        encoded.writeInt(m.getLength());//先写长度 4个字节
        encoded.writeInt(m.getVersion());
        encoded.writeCharSequence(m.getName(), Charset.defaultCharset());
        encoded.writeCharSequence(m.getCmd(), Charset.defaultCharset());
        System.out.println("server encoder2:"+m);
    }

}