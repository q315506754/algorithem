package com.jiangli.jvmlanguage.kotlinp.reference.interop;

import com.jiangli.jvmlanguage.kotlinp.reference.interop.demo.*;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;

import java.io.IOException;

/**
 * @author Jiangli
 * @date 2017/7/5 16:52
 */
public class CallKotlinInJava {
    public static String getID(C c) {
        return c.ID;
    }

    public static void main(String[] args) {
        new com.jiangli.jvmlanguage.kotlinp.reference.interop.demo.Foo();
        com.jiangli.jvmlanguage.kotlinp.reference.interop.demo.KotlinForJavaKt.bar();


        //@file:JvmName("DemoUtils2")
        //@JvmName 注解修改生成的 Java 类的类名：
        new com.jiangli.jvmlanguage.kotlinp.reference.interop.demo2.Foo2();
        com.jiangli.jvmlanguage.kotlinp.reference.interop.demo2.DemoUtils2.bar2();

        System.out.println(// Java
                Key.COMPARATOR);

        Singleton.provider = new Provider();

        System.out.println(KotlinForJavaKt.MAX);

        D.foo(); // 没问题
//        D.bar(); // 错误：不是一个静态方法
        D.Companion.foo(); // 保留实例方法
        D.Companion.bar(); // 唯一的工作方式

        Obj.foo(); // 没问题
//        Obj.bar(); // 错误
        Obj.INSTANCE.bar(); // 没问题，通过单例实例调用
        Obj.INSTANCE.foo(); // 也没问题

        KClass<Obj> kotlinClass = JvmClassMappingKt.getKotlinClass(Obj.class);
        System.out.println(kotlinClass);

        //overloads
        KotlinForJavaKt.f("a",3,"c");
        KotlinForJavaKt.f3("a");

        try {
            KotlinForJavaKt.f4();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
