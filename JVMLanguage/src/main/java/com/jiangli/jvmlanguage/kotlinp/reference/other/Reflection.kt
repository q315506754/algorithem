package com.jiangli.jvmlanguage.kotlinp.reference.other

import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

/**
 *在 Java 平台上，使用反射功能所需的运行时组件作为单独的 JAR 文件（kotlin-reflect.jar）分发。
 * 这样做是为了减少不使用反射功能的应用程序所需的运行时库的大小。
 * 如果你需要使用反射，请确保该 .jar文件添加到项目的 classpath 中
 *
 * @author Jiangli
 * @date 2017/6/30 15:50
 */


/**
 *
 * val c = MyClass::class
 *
 * 该引用是 KClass 类型的值。
 *
**/

/**
 *函数引用
 *
**/
fun isOdd(x: Int) = x % 2 != 0
fun isOdd(s: String) = s == "brillig" || s == "slithy" || s == "tove"

/**
 * 属性引用
**/
var x = 1

class A(val p: Int)

//要获得对应于 Java 类的 Kotlin 类，请使用 .kotlin 扩展属性：
fun getKClass(o: Any): KClass<Any> = o.javaClass.kotlin

/**
 * 构造函数引用
**/
class Foo

fun function(factory: () -> Foo) {
    val x: Foo = factory()
    println(x)
}

fun main(args: Array<String>) {
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd)) // 输出 [1, 3]

    val predicate: (String) -> Boolean = ::isOdd   // 引用到 isOdd(x: String)

    //表达式 ::x 求值为 KProperty<Int> 类型的属性对象，它允许我们使用 get() 读取它的值，
    // 或者使用 name 属性来获取属性名。更多信息请参见关于 KProperty 类的文档。
    println(::x.get()) // 输出 "1"
    ::x.set(2)
    println(x)         // 输出 "2"


    //要访问属于类的成员的属性，我们这样限定它：
    val prop = A::p
    println(prop.get(A(1))) // 输出 "1"
    println(A::p.javaGetter) // 输出 "public final int A.getP()"
    println(A::p.javaField)  // 输出 "private final int A.p"

    println(getKClass(A(1)))

    function(::Foo)

    /**
     * 绑定的函数与属性引用（自 1.1 起）
     **/
    val numberRegex = "\\d+".toRegex()
    println(numberRegex.matches("29")) // 输出“true”

    /**
     * 比较绑定的类型和相应的未绑定类型的引用。 绑定的可调用引用有其接收者“附加”到其上，因此接收者的类型不再是参数：
    **/
    val isNumber = numberRegex::matches
    val matches: (Regex, CharSequence) -> Boolean = Regex::matches
    println(isNumber("29")) // 输出“true”

    val strings = listOf("abc", "124", "a70")
    println(strings.filter(numberRegex::matches)) // 输出“[124]”

    val prop2 = "abc"::length
    println(prop2.get())   // 输出“3”
//    println(prop2)   // 输出“3”
}