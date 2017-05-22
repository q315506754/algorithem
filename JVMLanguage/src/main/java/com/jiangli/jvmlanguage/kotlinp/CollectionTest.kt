package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/22 17:32
 */
fun main(args: Array<String>) {
    println(emptyArray<String>())
    println(intArrayOf(1,2,3))
//    intArrayOf(1,2,3).forEach { println(it) }
//    intArrayOf(1,2,3).indices
//    intArrayOf(1,2,3).lastIndex

    println(emptyList<String>())
    println(listOf(1, 2, "3"))
    println(arrayListOf(1,"2","3"))
    println(mutableListOf<String>())
    println(mutableListOf(1, 2, "3"))
//    listOf(1, 2, "3").forEach { println(it) }
//    listOf(1, 2, "3").indices
//    listOf(1, 2, "3").lastIndex


    println(emptyMap<String,String>())
    println(mapOf<String,String>())
    println(mapOf("a" to "b"))
//    println(hashMapOf()) //compile error
    println(hashMapOf("a" to "b"))
    println(hashMapOf<String,String>())
    println(linkedMapOf("a" to "b"))
    println(linkedMapOf<String,String>())
    println(mutableMapOf("a" to "b"))
    println(mutableMapOf<String,String>())

    println(emptySet<String>())
    println(setOf<String>())
    println(setOf("asdasdas"))
    println(hashSetOf<String>())
    println(hashSetOf("asdasdas"))
    println(linkedSetOf<String>())
    println(linkedSetOf("asdasdas"))

    println(emptySequence<String>())
    println(sequenceOf("a","b","c"))
    val message = generateSequence { 2 }
    println(message)
    //infinite
//    for ( a in message) {
//        println(a)
//    }
}