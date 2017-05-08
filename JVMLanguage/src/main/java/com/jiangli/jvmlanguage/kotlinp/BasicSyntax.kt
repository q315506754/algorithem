package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/8 18:31
 */
fun main(args: Array<String>) {
    val language = "tt"

    println(when (language) {
        "EN" -> "Hello!"
        "FR" -> "Salut!"
        "IT" -> "Ciao!"
        else -> "Sorry, I can't greet you in $language yet"
    })

    //if here
    //it is called
    fun max(a: Int, b: Int) = if (a > b) a else b

    println(max(1,3))

    //if here
    //the outer one is called
//    fun max(a: Int, b: Int) = if (a > b) a else b


    if (args.size >= 2) {
        val x = parseInt(args[0])
        val y = parseInt(args[1])
        if (x != null && y != null) {
            print(x * y) // Now we can
        } else {
            println("One of the arguments is null")
        }
    }

    println("" is Any)  //equals object
    println(1 is Any)
    println("" is String)
    println(null is Any) // false
    println(false is Any)

    val arr = arrayOf(1, 2, 3);
    val intArr = intArrayOf(1, 2, 3)    //同理还有 booleanArrayOf() 等
    val asc = Array(5, { i -> i * i })  //0,1,4,9,16
    val empty = emptyArray<Int>()
    println(asc[3])
    println(asc.get(3))
    val fixedSizeArray = arrayOfNulls<Int>(5)
    println(empty.lastIndex)
    println(fixedSizeArray.lastIndex)
    println(fixedSizeArray[2])

    for(eac in asc){
        print("$eac ")
    }
    println()

    //遍历下标
    for(idx in asc.indices){
        print(idx)
    }
    println("---foreach---")

    asc.forEach{a->println(a)}

    fun log2(a: Any) = println(a)

    log2("hahahah--------")
    log2("hahahah $asc")

//    val status=1
    val status=1.32
    val srs = doSth(status) //equal 1 prev
    println(srs)

    var k=123
    val t = when(k){
        20 -> 20
        else -> 33
    }
    println(t)


    log2("aaaa".plus("adasdsa"))
}
fun doSth(status: Any):Any? {
    when(status){
        1 -> println("equal 1 prev")
        is Int -> println("is int")
        1 -> println("equal 1 after")
        is Boolean -> println("is Boolean")
        else -> return status //return can't be emitted
    }
    return null
}

fun max(a: Int, b: Int) = if (a < b) a else b

// Return null if str does not hold a number
fun parseInt(str: String): Int? { //? marks nullable
    try {
        return str.toInt()
    } catch (e: NumberFormatException) {
        println("One of the arguments isn't Int")
    }
    return null
}


fun cases(obj: Any) {
    when (obj) {
        1 -> println("One")
        "Hello" -> println("Greeting")
        is Long -> println("Long")
        !is String -> println("Not a string")
        else -> println("Unknown")
    }
}