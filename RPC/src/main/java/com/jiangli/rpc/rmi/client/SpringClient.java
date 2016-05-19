package com.jiangli.rpc.rmi.client;

import com.jiangli.rpc.rmi.common.IService;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import javax.naming.Context;
import javax.naming.InitialContext;

public class SpringClient {

    private static  String rmiServerIP = "127.0.0.1";
    private static String rmiServerPort = "1098";
    private  static String serverName = "service02";


    public static void main(String[] args) {
        try {
            RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
            rmiProxyFactoryBean.setRefreshStubOnConnectFailure(true);
            String rmiUrl = "rmi://" + rmiServerIP + ":" + rmiServerPort + "/" + serverName;
            rmiProxyFactoryBean.setServiceInterface(IService.class);
            rmiProxyFactoryBean.setServiceUrl(rmiUrl);
            rmiProxyFactoryBean.afterPropertiesSet();

            Object object = rmiProxyFactoryBean.getObject();
            System.out.println(object);

            IService iService = (IService) object;
            System.out.println(iService.service("spring,你好！"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}