package com.jiangli.jdk.v1_8;

import java.util.function.Function;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 10:24
 */
public class FunctionTest {
    public static void main(String[] args) {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        String apply = backToString.apply("123");// "123"
        System.out.println(apply);

        Function<String, Integer> compose = toInteger.compose(backToString);
        System.out.println(compose.apply("123"));

        Function<String, String> identity = Function.<String>identity();
        System.out.println(identity.apply("dsdsgfdgf"));

        Function<Person, String> func = Person::toString;
        System.out.println(func.apply(new Person()));
    }

}
