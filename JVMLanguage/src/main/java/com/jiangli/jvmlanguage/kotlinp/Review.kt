package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2018/11/14 16:05
 */

fun main(args: Array<String>) {

    abstract class AbsP{
        abstract fun x(x: Any): Unit
        abstract fun y(x: Any): Unit
    }

    val child  = object : AbsP() {
        override fun x(x: Any) {
        }

        override fun y(x: Any) {
        }
    }

    fun aa(x: Any,p:()->Unit?={  }): Unit {
        fun xx(x: Any, y: Any): Unit {

        }

        val xx2:(String)->Unit = {s: String ->  }

        fun xx3(x: Int, y: Int): Int  = x +y

        fun <T> T.apply(block: T.() -> Unit): T { block(); return this }
        HashMap<Any,Any>().apply({this.put("xx","yy")})
        HashMap<Any,Any>().apply{ this.put("xx","yy") }

    }
}