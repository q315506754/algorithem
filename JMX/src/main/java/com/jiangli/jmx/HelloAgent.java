package com.jiangli.jmx;

import com.jiangli.jmx.impl.HelloWorld;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

public class HelloAgent  {
    private MBeanServer mbs;

    public static void main(String args[]) throws Exception {
        // 首先建立一个MBeanServer，MBeanServer用来管理我们的MBean，通常是通过MBeanServer来获取我们MBean的信息
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

        String domainName = "MyMBean";

        // 为MBean（下面的new Hello()）创建ObjectName实例
        //ObjectName helloName = new ObjectName(domainName+":name=HelloWorldabc");
        //ObjectName helloName = new ObjectName(domainName+":type=HelloWorldabc");
        ObjectName helloName = new ObjectName("agentBeans:xxxx=HelloWorldabc");

        // 将new Hello()这个对象注册到MBeanServer上去
        mbeanServer.registerMBean(new HelloWorld(),helloName);

        LockSupport.park();

        /**
         * JMXConnectorServer service
         */
        //这句话非常重要，不能缺少！注册一个端口，绑定url后，客户端就可以使用rmi通过url方式来连接JMXConnectorServer
        Registry registry = LocateRegistry.createRegistry(1099);
        System.out.println(registry);
        System.out.println(Arrays.toString(registry.list()));


        //构造JMXServiceURL


        //localhost:1099
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
        System.out.println(jmxServiceURL);

        //创建JMXConnectorServer
        JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, mbeanServer);
        System.out.println(cs);

        new Thread(()->{
            try {
                Thread.sleep(3000L);
                //[jmxrmi]
                System.out.println(Arrays.toString(registry.list()));
                System.out.println(registry.lookup("jmxrmi"));
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }).start();

        //启动 此时没绑定端口就会报错
        cs.start();
    }
}