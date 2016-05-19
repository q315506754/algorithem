package com.jiangli.rpc.rmi.server;

import com.jiangli.rpc.rmi.common.IService;
import com.jiangli.rpc.rmi.common.ServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;

public class RMIServer {

    /**
     * 先运行“start rmiregistry”来启动JDK自带的注册表程序，它用于保存Server类注册的远程对象并允许远程客户端的请求访问
     *
     * 注意：这一部非常的关键，必须在你的源代码目录下打开
     *
     *
     * -Djava.rmi.server.codebase=file:/c:/AlgorithemProject/RPC/main/java/
     *
     * 使用本地文件目录时必须使用绝对路径，而且所有的codebase后面都需要有个 /  或 /   来表示这是一个目录，
     * 也可以是一个jar文件。如果去掉它们就会报错。
     *
     * @param args
     */
  public static void main(String[] args) { 
    try { 
      //实例化实现了IService接口的远程服务ServiceImpl对象 
      IService service02 = new ServiceImpl("service02");


        // 也可以直接使用RMI的API进行绑定
        //   Naming.rebind("HelloService1", service1);
//           Naming.rebind("service02", service02);
           Naming.rebind("rmi://localhost:1099/service02", service02);

    } catch (Exception e) {
      e.printStackTrace(); 
    } 
    System.out.println("服务器向命名表注册了1个远程服务对象！"); 
  } 
}