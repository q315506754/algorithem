package com.jiangli.jvmlanguage.javap;

/**
 * @author Jiangli
 * @date 2017/5/3 14:38
 */
public class HelloWorld {
    private String name;
    public String age;

    public static void main(String[] args) {
        System.out.println("invoked");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}