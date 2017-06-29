package com.jiangli.jvmlanguage.kotlinp.reference.methodsAndLambda

import javax.swing.tree.TreeNode

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/29 15:00
 */

/**
 * 内联
 *
 * 使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 即那些在函数体内会访问到的变量。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。

 */

//inline fun lock<T>(l: Lock, foo: () -> T): T {
//    // ……
//}

//* 编译器没有为参数创建一个函数对象并生成一个调用。取而代之，编译器可以生成以下代码：

//l.lock()
//try {
//    foo()
//}
//finally {
//    l.unlock()
//}
//、、inline 修饰符影响函数本身和传给它的 lambda 表达式：所有这些都将内联到调用处。


/**
 * 禁用内联
 *
 * 使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 即那些在函数体内会访问到的变量。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。

 */

inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {
    // ……
}

/**
 * 非局部返回
 */
//我们可以只使用一个正常的、非限定的 return 来退出一个命名或匿名函数。
// 这意味着要退出一个 lambda 表达式，我们必须使用一个标签，
// 并且在 lambda 表达式内部禁止使用裸 return，因为 lambda 表达式不能使包含它的函数返回：

fun foo() {
//    ordinaryFunction {
//        return // 错误：不能使 `foo` 在此处返回
//    }
}

//但是如果 lambda 表达式传给的函数是内联的，该 return 也可以内联，所以它是允许的：
fun foo2() {
//    inlineFunction {
//        return // OK：该 lambda 表达式是内联的
//    }
}
fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        if (it == 0) return true // 从 hasZeros 返回
    }
    return false
}

/**
 * 交叉内联
 *
 * 一些内联函数可能调用传给它们的不是直接来自函数体、而是来自另一个执行上下文的 lambda 表达式参数，
 * 例如来自局部对象或嵌套函数。在这种情况下，该 lambda 表达式中也不允许非局部控制流。
 * 为了标识这种情况，该 lambda 表达式参数需要用 crossinline 修饰符标记:
 */
inline fun f(crossinline body: () -> Unit) {
    val f = object: Runnable {
        override fun run() = body()
    }
    // ……
}

/**
 * 具体化的类型参数
 *
 *
 */
inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}
//treeNode.findParentOfType<MyTreeNode>()

inline fun <reified T> membersOf() = T::class.members

fun main(s: Array<String>) {
    println(membersOf<StringBuilder>().joinToString("\n"))
//    println(membersOf<Nothing>().joinToString("\n"))
}

/**
 * 内联属性（自 1.1 起）
inline 修饰符可用于没有幕后字段的属性的访问器。 你可以标注独立的属性访问器：

 */

//val foo: Foo
//    inline get() = Foo()
//
//var bar: Bar
//    get() = ……
//inline set(v) { …… }


//你也可以标注整个属性，将它的两个访问器都标记为内联：
//inline var bar: Bar
//    get() = ……
//set(v) { …… }
//在调用处，内联访问器如同内联函数一样内联。