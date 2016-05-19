package com.jiangli.rpc.rmi.server;

import com.jiangli.rpc.rmi.common.IService;
import com.jiangli.rpc.rmi.common.ServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;

public class JNDIServer {

    /**
     * 先运行“start rmiregistry”来启动JDK自带的注册表程序，它用于保存Server类注册的远程对象并允许远程客户端的请求访问
     *
     * 注意：这一部非常的关键，必须在你的源代码目录下打开
     *
     *
     * -Djava.rmi.server.codebase=file:/c:/AlgorithemProject/RPC/main/java/
     *-Djava.rmi.server.codebase=file:\c:\AlgorithemProject\RPC\target\classes
     *-Djava.rmi.server.codebase=file:\c:\AlgorithemProject\RPC\target\classes\com.jiangli.rpc.rmi.server.JNDIServer
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
        //   Naming.rebind("HelloService2", service2);

        // 使用JNDI的API将远程对象注册
        /**
         * 对JNDI API注册，名称必须以rmi：开头，默认情况下IntialContext的rebind方法会把HelloServieceImpl对
         象注册到本地主机上的监听1099端口的rmiregistry注册表进程中（RMI整合到JNDI中了）
         对直接应用RMI API注册无此限制，不用一rmi：开头就可以实现以上默认
         */

      //初始化命名空间 
      Context namingContext = new InitialContext();
      //将名称绑定到对象,即向命名空间注册已经实例化的远程服务对象 
//      namingContext.rebind("rmi://localhost:1099/service02", service02);
//      namingContext.rebind("rmi://localhost/service02", service02);
      namingContext.rebind("rmi:service02", service02);
//      namingContext.rebind("service02", service02);
    } catch (Exception e) {
      e.printStackTrace(); 
    } 
    System.out.println("服务器向命名表注册了1个远程服务对象！"); 
  } 
}