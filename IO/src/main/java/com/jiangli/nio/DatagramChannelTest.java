package com.jiangli.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class DatagramChannelTest {
    public static void main(String[] args) throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        channel.receive(buf);

        int bytesSent = channel.send(buf, new InetSocketAddress("jenkov.com", 80));


        //当连接后，也可以使用read()和write()方法，就像在用传统的通道一样。只是在数据传送方
        channel.connect(new InetSocketAddress("jenkov.com", 80));



    }
}
