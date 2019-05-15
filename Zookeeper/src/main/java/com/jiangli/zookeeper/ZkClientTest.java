package com.jiangli.zookeeper;

import com.jiangli.common.utils.RecurUtil;
import com.jiangli.zookeeper.utils.DubboURL;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author Jiangli
 * @date 2018/12/26 15:42
 */
public class ZkClientTest {


    public static void main(String[] args) {
        final boolean shouldDelete = false;
        //final boolean shouldDelete = true;
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
                    //System.out.println(pathInfo.absPath);

                    //if (pathInfo.parentPath.toString().endsWith("providers")) {
                    //    String urlEncoded = pathInfo.nodeName.toString();
                    //    String decode = URLDecoder.decode(urlEncoded);
                    //    DubboURL dubboURL = DubboURL.valueOf(decode);
                    //
                    //    if (decode.contains(myip)) {
                    //        System.out.println(pathInfo.absPath);
                    //        System.out.println(dubboURL);
                    //        //System.out.println(dubboURL.toServiceString());
                    //        //System.out.println(dubboURL.toFullString());
                    //        System.out.println(dubboURL.toIdentityString());
                    //        System.out.println(dubboURL.toParameterString());
                    //    }
                    //}

                    //if (pathInfo.absPath.toString().contains("DeliCustomerRelaCompanyOpenService")) {
                    //    //System.out.println(pathInfo.absPath);
                    //}

                    if (pathInfo.parentPath.toString().endsWith("providers")) {
                        String nodeName = pathInfo.nodeName.toString();
                        if(nodeName.contains(myip)){
                            //System.out.println(pathInfo.absPath);
                            //System.out.println(pathInfo.parentPath);

                            String servicePath = pathInfo.path(0, -2);
                            String mode = "configurators";
                            DubboURL dubboURL = DubboURL.valueOf(URLDecoder.decode(pathInfo.nodeName.toString()));

                            int port = dubboURL.getPort();
                            String serviceKey = dubboURL.getServiceInterface();
                            String prefix = "override://"+myip+":"+port+"/"+serviceKey;

                            Map<String, String> map = new LinkedHashMap<>();
                            String version = dubboURL.getParameter("default.version");
                            String group = dubboURL.getParameter("default.group");
                            //String ip = dubboURL.getIp();
                            map.put("category", "configurators");
                            map.put("dynamic", "false");
                            map.put("enabled", "true");
                            map.put("group", group);
                            map.put("version", version);
                            map.put("weight", "999999");

                            String s = prefix + "?" +  RecurUtil.buildQueryString(map);
                            String overridUrl = RecurUtil.buildPath(servicePath, mode, URLEncoder.encode(s));

                            System.out.println(overridUrl);
                            //System.out.println(prefix);
                        }
                        //System.out.println(pathInfo.absPath);

                    }

                    if (pathInfo.parentPath.toString().endsWith("configurators")) {
                        //System.out.println(pathInfo.nodeName + ":"+pathInfo);
                        //System.out.println(pathInfo.nodeName + ":"+pathInfo.paths.get(2));
                        //System.out.println(pathInfo.absPath);
                        //String urlEncoded = pathInfo.paths.get(2).toString();
                        String urlEncoded = pathInfo.nodeName.toString();
                        String decode = URLDecoder.decode(urlEncoded);


                        //convert
                        DubboURL dubboURL = DubboURL.valueOf(decode);
                        //System.out.println(dubboURL.getUrlParameter("group")+"/"+dubboURL.getServiceInterface()+":"+dubboURL.getUrlParameter("version"));
                        //System.out.println(dubboURL.getServiceKey());

                        String serviceKey = dubboURL.getServiceKey();
                        if (decode.contains(myip)&& !infs.contains(serviceKey)) {
                            //System.out.println(decode);

                            //  /dubbo/com.zhihuishu.aries.run.openapi.manage.ClassGradeOpenService/configurators/override%3A%2F%2F192.168.60.49%3A20882%2Fcom.zhihuishu.aries.run.openapi.manage.ClassGradeOpenService%3Fcategory%3Dconfigurators%26dynamic%3Dfalse%26enabled%3Dtrue%26group%3Dpc%26version%3D1.0.0%26weight%3D999200
                            //System.out.println(pathInfo.absPath);

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
