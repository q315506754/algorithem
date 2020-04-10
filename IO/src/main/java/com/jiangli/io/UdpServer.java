package com.jiangli.io;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * udp连接，用于动态ip, pos向255.255.255.255：5060发送请求即可
 * **/
public class UdpServer extends Thread implements Runnable {
    private final int MAX_LENGTH = 1024;
    private final int PORT = 5060;
    private DatagramSocket datagramSocket;
    
    public void run() {
        try {
            init();
            while(true){
                try {
                    byte[] buffer = new byte[MAX_LENGTH];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    receive(packet);
                    String receStr = new String(packet.getData(), 0 , packet.getLength());
                    System.out.println("接收数据包" + receStr);
                    System.out.println("数据包内容"+packet.getAddress().getHostAddress() + "：" + packet.getPort());


                    byte[] bt = new byte[packet.getLength()];
                    System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
                    System.out.println("响应包内容"+packet.getAddress().getHostAddress() + "：" + packet.getPort() + "：" + Arrays.toString(bt));
                    packet.setData(bt);
                    response(packet);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    //LoggerUtil.error("udp线程出现异常：" + e.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive(DatagramPacket packet) throws Exception {
        datagramSocket.receive(packet);
    }

    public void response(DatagramPacket packet) throws Exception {
        datagramSocket.send(packet);
    }
    
    /**
     * 初始化连接
     */
    public void init(){
        try {
            datagramSocket = new DatagramSocket(PORT);
            System.out.println("udp服务端已经启动！");
        } catch (Exception e) {
            datagramSocket = null;
            System.out.println("udp服务端启动失败！");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UdpServer().start();
    }
}