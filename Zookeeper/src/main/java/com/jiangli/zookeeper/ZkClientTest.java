package com.jiangli.zookeeper;

import com.jiangli.common.utils.RecurUtil;
import com.jiangli.zookeeper.utils.DubboURL;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2018/12/26 15:42
 */
public class ZkClientTest {


    public static void main(String[] args) {
        //final boolean shouldDelete = false;
        final boolean shouldDelete = true;
        String CONNECT_ADDR = "zk1.i.g2s.cn:2181,zk2.i.g2s.cn:2181,zk3.i.g2s.cn:2181";
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
        System.out.println(zkc);

        final String myip = getMyIp();

        System.out.println("我的ip:"+myip);

        //List<String> children = zkc.getChildren("/");
        //System.out.println(children);


        //zkc.delete("/dubbo/com.zhihuishu.aries.interaction.openapi.InteractionOpenService/configurators/override%3A%2F%2F192.168.70.20%3A20880%2Fcom.zhihuishu.aries.interaction.openapi.InteractionOpenService%3Fcategory%3Dconfigurators%26disabled%3Dtrue%26dynamic%3Dfalse%26enabled%3Dtrue%26group%3Dpc%26version%3D1.1.6");
        //String s = zkc.create("/dubbo/com.zhihuishu.aries.interaction.openapi.InteractionOpenService/configurators/override%3A%2F%2F192.168.70.20%3A20880%2Fcom.zhihuishu.aries.interaction.openapi.InteractionOpenService%3Fcategory%3Dconfigurators%26disabled%3Dtrue%26dynamic%3Dfalse%26enabled%3Dtrue%26group%3Dpc%26version%3D1.1.6", null, CreateMode.PERSISTENT_SEQUENTIAL);
        //System.out.println(s);
        //System.exit(0);

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
                    //System.out.println(RecurUtil.multiSplit(integer,"  ")+path );
                    //System.out.println(RecurUtil.multiSplit(integer,"  ")+path + ":"+pathInfo);

                    if (pathInfo.parentPath.toString().endsWith("configurators")) {
                        //System.out.println(pathInfo.nodeName + ":"+pathInfo);
                        //System.out.println(pathInfo.nodeName + ":"+pathInfo.paths.get(2));
                        //System.out.println(pathInfo.absPath);
                        //String urlEncoded = pathInfo.paths.get(2).toString();
                        String urlEncoded = pathInfo.nodeName.toString();
                        String decode = URLDecoder.decode(urlEncoded);


                        DubboURL dubboURL = DubboURL.valueOf(decode);
                        //System.out.println(dubboURL.getUrlParameter("group")+"/"+dubboURL.getServiceInterface()+":"+dubboURL.getUrlParameter("version"));
                        //System.out.println(dubboURL.getServiceKey());

                        String serviceKey = dubboURL.getServiceKey();
                        if (decode.contains(myip)&& !infs.contains(serviceKey)) {
                            infs.add(serviceKey);

                            System.out.println("接口:"+serviceKey+" 已禁用:"+decode);

                            if (shouldDelete) {
                                System.err.println("    删除:"+pathInfo.absPath);
                                zkc.delete(pathInfo.absPath.toString());
                            }
                        }

                        //infs.add(decode);
                    }
                });


        //infs.forEach(s -> {
        //    System.out.println(s);
        //});
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
