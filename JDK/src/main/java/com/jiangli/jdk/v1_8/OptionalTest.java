package com.jiangli.jdk.v1_8;

import java.util.Optional;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 15:43
 */
public class OptionalTest {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("bam");
        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"

//        Optional.of(null);
        Optional<Person> person = Optional.of(new Person());
        System.out.println(person.get());

        Optional<Object> o = Optional.ofNullable(null);
        System.out.println(o.orElse("sdfsdf"));

        Optional<Person> o2 = Optional.ofNullable(null);
        System.out.println(o2.orElse(new Person()));

//        o.ifPresent(o1 -> {
        person.ifPresent(o1 -> {
            System.out.println("ifPresent true");
        });

        Object supplier = o.orElseGet(() -> new Person("supplier", "supplier~"));
//        Person supplier = person.orElseGet(() -> new Person("supplier", "supplier~"));
        System.out.println(supplier);

        Optional<Object> empty = Optional.empty();
        System.out.println(empty);
    }

}
