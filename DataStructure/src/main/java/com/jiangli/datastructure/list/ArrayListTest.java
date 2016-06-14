package com.jiangli.datastructure.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 16:39
 */
public class ArrayListTest {
    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    static class Person {
        String firstName;
        String lastName;
        Person() {}
        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    static interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }


    public static void main(String[] args) {
        ArrayListTest test = new ArrayListTest();
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        converter = Integer::valueOf;
         converted = converter.convert("456");
        System.out.println(converted);    // 123

        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
        System.out.println(person.firstName);
        System.out.println(person.lastName);
    }
}
