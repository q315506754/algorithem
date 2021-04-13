package com.jiangli.common.utils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Jiangli
 * @date 2021/1/28 11:11
 */
public class MyComparator {
    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, ? extends U> keyExtractor)
    {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable)
                (c1, c2) -> {
                    U a1 = keyExtractor.apply(c1);
                    U a2 = keyExtractor.apply(c2);
                    if (a1 != null  && a2 ==null) {
                        return 1;
                    }else if (a1 == null  && a2 ==null) {
                        return 0;
                    } else if (a1 == null  && a2 !=null) {
                        return -1;
                    } else {
                        return a1.compareTo(a2);
                    }
                };
    }
    public static <T> Comparator<T> comparings(
            Function<? super T, ? extends Comparable>... keyExtractor)
    {
        Objects.requireNonNull(keyExtractor);

        return (Comparator<T> & Serializable)
                (c1, c2) -> {

                    for (Function<? super T, ? extends Comparable> function : keyExtractor) {
                        Comparable a1 = function.apply(c1);
                        Comparable a2 = function.apply(c2);
                        if (a1 != null  && a2 ==null) {
                            return 1;
                        }else if (a1 == null  && a2 ==null) {
                            return 0;
                        } else if (a1 == null  && a2 !=null) {
                            return -1;
                        } else {
                            int i = a1.compareTo(a2);
                            if (i != 0 ) {
                                return i;
                            }
                        }
                    }

                   return 0;
                };
    }

}
