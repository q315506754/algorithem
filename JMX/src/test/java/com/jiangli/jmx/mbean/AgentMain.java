package com.jiangli.jmx.mbean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author Jiangli
 * @date 2017/7/11 17:03
 */
public class AgentMain {

    public static void main(String[] args)throws Exception {
        //通过工厂类获取MBeanServer，用来做MBean的容器 。
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();

//        第14行中的ObjectName中的取名是有一定规范的，格式为：“域名：name=MBean名称”，
// 其中域名和MBean的名称可以任意取。这样定义后，就可以唯一标识我们定义的这个MBean的实现类了。
          ObjectName helloName = new ObjectName("jmxBeanaaaa:name=hello");//文件夹jmxBeanaaaa  生成bean ——hello
          //create mbean and register mbean
          server.registerMBean(new Hello(), helloName);
          Thread.sleep(60*60*1000);
    }

}
