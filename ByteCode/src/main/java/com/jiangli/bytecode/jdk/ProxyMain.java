package com.jiangli.bytecode.jdk;

import com.jiangli.common.core.HelloImpl;
import com.jiangli.common.core.IHello;

import java.lang.reflect.Proxy;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 9:46
 */
public class ProxyMain {

    public static void main(String[] args) {
        HelloImpl helloImpl = new HelloImpl();
        IHello o = (IHello) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class[]{IHello.class}, new JDKProxyHandler(helloImpl));

        System.out.println(o.getClass());
        System.out.println(o.sayHello("Jim"));

    }
}
