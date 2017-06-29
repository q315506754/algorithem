package com.jiangli.jvmlanguage.kotlinp.reference.other

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/29 15:57
 */


//注意声明两个参数和声明一个解构对来取代单个参数之间的区别：

//{ a //-> …… } // 一个参数
// { a, b //-> …… } // 两个参数
//  { (a, b) //-> …… } // 一个解构对
// { (a, b), c //-> …… } // 一个解构对以及其他参数

fun main(args: Array<String>) {
    mapOf("1" to "2","2" to "3").mapValues { (_,_)-> println("ddd") }
}