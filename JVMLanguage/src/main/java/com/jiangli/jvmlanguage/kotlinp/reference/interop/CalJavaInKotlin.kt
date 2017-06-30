package com.jiangli.jvmlanguage.kotlinp.reference.interop.CalJavaInKotlin.kt

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 17:17
 */
import com.jiangli.jvmlanguage.kotlinp.reference.interop.Foo
import java.util.*

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

fun main(args: Array<String>) {
    calendarDemo()
}
