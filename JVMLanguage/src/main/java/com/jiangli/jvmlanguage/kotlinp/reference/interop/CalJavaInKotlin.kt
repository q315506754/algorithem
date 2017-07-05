package com.jiangli.jvmlanguage.kotlinp.reference.interop.CalJavaInKotlin.kt

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 17:17
 */
import com.jiangli.jvmlanguage.kotlinp.reference.interop.Foo
import com.jiangli.jvmlanguage.kotlinp.reference.interop.JavaArrayExample
import java.io.File
import java.util.*
import java.util.concurrent.Executors

fun demo(source: List<Int>) {
    val list = ArrayList<Int>()
    // “for”-循环用于 Java 集合：
    for (item in source) {
        list.add(item)
    }
    // 操作符约定同样有效：
    for (i in 0..(source.size) - 1) {
        list[i] = source[i] // 调用 get 和 set
    }
}



//请注意，如果 Java 类只有一个 setter，它在 Kotlin 中不会作为属性可见，因为 Kotlin 目前不支持只写（set-only）属性。
fun calendarDemo() {
    val calendar = Calendar.getInstance()
    if (calendar.firstDayOfWeek == Calendar.SUNDAY) {  // 调用 getFirstDayOfWeek()
        calendar.firstDayOfWeek = Calendar.MONDAY       // 调用 setFirstDayOfWeek()
    }

    //    如果一个 Java 库使用了 Kotlin 关键字作为方法，你仍然可以通过反引号（`）字符转义它来调用该方法
    val foo = Foo()

    foo.`is`()
}

//native
external fun foo(x: Int): Double

//平台类型表示法
//
//如上所述，平台类型不能在程序中显式表述，因此在语言中没有相应语法。 然而，编译器和 IDE 有时需要（在错误信息中、参数信息中等）显示他们，所以我们用一个助记符来表示他们：
//
//T! 表示“T 或者 T?”，
//(Mutable)Collection<T>! 表示“可以可变或不可变、可空或不可空的 T 的 Java 集合”，
//Array<(out) T>! 表示“可空或者不可空的 T（或 T 的子类型）的 Java 数组”

fun main(args: Array<String>) {
    calendarDemo()

    val javaObj = JavaArrayExample()
    val array = intArrayOf(0, 1, 2, 3)
    javaObj.removeIndices(array)  // 将 int[] 传给方法

    //你需要使用展开运算符 * 来传递 IntArray：
//    目前无法传递 null 给一个声明为可变参数的方法。
    javaObj.removeIndicesVarArg(*array)


    println(File("").absolutePath)


    val foo = Foo()

    println(foo.javaClass)
    println(foo::class)
    println(foo::class.java)
    println(foo::class.java.javaClass)
    println(foo::class.java.javaClass.javaClass)


    //SAM 转换
    //Kotlin 函数字面值可以被自动的转换成只有一个非默认方法的 Java 接口的实现
//    请注意，SAM 转换只适用于接口，而不适用于抽象类，即使这些抽象类也只有一个抽象方法。
//    此功能只适用于 Java 互操作；因为 Kotlin 具有合适的函数类型，所以不需要将函数自动转换为 Kotlin 接口的实现，因此不受支持。
    val runnable = Runnable { println("This runs in a runnable") }
    val executor = Executors.newSingleThreadExecutor()
// Java 签名：void execute(Runnable command)
    executor.execute { println("This runs in a thread pool") }
//    executor.execute { runnable }
    executor.execute ( runnable )


//    println(foo(1))
//    java.lang.UnsatisfiedLinkError: com.jiangli.jvmlanguage.kotlinp.reference.interop.CalJavaInKotlin.kt.CalJavaInKotlinKt.foo(I)D

    synchronized(foo) {
        (foo as java.lang.Object).wait()
    }
}
