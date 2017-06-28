package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.NestedClass.kt

import java.awt.event.ActionEvent
import java.awt.event.ActionListener


/**
 *
 *
 * @author Jiangli
 * @date 2017/6/28 15:10
 */
//嵌套类
internal class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
//        fun foo() = bar
        fun foo2() {
            this@Nested
            this
        }
    }
}

internal val demo = Outer.Nested().foo() // == 2

//内部类
internal class Outer2 {
    private val bar: Int = 1
    //类可以标记为 inner 以便能够访问外部类的成员。内部类会带有一个对外部类的对象的引用：
    inner class Inner {
        fun foo() = bar
        fun foo2() {
            this@Outer2
            this@Inner
            this
        }
    }
}

internal val demo2 = Outer2().Inner().foo() // == 1

//匿名内部类

internal fun  test(){
//    window.addMouseListener(object: MouseAdapter() {
//        override fun mouseClicked(e: MouseEvent) {
//            // ……
//        }
//
//        override fun mouseEntered(e: MouseEvent) {
//            // ……
//        }
//    })

    val listener = ActionListener { println("clicked") }
    val listener2:ActionListener = ActionListener { println("clicked") }
    fun listener3_f(e: ActionEvent):Unit =  println("clicked")
//    val listener3:ActionListener = ::listener3_f
    val kFunction1 = String::toString

}