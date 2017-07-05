@file:JvmName("DemoUtils2")

//如果多个文件中生成了相同的 Java 类名（包名相同并且类名相同或者有相同的 @JvmName 注解）通常是错误的。
//然而，编译器能够生成一个单一的 Java 外观类，它具有指定的名称且包含来自所有文件中具有该名称的所有声明。
//要启用生成这样的外观，请在所有相关文件中使用 @JvmMultifileClass 注解。
@file:JvmMultifileClass

package com.jiangli.jvmlanguage.kotlinp.reference.interop.demo2

/**
 *
 *
 * @author Jiangli
 * @date 2017/7/5 16:50
 */

class Foo2

fun bar2() {
    println("bar2...")
}