package com.jiangli.bytecode.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2018/1/16 10:52
 */
public class HashMapTest2 {
    public static void main(String[] args) {
        /**
         *
         NEW java/util/HashMap  	create new object of type identified by class reference in constant pool index (indexbyte1 << 8 + indexbyte2)
         DUP    	duplicate the value on top of the stack
         INVOKESPECIAL java/util/HashMap.<init> ()V invoke instance method on object objectref, where the method is identified by method reference indexin constant pool (indexbyte1 << 8 + indexbyte2)
         ASTORE 1   	store a reference into a local variable #index
         ALOAD 1    	load a reference onto the stack from a local variable #index
         LDC "ddd"  push a constant #index from a constant pool (String, int or float) onto the stack
         LDC "xxx"
         INVOKEINTERFACE java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; invokes an interface method on object objectref, where the interface method is identified by method reference index in constant pool (indexbyte1 << 8 + indexbyte2)
         POP    discard the top value on the stack
         ALOAD 1
         LDC "ddd"
         INVOKEINTERFACE java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object;
         POP
         */
        Map<String,String> map = new HashMap<String,String>();
        map.put("ddd","xxx");
        map.get("ddd");
    }

}
