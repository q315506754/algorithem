package com.jiangli.jmx.impl;

public interface HelloWorldMBean {
    String getGreeting();
    void setGreeting(String greeting);
    void printGreeting();
}