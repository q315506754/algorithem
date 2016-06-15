package com.jiangli.jdk.v1_8;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 10:14
 */
public class PredicateTest {

    public static void main(String[] args) {
        Predicate<String> predicate = (s) -> s.length() > 0;
        print(predicate);
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        print(isEmpty);
        Predicate<String> isNotEmpty = isEmpty.negate();
        print(isNotEmpty);
    }

    public static void print(Predicate predicate) {
        System.out.println(predicate);// true
        System.out.println(predicate.test("true"));// true
        System.out.println(predicate.negate().test("false"));// false
    }

}
