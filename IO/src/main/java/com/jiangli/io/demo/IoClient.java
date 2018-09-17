package com.jiangli.io.demo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Jiangli
 * @date 2018/9/17 13:19
 */
public class IoClient {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(8080));

            InputStream inputStream = socket.getInputStream();
            System.out.println(IOUtils.copy(inputStream,System.out));

            //可以考虑用某种分隔符实现协议 比如\r\n
            System.out.println("can not reach...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
