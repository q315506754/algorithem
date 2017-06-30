package com.jiangli.jvmlanguage.kotlinp.reference.other.Exceptions.kt

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 11:14
 */
//Try 是一个表达式
//
//try 是一个表达式，即它可以有一个返回值。

val a: Int? = try {
    val input = "adsdsd"
    parseInt(input) } catch (e: NumberFormatException) { null }

fun parseInt(input: Any?): Int? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}


/**
 * Nothing 类型
**/

//在 Kotlin 中 throw 是表达式，所以你可以使用它（比如）作为 Elvis 表达式的一部分：
//
//val s = person.name ?: throw IllegalArgumentException("Name required")
//throw 表达式的类型是特殊类型 Nothing。 该类型没有值，而是用于标记永远不能达到的代码位置。 在你自己的代码中，你可以使用 Nothing 来标记一个永远不会返回的函数：

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}
//当你调用该函数时，编译器会知道执行不会超出该调用：

//val s = person.name ?: fail("Name required")
//println(s)     // 在此已知“s”已初始化