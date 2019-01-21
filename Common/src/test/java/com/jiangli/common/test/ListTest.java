package com.jiangli.common.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/17 0017 10:33
 */
public class ListTest {
    public static void main(String[] args) {
//        List list22 = new ArrayList();
        List list22 = new LinkedList();
        //System.out.println(list22.get(0));

        String sss = "12321sdgf";
        String sss2 = new String("12321sdgf");
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("3434");
        list.add("123");
        list.add("aaa");

        System.out.println(list);

        List<String> subList = list.subList(1, 3);
        List<String> subList2 = list.subList(1, 3);
        List<String> subListOfsubList = subList.subList(1, 2);
        System.out.println(subList);

//        list.add(1,"aaaaaaaaaaaaaa");//wrong
//        subList.add(1,"aaaaaaaaaaaaaa");
        subListOfsubList.add(0,"aaaaaaaaaaaaaa");
        System.out.println(list);
        System.out.println(subList);//
        System.out.println(subListOfsubList);//
//        System.out.println(subList2);//wrong
        System.out.println(subList.get(0));//



        String[] params = new String[]{"123","456","789"};
        List<String> arrList = Arrays.asList(params);
        System.out.println(arrList);
        System.out.println(arrList.getClass());
        System.out.println("before:"+Arrays.toString(params));


//        arrList.add("012");//wrong
//        System.out.println(arrList);
//        System.out.println("after:"+Arrays.toString(params));

        arrList.set(2, "modified");
        System.out.println(arrList);
        System.out.println("after:"+Arrays.toString(params));
        //List<String> strings = Arrays.asList("aaa", "bbb");
        //List<String> strings = new ArrayList<>();
        List<String> strings = new LinkedList<>();
        strings.add("aaaa");
        //strings.add("bbbb");
        //strings.add(0,"ccc");
        strings.add(1,"ddd");
        //strings.add(2,"eee");
        System.out.println(strings);

        //strings = strings.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //strings=strings.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        System.out.println(strings);

    }

}
