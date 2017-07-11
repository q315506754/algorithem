package com.jiangli.jmx.mbean;

/**
 * 该类名称必须与实现的接口的前缀保持一致（即MBean前面的名称
 *
 * @author Jiangli
 * @date 2017/7/11 17:03
 */
public class Hello implements HelloMBean{
    private String name;
    private String age;

    @Override
    public String getName() {
        System.out.println("getName:"+name);
        return name;
    }

    @Override
    public void setName(String name) {
        System.out.println("setName:"+name);
        this.name = name;
    }

    @Override
    public String getAge() {
        System.out.println("getAge:"+age);
        return age;
    }

    @Override
    public void setAge(String age) {
        System.out.println("setAge:"+age);
        this.age = age;
    }

    @Override
    public void helloWorld() {
        System.out.println("helloWorld");
    }

    @Override
    public void helloWorld(String str) {
        System.out.println("hello ~"+str);
    }

    @Override
    public void getTelephone() {
        System.out.println("tele ~"+Math.random());
    }
}
