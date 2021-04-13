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
 * 设置我的接口优先级
 * @author Jiangli
 * @date 2018/12/26 15:42
 */
public class ZkClientSetMyServiceWeightTest {


    public static void main(String[] args) {
        //final boolean shouldDelete = false;
        final boolean shouldDelete = true;
        String CONNECT_ADDR = "zk1.i.g2s.cn:2181,zk2.i.g2s.cn:2181,zk3.i.g2s.cn:2181";
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 10000);
        System.out.println(zkc);

        final String myip = getMyIp();
        //final String myip = "192.168.222.3";

        System.out.println("我的ip:"+myip);
        List<String> serviceKeyWords = Arrays.asList(
                "CompanyCoachOpenService"
                ,"TblUserCompanyTeamTargetOpenService"
                ,"TblUserCompanyTeamOpenService"
                ,"TblGroupOpenService"
                ,"CompanyCourseOpenService"
                ,"TblSpecialTrainingCampBaseOpenService"
                ,"ZhishiCoachOperationService"
                ,"ZhishiCoachBaseOpenService"
                ,"TblContractOpenService"
                ,"TblCompanyCrmOpenService"
                ,"TblCompanyLabelOpenService"
                ,"TblCompanyLabelRelaOpenService"
                ,"TblCommonKvConfigService"
                ,"ZhishiCoachOperationOpenService"
                ,"ICompanyOpenService"
                ,"ZhishiCoachOperationLabelOpenService"
                ,"CompanyInfoOpenService"
                ,"InteractionOpenService"
                ,"LiveTaskOpenServiceForH5"
                ,"JobMenuOpenService"
                ,"JobMenuRoleOpenService"
                ,"RandomRollCallGroupOpenService"
                ,"TblGoodsOpenService"
                ,"TblRoleDictOpenService"
                ,"SaasCompanyOpenService"
                ,"TblReadBookOpenService"
                ,"TblReadBookGiveOpenService"
                ,"TblKeyPersonOpenService"
                ,"TblGroupOpenService"
                ,"UserCourseOpenServiceForManage"
                ,"TblBusiOperLogOpenService"
                ,"UserMenuOpenService"
                ,"CompanyRunRoleOpenService"
                ,"TblUserRoleOpenService"
                ,"ClassGroupOpenService"
                ,"ZhishiOpenPlatformUserOpenService"
                ,"ZhishiOpenDocConfigOpenService"
                ,"ZhishiOpenDocOpenService"
                ,"ZhishiOpenDocParaOpenService"
                ,"TblOperationListOpenService"
                ,"EventModuleOpenService"
                ,"ModuleDetailsOpenService"
                ,"UserOpenService"
                ,"ZhishiOpenPlatformOpenService"
                ,"GroupUserService"
                ,"L1TopicOpenService"
                ,"TblUserSaasCompanyOpenService"
                ,"CompanyRunRoleOpenService"
                ,"TblGroupDepartOpenService"
                ,"TblUserCompanyOpenService"
                ,"CourseWrapCombinationOpenServiceForWebCoach"
                ,"CourseCommunityQAOpenService"
                ,"CompanyMenuOpenService"
                ,"TblCompanyMenuOpenService"
                ,"TblCompanyMenuRoleOpenService"
                ,"TblCompanyRelaMenuOpenService"
                ,"TblCompanyRoleOpenService"
                ,"TblCompanyTypeRelaMenuOpenService"
                ,"TblCompanyUserRoleOpenService"

        );
        System.out.println("serviceKeyWord:"+serviceKeyWords);

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

                    if (pathInfo.parentPath.toString().endsWith("configurators")) {
                        //System.out.println(pathInfo.nodeName + ":"+pathInfo);
                        String nodeName = pathInfo.nodeName.toString();
                        String urlEncoded = pathInfo.nodeName.toString();
                        String decode = URLDecoder.decode(urlEncoded);
                        //convert
                        DubboURL dubboURL = DubboURL.valueOf(decode);
                        //System.out.println(dubboURL.getUrlParameter("group")+"/"+dubboURL.getServiceInterface()+":"+dubboURL.getUrlParameter("version"));
                        //System.out.println(dubboURL.getServiceKey());

                        String serviceKey = dubboURL.getServiceKey();
                        if(nodeName.contains(myip) && nodeName.contains("disabled%3Dtrue")){
                        //if (decode.contains(myip)&& !infs.contains(serviceKey)) {
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

                    if (pathInfo.parentPath.toString().endsWith("providers")) {
                        String nodeName = pathInfo.nodeName.toString();
                        if(nodeName.contains(myip) && serviceKeyWords.stream().anyMatch(it->nodeName.contains(it))){
                            String servicePath = pathInfo.path(0, -2);
                            String mode = "configurators";
                            DubboURL dubboURL = DubboURL.valueOf(URLDecoder.decode(pathInfo.nodeName.toString()));

                            int port = dubboURL.getPort();
                            String serviceKey = dubboURL.getServiceInterface();
                            String prefix = "override://"+myip+":"+port+"/"+serviceKey;

                            Map<String, String> map = new LinkedHashMap<>();
                            String version = dubboURL.getParameter("version");
                            String group = dubboURL.getParameter("default.group");
                            //String ip = dubboURL.getIp();
                            map.put("category", "configurators");
                            map.put("dynamic", "false");
                            map.put("enabled", "true");
                            map.put("group", group);
                            map.put("version", version);
                            map.put("weight", "99999999");
                            String s = prefix + "?" +  RecurUtil.buildQueryString(map);
                            String overridUrl = RecurUtil.buildPath(servicePath, mode, URLEncoder.encode(s));
                            //System.out.println(overridUrl);
                            if(!zkc.exists(overridUrl)){
                                zkc.createPersistent(overridUrl);
                                System.out.println("created... "+overridUrl);
                            } else {
                                System.out.println("exist... "+overridUrl);
                            }
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
