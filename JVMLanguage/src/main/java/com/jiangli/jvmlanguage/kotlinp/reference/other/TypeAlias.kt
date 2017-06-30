package com.jiangli.jvmlanguage.kotlinp.reference.other.TypeAlias.kt

/**
 *
 *类型别名
 *
 * @author Jiangli
 * @date 2017/6/30 17:06
 */

//它有助于缩短较长的泛型类型。 例如，通常缩减集合类型是很有吸引力的：
typealias FileTable2<K> = MutableMap<K, MutableList<String>>


//你可以为函数类型提供另外的别名：
typealias MyHandler = (Int, String, Any) -> Unit

typealias Predicate<T> = (T) -> Boolean


//你可以为内部类和嵌套类创建新名称：
class A {
    inner class Inner
}
class B {
    inner class Inner
}

typealias AInner = A.Inner
typealias BInner = B.Inner

fun main(args: Array<String>) {
    fun foo(p: Predicate<Int>) = p(42)

    val f: (Int) -> Boolean = { it > 0 }
    println(foo(f)) // 输出 "true"

    val p: Predicate<Int> = { it > 0 }
    println(listOf(1, -2).filter(p)) // 输出 "[1]"

}