package com.jiangli.jvmlanguage.kotlinp;

/**
 * Here we have a class with a primary constructor and a member function.
 * Note that there's no `new` keyword used to create an object.
 * See http://kotlinlang.org/docs/reference/classes.html#classes
 */

//public  not final
open class Base(p: Int) {}

//函数必须加上override标注才能重写父类方法
class Derived(p: Int) : Base(p) {}

class Greeter(val name: String?="unkown") {
    fun greet() {
        println("Hello, ${name}");
    }

    //constructor
    init {
        println("new Greeter")
    }
}

fun main(args: Array<String>) {
    Greeter(if (args.size == 0)
        "bill"
    else args[0]).greet()

    println(test1(123))

    val s:String? = null
//    val s:String? = "123"
    println(s?.length)
    println(s?.length?.equals(3))

//    if (s?.length?.equals(3)) {
//    }
}

fun test1(x: Any): Int {
    listOf(1,2,3).map {y->
        println(y)
        y
    }.forEach { x->
        println(x)
        if(x%2==0) return x
    }
    return -1
}