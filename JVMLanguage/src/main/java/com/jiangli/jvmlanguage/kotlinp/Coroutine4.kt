package com.jiangli.jvmlanguage.kotlinp

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) = runBlocking<Unit> {
    val job = launch(CommonPool) { // 创建一个协程并维持一个指向它任务的引用
        while (true) {
//            delay(1000L)
            println("World!")
        }
    }

    while (true) {
//        delay(1000L)
        println("Hello!")
    }
}