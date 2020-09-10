package com.jiangli.jvmlanguage.kotlinp.reference.methodsAndLambda.Coroutines.kt

import com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.Foo
import kotlin.coroutines.experimental.SequenceBuilder
import kotlin.coroutines.experimental.buildSequence

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/29 15:41
 */

/**
 * 挂起函数

当我们调用标记有特殊修饰符 suspend 的函数时，会发生挂起：

 */

suspend fun doSomething(foo: Foo) {

}



//fun test(x: Any): Unit {
//     fun <T> async(block: suspend () -> T) {}
//
//    async {
//        doSomething(object: Foo {
//            override val count: Int
//                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
//        })
////        ……
//    }
//}


//还要注意的是，挂起函数可以是虚拟的，当覆盖它们时，必须指定 suspend 修饰符：
interface Base {
    suspend fun foo()
}

class Derived: Base {
    override suspend fun foo() {  }
}

suspend fun SequenceBuilder<Int>.yieldIfOdd(x: Int) {
    if (x % 2 != 0) yield(x)
}

val lazySeq22 = buildSequence {
    for (i in 1..10) yieldIfOdd(i)
}

fun main(args: Array<String>) {
    val lazySeq = buildSequence {
        yield(0)
        yieldAll(1..10)
    }

    lazySeq.forEach { print("$it ") }
    println()
    lazySeq22.forEach { print("$it ") }
}