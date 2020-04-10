package com.jiangli.io;

import java.io.IOException;
import java.net.*;

/***
 * UDP Client端
 ***/
public class UdpClient {
   
    private String sendStr = "hello";
    private String netAddress = "255.255.255.255";
    private final int PORT = 5060;
   
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
   
    public UdpClient(){
        try {
             //datagramSocket = new DatagramSocket();
            DatagramSocket datagramSocket = new DatagramSocket();
            byte[] buf = sendStr.getBytes();

            InetAddress address = InetAddress.getByName(netAddress);
            System.out.println(address);
            datagramPacket = new DatagramPacket(buf, buf.length, address, PORT);
            datagramSocket.send(datagramPacket);
           
            byte[] receBuf = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
            datagramSocket.receive(recePacket);
           
            String receStr = new String(recePacket.getData(), 0 , recePacket.getLength());
            System.out.println("收到响应:"+receStr);

//获取服务端ip
            String serverIp = recePacket.getAddress().getHostAddress();
            System.out.println("收到服务器ip:"+serverIp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if(datagramSocket != null){
                datagramSocket.close();
            }
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UdpClient udpClient = new UdpClient();
                }
            }).start();
        }
    }
}