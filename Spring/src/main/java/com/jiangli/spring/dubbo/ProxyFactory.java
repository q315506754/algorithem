package com.jiangli.spring.dubbo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler {
    private Class interfaceClass;

    public ProxyFactory(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    //返回代理对象,此处用泛型为了调用时不用强转,用Object需要强转
    public <T> T getProxyObject() {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),//类加载器
                new Class[]{interfaceClass},//为哪些接口做代理(拦截哪些方法)
                this);//(把这些方法拦截到哪处理)
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method);
        System.out.println("进行编码");
        System.out.println("发送网络请求");
        System.out.println("将网络请求结果进行解码并返回");
        return null;
    }

    public static void main(String[] args) {
        ProxyFactory proxyFactor = new ProxyFactory(MenuService.class);
        MenuService menuService = proxyFactor.getProxyObject();
        menuService.sayHello();
        //输出结果如下:
        //public abstract void com.toby.rpc.MenuService.sayHello()
        //进行编码
        //发送网络请求
        //将网络请求结果进行解码并返回

    }
}