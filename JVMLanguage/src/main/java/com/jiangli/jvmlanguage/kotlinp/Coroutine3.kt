package com.jiangli.jvmlanguage.kotlinp

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

//使用挂起函数有两点需要注意的地方
//1. 声明挂起函数需要添加 suspend 修饰符
//2. 挂起函数只能在协程中或是其它的挂起函数中使用。
fun main(args: Array<String>) = runBlocking<Unit> {
    val job = launch(CommonPool) { doWorld() }
    println("Hello,")
    job.join()
}

// 这是你的第一个挂起函数
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}