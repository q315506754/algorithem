package com.jiangli.jdk.v1_8;

import java.util.function.Supplier;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 12:05
 */
public class SupplierTest {
    public static void main(String[] args) {
        Supplier<Person> personSupplier = Person::new;
        Person s = personSupplier.get();// new Person
        System.out.println(s);
    }

}
