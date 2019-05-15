package com.jiangli.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2018/12/26 15:42
 */
public class ZkSetServiceWeightTest {


    public static void main(String[] args) {
        //final boolean shouldDelete = true;
        String CONNECT_ADDR = "zk1.i.g2s.cn:2181,zk2.i.g2s.cn:2181,zk3.i.g2s.cn:2181";
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
        System.out.println(zkc);

        final String myip = getMyIp();

        Arrays.asList("");
        zkc.createPersistent("/dubbo/com.zhishi.aries.erp.openapi.serverWeb.DeliCustomerRelaCompanyOpenService/configurators/override%3A%2F%2F192.168.60.49%3A20880%2Fcom.zhishi.aries.erp.openapi.serverWeb.DeliCustomerRelaCompanyOpenService%3Fcategory%3Dconfigurators%26dynamic%3Dfalse%26enabled%3Dtrue%26group%3Dpc%26version%3D1.0.0%26weight%3D88888");
    }

    private static String getMyIp() {
        String myip = "192.168.60.49";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            myip = localHost.getHostAddress();
            //System.out.println(localHost);//Jiangli-mi-pc/192.168.60.49
            //System.out.println(localHost.getHostName());//Jiangli-mi-pc
            //System.out.println(localHost.getHostAddress());//192.168.60.49
            //System.out.println(localHost.getCanonicalHostName());//windows10.microdone.cn
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return myip;
    }
}
