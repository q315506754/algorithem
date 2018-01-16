package com.jiangli.bytecode.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2018/1/16 11:04
 */
public class NewTest2 {
    public static void main(String[] args) {
        /**
         NEW java/util/HashMap
         DUP
         INVOKESPECIAL java/util/HashMap.<init> ()V
         ASTORE 1
         */
        Map<String, Object> map = new HashMap<>();
    }

}
