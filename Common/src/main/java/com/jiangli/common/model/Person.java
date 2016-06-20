package com.jiangli.common.model;

import net.sf.json.JSONObject;

public class Person {
    protected int age;
    protected String name;
    protected String state;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}