package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/8 19:33
 */
fun main(args: Array<String>) {
    fun p(a:Any)= println(a)

    val c = PairC(12,false)
    p(c)//no toString()
    p(c.hashCode())
    //no copy
    p("${c.a} ${c.b}")

    val (ca,cb)=c //name must be  component1  component2

    p("$ca $cb")

    val c2 = Pair2(12,false)
    p(c2) //toString auto generated generated automatically
    p(c2.hashCode())
    val c2c = c2.copy()
    p(c2c)

    val (c2a,c2b)=c2 //component functions
    p("$c2a $c2b")
    p("${c2.component1()} ${c2.component2()}")

    val map = hashMapOf<String, Int>()
    map.put("one", 11)
    map.put("two", 22)

    for ((key, value) in map) {
        println("key = $key, value = $value")
    }

    fun start(): String = TODO()
    start()
}

class PairC<K,V>(val a:K, val b:V){
    operator fun component1(): K {
        return a
    }

    operator fun component2(): V {
        return b
    }
}

/**
 *  Data class gets component functions, one for each property declared
 *  in the primary constructor, generated automatically, same for all the
 *  other goodies common for data: toString(), equals(), hashCode() and copy().
 *  See http://kotlinlang.org/docs/reference/data-classes.html#data-classes
 */

data class Pair2<K,V>(val a:K,val b:V){
}
