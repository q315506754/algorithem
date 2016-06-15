package com.jiangli.jdk.v1_8;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 15:33
 */
@MulAnno("hint1")
@MulAnno("hint2")
@MulAnno("hint3")
public class Person {
    public int age=-1;
    public String firstName="NO_firstName";
    public String lastName="NO_lastName";

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
