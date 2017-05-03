package com.jiangli.jvmlanguage.kotlinp;

/**
 * Here we have a class with a primary constructor and a member function.
 * Note that there's no `new` keyword used to create an object.
 * See http://kotlinlang.org/docs/reference/classes.html#classes
 */

class Greeter(val name: String?="unkown") {
    fun greet() {
        println("Hello, ${name}");
    }
}

fun main(args: Array<String>) {
    Greeter(if (args.size == 0)
        "bill"
    else args[0]).greet()
}