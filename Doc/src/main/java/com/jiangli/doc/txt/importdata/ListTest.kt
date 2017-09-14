package com.jiangli.doc.txt.importdata

/**
 *
 *
 * @author Jiangli
 * @date 2017/9/14 18:17
 */

fun main(args: Array<String>) {
    val list = mutableListOf<Int>()
    for (i in 1..1000) {
        list.add(i)
    }
    val sublist = list.subList(2, 4)
    println(sublist)
    println(list)

//    sublist.add(999)
//    println(list)

    printX(list)
}

fun printX(list:List<Int>){
    val s = "del "+list.joinToString(" ","","",100,"") { s -> "$s" }
    println(s)

    if (list.size > 100) {
        printX(list.subList(100,list.size))
    }
}