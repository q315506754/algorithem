package com.jiangli.jvmtest;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.context.support.StaticApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2019/5/24 9:22
 */
public class OOMMethodArea_ClassInfoTest {
    /**
     * -XX:PermSize=10m -XX:MaxPermSize=10m -verbose:gc
     *
     *  jdk8要用下面的 试了很久
     * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
     * @param args
     */
    public static void main(String[] args) {
        List objects = new ArrayList<>();
        int i = 0;
        while (true) {
            //Object o = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{List.class}, (proxy, method, args1) -> {
            //    return proxy;
            //});
            //
            ////都为同一个代理类
            ////class com.sun.proxy.$Proxy0
            //System.out.println(o.getClass());
            ////objects.add(String.valueOf("test:"+i++).intern());
            //objects.add(o);

            Enhancer enhancer = new Enhancer();
            //Object方法较少，建议选个方法多的作为父类
            //enhancer.setSuperclass(Object.class);
            enhancer.setSuperclass(StaticApplicationContext.class);

            //重要 这样才不会生成同一个class
            enhancer.setUseCache(false);

            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return null;
                }
            });

            //非堆的metaspace 涨得特别快
            Object o = enhancer.create();
            System.out.println(o.getClass());

            //java.lang.OutOfMemoryError: Metaspace

            //objects.add(o);
        }
    }

}
