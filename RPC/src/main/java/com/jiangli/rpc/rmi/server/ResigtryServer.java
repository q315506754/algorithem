package com.jiangli.rpc.rmi.server;

import com.jiangli.rpc.rmi.common.IService;
import com.jiangli.rpc.rmi.common.ServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ResigtryServer {

    /**
     * @param args
     */
  public static void main(String[] args) {
    try { 
      //实例化实现了IService接口的远程服务ServiceImpl对象 
      IService service02 = new ServiceImpl("service02");


        Registry registry = null;
        int port=1098;
        try {
            registry = LocateRegistry.getRegistry(port); //如果该端口未被注册，则抛异常
            registry.list(); //拿到该端口注册的rmi对象
        } catch (final Exception e) {
            e.printStackTrace();
            try {
                registry = LocateRegistry.createRegistry(port);//捕获异常，端口注册
            } catch (final Exception ee) {
                ee.printStackTrace();
            }
        }

      //将名称绑定到对象,即向命名空间注册已经实例化的远程服务对象
//      registry.rebind("rmi://localhost:1099/service02", service02);
//      registry.rebind("rmi://localhost:1098/service02", service02);
//      registry.rebind("rmi://localhost/service02", service02);
//        registry.rebind("rmi:service02", service02);
      registry.rebind("service02", service02);
    } catch (Exception e) {
      e.printStackTrace(); 
    } 
    System.out.println("服务器向命名表注册了1个远程服务对象！"); 
  } 
}