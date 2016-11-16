package com.jiangli.jdk.v1_8;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 16:55
 */
public class CollectorsTest {
    public static void main(String[] args) {
        Collector<String, ?, ArrayList<String>> stringArrayListCollector = Collectors.toCollection(ArrayList<String>::new);

        Collector<Object, ?, List<Object>> objectListCollector = Collectors.toList();
        Collector<String, ?, List<String>> stringListCollector = Collectors.<String>toList();
        Collector<String, ?, List<String>> stringListCollector2 = Collectors.toList();
//        Collector<String, List<String>, List<String>> stringListCollector3 = Collectors.toList();


        Collector<String, ArrayList<String>, ArrayList<String>> asd = toCollection(ArrayList<String>::new);

        Function<Object, Object> objectObjectFunction = castingIdentity();
        Function<String, String> objectObjectFunction2 = castingIdentity();



    }

    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Function<A,R> finisher,
                      Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    public static <T, C extends Collection<T>>
    Collector<T, C, C> toCollection(Supplier<C> collectionFactory) {
        return new CollectorImpl<>(collectionFactory, Collection<T>::add,
                (r1, r2) -> { r1.addAll(r2); return r1; },
                CH_ID);
    }

    static final Set<Collector.Characteristics> CH_ID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

}
