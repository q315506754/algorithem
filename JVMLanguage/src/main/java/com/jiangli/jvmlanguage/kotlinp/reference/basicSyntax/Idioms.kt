package com.jiangli.jvmlanguage.kotlinp.reference.basicSyntax

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/6 16:12
 */

fun main(args: Array<String>) {
    for (i in 0 until 10 step 2) {
        print(i)
    }
    println()
    for (i in 0..10 step 2) {
        print(i)
    }
    println()

    val mapOf = mapOf("a" to 12, "bb" to 12)
    println(mapOf)
    println(mapOf["a"])
    println(mapOf["cc"])

    val a = mapOf?.let {
        println("it's not null $it")
        it["a"] //return
    }
    println("a: $a")

    class Turtle {
        fun penDown(){}
        fun penUp(){}
        fun turn(degrees: Double){}
        fun forward(pixels: Double){}
    }

//    println(this) //error

    val myTurtle = Turtle()
    with(myTurtle) { //draw a 100 pix square
        println(this)
        penDown()
        for(i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }

    val b: Boolean? = null
    if (b == true) {
        println("b == true")
    } else {
        // `b` is false or null
        println("b != true :$b")
    }
}