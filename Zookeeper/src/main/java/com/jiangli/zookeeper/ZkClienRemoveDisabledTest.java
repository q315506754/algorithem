package com.jiangli.zookeeper;

import com.jiangli.common.utils.RecurUtil;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2018/12/26 15:42
 */
public class ZkClienRemoveDisabledTest {


    public static void main(String[] args) {
        final boolean shouldDelete = false;
        //final boolean shouldDelete = true;
        String CONNECT_ADDR = "zk1.i.g2s.cn:2181,zk2.i.g2s.cn:2181,zk3.i.g2s.cn:2181";
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
        System.out.println(zkc);

        //final String myip = getMyIp();
        final String myip = "192.168.222.3";

        System.out.println("我的ip:"+myip);


        Set<String> infs = new LinkedHashSet<>();
        RecurUtil.recursivePath("", "/",
                path -> {
                    if (!zkc.exists(path)) {
                        System.err.println(path);
                        return null;
                    }
                    return zkc.getChildren(path);
                }
                , (integer, path) -> {
                    RecurUtil.PathInfo pathInfo = RecurUtil.getCurPathInfo();

                    //System.out.println(pathInfo);
                    if (pathInfo.parentPath.toString().endsWith("configurators")) {
                        String nodeName = pathInfo.nodeName.toString();
                        if(nodeName.contains(myip) && nodeName.contains("disabled%3Dtrue")){
                            System.out.println(pathInfo.absPath);

                            zkc.delete(pathInfo.absPath.toString());
                        }
                    }
                });

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
