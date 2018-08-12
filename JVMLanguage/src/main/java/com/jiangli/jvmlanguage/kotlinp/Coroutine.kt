package com.jiangli.jvmlanguage.kotlinp

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.SequenceBuilder
import kotlin.coroutines.experimental.buildSequence

suspend fun doSomething(foo: Any){
    println("from suspend func : $foo")
}
suspend fun doSomethingB(foo: Any) {
    doSomething(foo)
}

fun main(args: Array<String>) {
//    doSomething("aa")  //wrong
    launch(CommonPool) { // 在一个公共线程池中创建一个协程
        delay(1000L) // 非阻塞的延迟一秒（默认单位是毫秒）
        println("World!") // 在延迟后进行打印
    }
    println("Hello,") // 主线程在协程延时后，不受影响，继续执行
    Thread.sleep(2000L) // 阻塞主线程2秒钟，保持JVM存活

    val fibonacciSeq = buildSequence {
        var a : Long = 0
        var b : Long = 1
        yield(1)           // 1
        while (true) {
            yield(a + b)   // 2
            val tmp = a + b
            a = b
            b = tmp
            print(tmp.toString() + " ") // 3
        }
    }
    println(fibonacciSeq.take(10).toList()) // 4

    val lazySeq = buildSequence {
        yield(0)
        yieldAll(1..5)
    }
    //输出: 0 1 2 3 4 5
    lazySeq.forEach { print("$it ") }
    println()

    //输出：1 3 5 7 9
    lazySeq2.forEach { print("$it ") }
}

//添加自定义yield函数
suspend fun SequenceBuilder<Int>.yieldIfOdd(x: Int) {
    if (x % 2 != 0) yield(x)
}
val lazySeq2 = buildSequence {
    for (i in 1..10) yieldIfOdd(i)
}