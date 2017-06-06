package com.jiangli.jvmlanguage.kotlinp.reference.basics

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/6 16:34
 */
fun main(args: Array<String>) {
    var x = 3
    when {
        x.isOdd() -> print("x is odd")
        x.isEven() -> print("x is even")
        else -> print("x is funny")
    }

    for ((index11, value11) in listOf("aaa","cccc","ddd").withIndex()) {
        println("the element at $index11 is $value11")
    }

    while (x > 0) {
        x--
    }

    do {
        val y = if(x >0) x else null
    } while (y != null) // y is visible here
}

private fun Number.isOdd(): Boolean {
    return this.toInt()%2==1
}
private fun Number.isEven(): Boolean {
    return this.toInt()%2==0
}
