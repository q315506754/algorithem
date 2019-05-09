package com.jiangli.jmx.impl;

public class HelloWorld implements HelloWorldMBean {
    private String greeting;

    public HelloWorld(String greeting) {
        this.greeting = greeting;
    }
    public HelloWorld() {
        this.greeting = "hello world!";
    }
    public String getGreeting() {
        System.out.println("getGreeting");
        return greeting;
    }
    public void setGreeting(String greeting) {
        this.greeting = greeting;
        System.out.println("setGreeting:"+greeting);
    }
    public void printGreeting() {
        System.out.println(greeting);
    }
}