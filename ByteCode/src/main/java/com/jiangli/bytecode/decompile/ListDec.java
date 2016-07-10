package com.jiangli.bytecode.decompile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ListDec {
    /*
    NEW java/util/ArrayList
    DUP
    INVOKESPECIAL java/util/ArrayList.<init> ()V
    ASTORE 1

    ALOAD 1
    LDC "111"
    INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z
    POP

 ALOAD 1
    LDC "222"
    INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z
    POP

     ALOAD 1
    INVOKEINTERFACE java/util/List.iterator ()Ljava/util/Iterator;
    ASTORE 2

    FRAME APPEND [java/util/List java/util/Iterator]
    ALOAD 2
    INVOKEINTERFACE java/util/Iterator.hasNext ()Z
    IFEQ L5
    ALOAD 2
    INVOKEINTERFACE java/util/Iterator.next ()Ljava/lang/Object;
    CHECKCAST java/lang/String
    ASTORE 3

     GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    ALOAD 3
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V

    GOTO L4

      FRAME CHOP 1
    RETURN

     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");

        for (String s : list) {
            System.out.println(s);
        }
    }
}
