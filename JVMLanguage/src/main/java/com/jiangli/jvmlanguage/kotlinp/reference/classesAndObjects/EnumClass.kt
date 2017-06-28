package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/28 15:31
 */

internal enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

/**
 *
 * 匿名类

枚举常量也可以声明自己的匿名类
 */
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

fun main(args: Array<String>) {
    val valueOf = Color.valueOf("RED")
    println(valueOf)


//    println(Color.valueOf("_NO_SUCH"))//java.lang.IllegalArgumentException

//自 Kotlin 1.1 起，
    println(enumValues<Color>())
    println(enumValueOf<ProtocolState>("TALKING"))

    println(Color.BLUE.ordinal)
    println(Color.BLUE.name)
}