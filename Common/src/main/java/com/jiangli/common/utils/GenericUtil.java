package com.jiangli.common.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * 泛型工具
 *
 * @author Jiangli
 * @date 2018/4/24 14:13
 */
public class GenericUtil {
    public static <DTO,ID1,ID2> Map<ID1,List<ID2>> group(List<DTO> list, Function<DTO,ID1> key, Function<DTO,ID2> value){
        if (list != null) {
            return list.stream().collect(Collectors.groupingBy(key, Collectors.mapping(value, Collectors.toList())));
        }
        return new HashMap<>();
    }

    public static  <DTO,ID1> List<ID1> list(List<DTO> list, Function<DTO,ID1> key){
        if (list != null) {
            return list.stream().collect(Collectors.mapping(key, Collectors.toList()));
        }
        return new ArrayList<>();
    }

    public static  <DTO,ID1> List<ID1> listSet(List<DTO> list, Function<DTO,ID1> key){
        return new ArrayList<>(set(list, key));
    }

    public static  <DTO,ID1> Set<ID1> set(List<DTO> list, Function<DTO,ID1> key){
        if (list != null) {
            return list.stream().collect(Collectors.mapping(key, Collectors.toSet()));
        }
        return new HashSet<>();
    }

    public static  <DTO> List<DTO> listMerge(List<DTO>... lists){
        if (lists != null) {
            Set<DTO> set = new HashSet<>();
            for (List<DTO> list : lists) {
                set.addAll(list);
            }
            return new ArrayList<>(set);
        }
        return new ArrayList<>();
    }

}
