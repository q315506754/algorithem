package com.jiangli.jdk.v1_8;

import java.util.Comparator;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 15:40
 */
public class ComparatorTest {
    public static void main(String[] args) {
        Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        comparator.compare(p1, p2);             // > 0
        comparator.reversed().compare(p1, p2);  // < 0
    }

}
