package com.jiangli.jdk.v1_8;

import java.util.function.Consumer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 15:33
 */
public class ConsumerTest {
    public static void main(String[] args) {
        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
        greeter.accept(new Person("Luke", "Skywalker"));
        greeter.accept(new Person("Luke2", "Skywalker2"));
    }
}
