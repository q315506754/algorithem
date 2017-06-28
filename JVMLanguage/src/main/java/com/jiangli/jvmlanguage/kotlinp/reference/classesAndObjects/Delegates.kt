package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.Delegates

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/28 16:28
 */

 interface Base {
    fun print()
}

 class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

/**
 * 请注意，覆盖会以你所期望的方式工作：编译器会使用你的 override 实现取代委托对象中的实现。
 * 如果我们为 Derived 添加 override fun print() { print("abc") }，该程序会输出“abc”而不是“10”。
 *
 * Derived 的超类型列表中的 by-子句表示 b 将会在 Derived 中内部存储。 并且编译器将生成转发给 b 的所有 Base 的方法。
 */
 class Derived(b: Base) : Base by b

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    Derived(b).print() // 输出 10
}
