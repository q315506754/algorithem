package com.jiangli.jvmlanguage.kotlinp.reference.basics

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/6 16:23
 */
fun main(args: Array<String>) {
//    Type	Bit width
//    Double	64
//    Float	32
//    Long	64
//    Int	32
//    Short	16
//    Byte	8

    val l:Long = 123L
    val h:Int = 0x02BF
    val b:Int = 0b00001011
    val d:Double = 123.5e10
    val f:Float = 123.5f


    //_
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 999_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010

    //box
    //1.need a nullable number reference
    //1.generics are involved
    val a: Int = 10000
    println(a === a) // Prints 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA) // !!!Prints 'false'!!!
    println(boxedA == anotherBoxedA) // true

    val l2 = 1L + 3 // Long + Int => Long


//    shl(bits) – signed shift left (Java's <<)
//    shr(bits) – signed shift right (Java's >>)
//    ushr(bits) – unsigned shift right (Java's >>>)
//    and(bits) – bitwise and
//    or(bits) – bitwise or
//    xor(bits) – bitwise xor
//    inv() – bitwise inversion
    val x = (1 shl 2) and 0x000FF000
    println(x)

    //1. escaped string   " \r \n"
    //1. raw string  """  ... """
    val price = """
    ${'$'}9.99
    """
    println(price)
}