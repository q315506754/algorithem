package com.jiangli.zookeeper;

import com.jiangli.common.utils.RecurUtil;
import com.jiangli.zookeeper.utils.DubboURL;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 设置我的接口优先级
 *
 * @author Jiangli
 * @date 2018/12/26 15:42
 */
public class ZkClientMonitorTest {


    public static void main(String[] args) {
        //final boolean shouldDelete = false;
        final boolean shouldDelete = true;
        String CONNECT_ADDR = "zk1.i.g2s.cn:2181,zk2.i.g2s.cn:2181,zk3.i.g2s.cn:2181";
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
        System.out.println(zkc);

        final String myip = getMyIp();
        //final String myip = "192.168.222.3";

        System.out.println("我的ip:" + myip);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //current 2018-03-13 11:04:34

        scheduledExecutorService.scheduleWithFixedDelay(() -> {
        //scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("检查:" + sdf.format(new Date()));

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

                        if (pathInfo.parentPath.toString().endsWith("configurators")) {
                            //System.out.println(pathInfo.nodeName + ":"+pathInfo);
                            String nodeName = pathInfo.nodeName.toString();
                            String urlEncoded = pathInfo.nodeName.toString();
                            String decode = URLDecoder.decode(urlEncoded);
                            //convert
                            DubboURL dubboURL = DubboURL.valueOf(decode);
                            String host = dubboURL.getHost();
                            String weight = dubboURL.getParameter("weight");
                            String disabled = dubboURL.getParameter("disabled");

                            String serviceKey = dubboURL.getServiceKey();

                            if (!host.contains(myip) && weight != null && Integer.parseInt(weight) > 100) {
                                //System.out.println("接口:" + serviceKey + " 已加权:" + decode);

                                //if (shouldDelete) {
                                //    System.err.println("    删除:" + pathInfo.absPath);
                                //    zkc.delete(pathInfo.absPath.toString());
                                //}
                            }

                            if (host.contains(myip) && disabled != null) {

                                System.out.println("接口:" + serviceKey + " 已禁用:" + decode);

                                if (shouldDelete) {
                                    System.err.println("    删除:" + pathInfo.absPath);
                                    zkc.delete(pathInfo.absPath.toString());
                                }
                            }

                        }

                    });

            System.out.println("检查over:" + sdf.format(new Date()));
            System.out.println("----------------");
        }, 1, 60L, TimeUnit.SECONDS);
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
