package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.DelegateProp

import com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.Foo


/**
 *
 *延迟属性（lazy properties）: 其值只在首次访问时计算，
可观察属性（observable properties）: 监听器会收到有关此属性变更的通知，
把多个属性储存在一个映射（map）中，而不是每个存在单独的字段中。

 * @author Jiangli
 * @date 2017/6/28 16:36
 */

/**
 * 局部委托属性（自 1.1 起）
 */
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    if (true) {
        memoizedFoo.doSomething()
    }

}

fun main(args: Array<String>) {
    example{
        println("asdasdsad...")
        object :Foo {
            override val count: Int
                get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        }

    }
}