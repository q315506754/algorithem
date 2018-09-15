package com.jiangli.nio;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Created by Jiangli on 2017/9/3.
 */
public class PipeTest {
    public static void main(String[] args) throws Exception {
        //Java NIO 管道是2个线程之间的单向数据连接。
        // Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sinkChannel = pipe.sink();
        Pipe.SourceChannel sourceChannel = pipe.source();

        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        ByteBuffer put = buf.put(newData.getBytes());
        System.out.println(put);
        buf.flip();

        while(buf.hasRemaining()) {
            sinkChannel.write(buf);
        }

         buf.clear();
        int bytesRead = sourceChannel.read(buf);
        System.out.println(bytesRead);
        System.out.println(new String(buf.array()));
    }
}
