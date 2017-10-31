package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/10/31 17:34
 */

fun main(args: Array<String>) {
    fun func(i:Int):Int{
        if (i<=1) return 5
        else if (i>5) return func(5)
        return 2 * func(i-1)
    }

    (0..10).forEach {
        println("func($it) -> ${func(it)}")
    }
}