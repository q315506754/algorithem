package com.jiangli.jvmlanguage.kotlinp

/**
 * Created by Jiangli on 2017/5/27.
 */
fun main(args: Array<String>) {
    println(task())
//    println(43.isOdd2())  //could not call inner func

    val s = buildString {
        //this代表是StringBuilder
        this.append("Numbers: ")

        for (i in 1..3) {
            // 'this' can be omitted
            append(i)
        }
    }
    println(s)

    val m = buildMap {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
    println(m)

    println(createString())
    println(createMap())

}

//////////////////////////////////////
//////////////////////////////////////
//////////////////////////////////////

fun <T> T.myApply(f: T.() -> Unit): T {
    f()
    return this
}
fun createString(): String {
    return StringBuilder().myApply {
        append("Numbers: ")
        for (i in 1..10) {
            append(i)
        }
    }.toString()
}

fun createMap(): Map<Int, String> {
    return hashMapOf<Int, String>().myApply {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}

//////////////////////////////////////
fun buildMap(builder: HashMap<Int,String>.()->Unit): HashMap<Int, String> {
    val hashMap = HashMap<Int, String>()
    hashMap.builder()
    return hashMap
}
//////////////////////////////////////

fun buildString(build: StringBuilder.() -> Unit): String {
    val stringBuilder = StringBuilder()
    stringBuilder.build()
    return stringBuilder.toString()
}

//////////////////////////////////////

fun task(): List<Boolean> {
    val isEven: Int.() -> Boolean = { this%2==0 }
    val isOdd: Int.() -> Boolean = { this%2!=0 }
    fun Int.isOdd2():Boolean= this%2!=0
    fun Int.isOdd3():Boolean {return this%2!=0}

    return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven(),43.isOdd2(),44.isOdd3())
}