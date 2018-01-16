package com.jiangli.bytecode.common;

import java.util.HashMap;

/**
 * @author Jiangli
 * @date 2018/1/16 11:04
 */
public class NewTest {
    public static void main(String[] args) {
        /**
         NEW java/util/HashMap
         DUP
         INVOKESPECIAL java/util/HashMap.<init> ()V
         POP
         */
        new HashMap<String, Object>();
    }

}
