package com.jiangli.io.demo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jiangli
 * @date 2018/9/17 13:14
 */
public class IoServer {
    //telnet localhost 8080
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8080));

            //blocking
            while (true) {
                System.out.println("accept wait...");

                Socket accept = serverSocket.accept();

                new Thread(() -> {
                    try {
                        OutputStream outputStream = accept.getOutputStream();
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                        outputStreamWriter.write("hello");
                        outputStreamWriter.flush();

                        InputStream inputStream = accept.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bf = new BufferedReader(inputStreamReader);
                        String str=null;
                        while ((str=bf.readLine())!=null) {
                            System.out.println(str);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
