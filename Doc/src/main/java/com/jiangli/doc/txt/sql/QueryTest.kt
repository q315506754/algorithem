package com.jiangli.doc.txt.sql

/**
 *
 *
 * @author Jiangli
 * @date 2018/6/13 19:18
 */
fun main(args: Array<String>) {
    iterToPair(arrayListOf("sdsd"))

    val iterToPair:List<Pair<Number, Number>> = iterToPair(arrayListOf(23))

//    var x = 111
//    var x = 82
    var x = 59

//    val t = when(x){
//        x>0 -> 20
//        else -> 33
//    }

    when {
        x>100->println("a")
        x>90->println("b")
        x>80->println("c")
        else->println("不及格")
    }
}