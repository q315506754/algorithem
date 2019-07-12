package com.jiangli.net;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 多网卡
 * @author Jiangli
 * @date 2019/5/31 14:40
 */
public class LocalAddrTest {
    public static void main(String[] args) throws Exception {
        InetAddress inetAddress=InetAddress.getLocalHost();
        System.out.println(inetAddress);

        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        while (nifs.hasMoreElements()) {
            NetworkInterface networkInterface = nifs.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            System.out.println(networkInterface + " " + networkInterface.getName()+" " + networkInterface.getDisplayName());
            while (inetAddresses.hasMoreElements()) {
                InetAddress addr = inetAddresses.nextElement();
                System.out.println("--"+addr +" hostname:" + addr.getHostName() + " ipv4:" + (addr instanceof Inet4Address)+ " ipv6:" + (addr instanceof Inet6Address));
            }
        }
    }

}
