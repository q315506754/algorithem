package com.jiangli.jvmlanguage.kotlinp.reference.interop.demo

import java.io.IOException

/**
 *
 *
 * @author Jiangli
 * @date 2017/7/5 16:50
 */

class Foo

fun bar() {
    println("bar...")
}

//如果需要在 Java 中将 Kotlin 属性作为字段暴露，那就需要使用 @JvmField 注解对其标注。
// 该字段将具有与底层属性相同的可见性。如果一个属性有幕后字段（backing field）、
// 非私有、没有 open /override 或者 const 修饰符并且不是被委托的属性，那么你可以用 @JvmField 注解该属性。
class C(id: String) {
    @JvmField val ID = id
}


//静态字段

//在命名对象或伴生对象中声明的 Kotlin 属性会在该命名对象或包含伴生对象的类中具有静态幕后字段。
//
//通常这些字段是私有的，但可以通过以下方式之一暴露出来：
//
//@JvmField 注解；
//lateinit 修饰符；
//const 修饰符。
class Key(val value: Int) {
    companion object {
        @JvmField
        val COMPARATOR: Comparator<Key> = compareBy<Key> { it.value }
    }
}

object Singleton {
    lateinit var provider: Provider
}

class Provider {

}

//用 const 标注的（在类中以及在顶层的）属性在 Java 中会成为静态字段：
const val MAX = 239

class D {
    companion object {
        @JvmStatic fun foo() {}
        fun bar() {}
    }
}

object Obj {
    @JvmStatic fun foo() {}
    fun bar() {}
}

fun main(args: Array<String>) {
    D.foo() //
    D.bar(); //
    D.foo() //
    D.bar() //

    Obj.foo() //
    Obj.bar() //
    Obj.bar() //
    Obj.foo() //
}

fun f(a: String, b: Int = 0, c: String = "abc") {
//    ……
}

//对于每一个有默认值的参数，都会生成一个额外的重载，这个重载会把这个参数和它右边的所有参数都移除掉。在上例中，会生成以下方法 ：
@JvmOverloads
@JvmName("f3")
fun f2(a: String, b: Int = 0, c: String = "abc") {
//    ……
}

@Throws(IOException::class)
fun f4() {
    throw IOException()
}
