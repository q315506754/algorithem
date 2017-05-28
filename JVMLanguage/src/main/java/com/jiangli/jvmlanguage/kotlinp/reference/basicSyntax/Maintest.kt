package com.jiangli.jvmlanguage.kotlinp.reference.basicSyntax

/**
 * Created by Jiangli on 2017/5/28.
 */

fun main(args: Array<String>) {
    val x = 9 downTo 0
    println(x)
    println(x.javaClass)

    for (x in 9 downTo 0 step 3) {
        println(x)
    }
}