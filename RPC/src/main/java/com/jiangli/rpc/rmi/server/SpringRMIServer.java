package com.jiangli.rpc.rmi.server;

import com.jiangli.rpc.rmi.common.IService;
import com.jiangli.rpc.rmi.common.ServiceImpl;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.Naming;
import java.rmi.registry.Registry;

public class SpringRMIServer {

    /**
     * @param args
     */
  public static void main(String[] args) { 
    try {
        IService service02 = new ServiceImpl("service02");

        int port=1098;
//        int port=1099;

        final RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setRegistryPort(port);
        rmiServiceExporter.setServiceName("service02");
        rmiServiceExporter.setServiceInterface(IService.class);
        rmiServiceExporter.setService(service02);
        rmiServiceExporter.afterPropertiesSet();
    } catch (Exception e) {
      e.printStackTrace(); 
    } 
    System.out.println("服务器向命名表注册了1个远程服务对象！"); 
  } 
}