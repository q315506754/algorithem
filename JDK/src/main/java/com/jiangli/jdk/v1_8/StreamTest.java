package com.jiangli.jdk.v1_8;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 16:24
 */
public class StreamTest {
    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        Stream<String> stream = stringCollection.stream();
        System.out.println(stream);

        stream.filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

        System.out.println("------------sort-------------");
        stringCollection.stream().sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        System.out.println(stringCollection);//not affected

        System.out.println("------------map-------------");
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        System.out.println("------------match-------------");
        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true
        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false
        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true


        System.out.println("------------count-------------");
        long startsWithB =
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b"))
                        .count();
        System.out.println(startsWithB);    // 3

        System.out.println("------------reduce-------------");
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);


//        Set<String> collect = stringCollection.stream().collect(Collectors.toSet());
        Collector<Object, ?, TreeSet<Object>> objectTreeSetCollector = Collectors.toCollection(TreeSet::new);

        //final oper
        //foreach, match, count,reduce

        //middle oper
        //filter,sorted,map,


        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        //sequential sort
        sequentialSort(values);

        //parallel sort
        parallelSort(values);
    }

    public static void parallelSort(List<String> values) {
        long t0 = System.nanoTime();
        long count = values.parallelStream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }

    public static void sequentialSort(List<String> values) {
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    }

}
