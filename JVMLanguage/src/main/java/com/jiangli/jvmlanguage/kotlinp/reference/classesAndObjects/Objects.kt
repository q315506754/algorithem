package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.Objects

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/28 16:02
 */

internal open class A(x: Int) {
    public open val y: Int = x
}

internal interface B

internal val ab: A = object : A(1), B {
    override val y = 15
}

internal class C {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // 没问题
//        val x2 = publicFoo().x  // 错误：未能解析的引用“x”
    }
}

//fun main(args: Array<String>) {
//    println(ab.y)
////    println(ab.x)
//
//    val adHoc = object {
//        var x: Int = 0
//        var y: Int = 0
//    }
//    print(adHoc.x + adHoc.y)
//}

/**
 * 就像 Java 匿名内部类一样，对象表达式中的代码可以访问来自包含它的作用域的变量。 （与 Java 不同的是，这不仅限于 final 变量。）
 */
internal fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
    // ……
}

/**
 *
 * 对象声明
 * 单例模式
 *
 */
internal class DataProvider
internal object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ……
    }

    val allDataProviders: Collection<DataProvider>
        get() = allDataProviders  // ……
}

//要引用该对象，我们直接使用其名称即可：
internal fun aaa(x: Any): Unit {
    DataProviderManager.registerDataProvider(DataProvider())
}

/**
 * 这些对象可以有超类型
 */
internal object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
        // ……
    }
//
//    override fun mouseEntered(e: MouseEvent) {
//        // ……
//    }
}

/**
 * 伴生对象
 */
internal class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }

    //只能有1个
//    companion object {
//    }
}
internal class MyClass2 {
    companion object {
    }
}

internal interface Factory<T> {
    fun create(): T
}


internal class MyClass3 {
    init {
        println("MyClass3.init")
    }
    companion object : Factory<MyClass> {
        var aaa = MyClass()
        override fun create(): MyClass = MyClass()
        init {
            println("MyClass3.companion.init")
        }
    }
}

/**
 * 对象表达式和对象声明之间的语义差异

对象表达式和对象声明之间有一个重要的语义差别：

对象表达式是在使用他们的地方立即执行（及初始化）的
对象声明是在第一次被访问到时延迟初始化的
伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配

 */
fun main(args: Array<String>) {
    //    该伴生对象的成员可通过只使用类名作为限定符来调用：
    val instance = MyClass.create()
    val x1 = MyClass.Factory

    val x2 = MyClass2.Companion
    println(x1)
    println(x2)

    val create1 = MyClass3.create()
    val create2 = MyClass3.create()
    println(create1)
    println(create2)

    println(MyClass3.aaa)
    println(MyClass3.aaa)

}