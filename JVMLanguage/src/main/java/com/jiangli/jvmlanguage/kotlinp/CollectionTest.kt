package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/22 17:32
 */
fun main(args: Array<String>) {
    println(emptyArray<String>())
    println(emptyList<String>())
    println(listOf(1, 2, "3"))
    println(arrayListOf(1,"2","3"))

    println(hashMapOf("a" to "b"))
    println(hashMapOf<String,String>())
//    println(hashMapOf()) //compile error
}