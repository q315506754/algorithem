package com.jiangli.db.model;

/**
 * Created by Jiangli on 2016/7/14.
 */
public class Student {
    private int age;
    private String name;
    private String className;

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
