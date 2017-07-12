package com.jiangli.jmx.mbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2017/7/11 17:03
 */
public class AgentMain {

    public static void main(String[] args)throws Exception {
        System.out.println(Arrays.toString(args));
        //通过工厂类获取MBeanServer，用来做MBean的容器 。
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();

//        第14行中的ObjectName中的取名是有一定规范的，格式为：“域名：name=MBean名称”，
// 其中域名和MBean的名称可以任意取。这样定义后，就可以唯一标识我们定义的这个MBean的实现类了。
          ObjectName helloName = new ObjectName("jmxBeanaaaa:name=hello");//文件夹jmxBeanaaaa  生成bean ——hello
          //create mbean and register mbean
          server.registerMBean(new Hello(), helloName);
          Thread.sleep(60*60*1000);

        //http://www.cnblogs.com/dongguacai/p/5900507.html

        //通过JMX提供的工具页访问
//        这里，我们复用上面的接口和实现类，只需要改动适配层，这里需要到导入外部jar包jdmk  com.sun.jdmk-jmxtools-1.2.1
//        ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
//        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
//        server.registerMBean(adapter, adapterName);
//        adapter.start();

        //rmi

        //remote jmx

//        主要是在tomcat下的文件catalina.sh中进行一些环境变量的配置配置：
//        -Dcom.sun.management.jmxremote=true                 相关 JMX 代理侦听开关
//                -Djava.rmi.server.hostname                                     服务器端的IP
//                -Dcom.sun.management.jmxremote.port=29094             相关 JMX 代理侦听请求的端口
//
//                -Dcom.sun.management.jmxremote.ssl=false              指定是否使用 SSL 通讯
//
//                -Dcom.sun.management.jmxremote.authenticate=false     指定是否需要密码验证


//        http://www.cnblogs.com/zhongkaiuu/p/debug_online.html
        //远程调试
//        首先被debug程序的虚拟机在启动时要开启debug模式，启动debug监听程序。jdwp是Java Debug Wire Protocol的缩写。
//        java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n zhc_application

//        这是jdk1.7版本之前的方法，1.7之后可以这样用：
//        java -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n zhc_application

//        -XDebug 启用调试；
//        -Xrunjdwp 加载JDWP的JPDA参考执行实例；
//        transport 用于在调试程序和VM使用的进程之间通讯；
//        dt_socket 套接字传输；
//        server=y/n VM是否需要作为调试服务器执行；
//        address=7899 调试服务器监听的端口号；
//        suspend=y/n 是否在调试客户端建立连接之后启动 VM
    }

}
