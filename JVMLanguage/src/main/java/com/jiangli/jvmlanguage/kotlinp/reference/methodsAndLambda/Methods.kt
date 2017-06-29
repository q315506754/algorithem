package com.jiangli.jvmlanguage.kotlinp.reference.methodsAndLambda.methods

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/29 11:00
 */
//中缀表示法
//
//函数还可以用中缀表示法调用，当
//
//1.他们是成员函数或扩展函数
//2.他们只有一个参数
//3.他们用 infix 关键字标注

// 给 Int 定义扩展
internal infix fun Int.shl2(x: Int): Int {
    TODO()
}

internal fun test(x: Any): Unit {
    // 用中缀表示法调用扩展函数
    1 shl2 2

// 等同于这样

    1.shl2(2)
}

/**
 * 默认参数

函数参数可以有默认值，当省略相应的参数时使用默认值。与其他语言相比，这可以减少重载数量。
**/
//覆盖方法总是使用与基类型方法相同的默认参数值。 当覆盖一个带有默认参数值的方法时，必须从签名中省略默认参数值：

open class A {
    open fun foo(i: Int = 10) {  TODO() }
}

class B : A() {
//    override fun foo(i: Int=20) {  TODO() }  // 不能有默认值
    override fun foo(i: Int) {  TODO() }  // 不能有默认值
}

/**
 * 命名参数
 */
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' ') {

}

internal fun test2(x: Any): Unit {
//    如果我们不需要所有的参数
    val str = "asdsa"
    reformat(str)
    reformat(str, true, true, false, '_')
    reformat(str, wordSeparator = '_')
}


/**
 * 单表达式函数
 */
//当函数返回单个表达式时，可以省略花括号并且在 = 符号之后指定代码体即可

fun double2(x: Int): Int = x * 2
//当返回值类型可由编译器推断时，显式声明返回类型是可选的

fun double3(x: Int) = x * 2

/**
 * 可变数量的参数（Varargs）
 */

//函数的参数（通常是最后一个）可以用 vararg 修饰符标记：
//即上例中的 ts 变量具有类型 Array <out T>。
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}
//只有一个参数可以标注为 vararg。
// 如果 vararg 参数不是列表中的最后一个参数， 可以使用命名参数语法传递其后的参数的值，
// 或者，如果参数具有函数类型，则通过在括号外部传一个 lambda。

val list = asList(1, 2, 3)

val a = arrayOf(1, 2, 3)
//如果我们已经有一个数组并希望将其内容传给该函数，我们使用伸展（spread）操作符（在数组前面加 *）：
val list2 = asList(-1, 0, *a, 4)


/**
 * 函数作用域
 *
 * 局部函数
 * 成员函数
 * 泛型函数
 * 内联函数
 * 扩展函数
 * 高阶函数和 Lambda 表达式
 * 尾递归函数  当一个函数用 tailrec 修饰符标记并满足所需的形式时，编译器会优化该递归，留下一个快速而高效的基于循环的版本。
 *
 *
 */


/**
 * 尾递归函数
 */
//当一个函数用 tailrec 修饰符标记并满足所需的形式时，编译器会优化该递归，留下一个快速而高效的基于循环的版本。

tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))
//要符合 tailrec 修饰符的条件的话，函数必须将其自身调用作为它执行的最后一个操作。
//在递归调用后有更多代码时，不能使用尾递归，并且不能用在 try/catch/finally 块中。目前尾部递归只在 JVM 后端中支持。
private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (x == y) return y
        x = y
    }
}


/**
 * 高阶函数
 *  高阶函数是将函数用作[参数]或[返回值]的函数。
 */

//fun toBeSynchronized() = sharedResource.operation()
//val result = lock(lock, ::toBeSynchronized)

//另一种方式是传一个 lambda 表达式：
//val result = lock(lock, { sharedResource.operation() })


/**
 * Lambda 表达式
 *
lambda 表达式总是被大括号括着，
其参数（如果有的话）在 -> 之前声明（参数类型可以省略），
函数体（如果存在的话）在 -> 后面。

Lambda 表达式的完整语法形式，即函数类型的字面值如下：

val sum = { x: Int, y: Int -> x + y }

 */

//在 Kotlin 中有一个约定，如果函数的最后一个参数是一个函数，
// 并且你传递一个 lambda 表达式作为相应的参数，你可以在圆括号之外指定它：

//lock (lock) {
//    sharedResource.operation()
//}




//另一个有用的约定是，如果函数字面值只有一个参数， 那么它的声明可以省略（连同 ->），其名称是 it。
//ints.map { it * 2 }

//这些约定可以写LINQ-风格的代码:
//strings.filter { it.length == 5 }.sortBy { it }.map { it.toUpperCase() }

//下划线用于未使用的变量（自 1.1 起）
//如果 lambda 表达式的参数未使用，那么可以用下划线取代其名称：

//map.forEach { _, value -> println("$value!") }
val sum: (Int, Int) -> Int = { x, y -> x + y }
//ints.filter { it > 0 } // 这个字面值是“(it: Int) -> Boolean”类型的
var ints = arrayOf(1)

fun ttt(x: Any): Unit {
    ints.filter {
        val shouldFilter = it > 0
        shouldFilter
    }

    ints.filter {
        val shouldFilter = it > 0
        return@filter shouldFilter
    }

    fun(x: Int, y: Int): Int = x + y

    //匿名函数
    //请注意，匿名函数参数总是在括号内传递。 允许将函数留在圆括号外的简写语法仅适用于 lambda 表达式。

//    一个不带标签的 return 语句总是在用 fun 关键字声明的函数中返回。
// 这意味着 lambda 表达式中的 return 将从包含它的函数返回，而匿名函数中的 return 将从匿名函数自身返回。
    ints.filter(fun(item) = item > 0)
}


//fun(x: Int, y: Int): Int = x + y


/**
 * 闭包
 *
 * Lambda 表达式或者匿名函数（以及局部函数和对象表达式） 可以访问其 闭包 ，
 * 即在外部作用域中声明的变量。 与 Java 不同的是可以修改闭包中捕获的变量：
 *
 */

/**
 *带接收者的函数字面值

Kotlin 提供了使用指定的 接收者对象 调用函数字面值的功能。
在函数字面值的函数体中，可以调用该接收者对象上的方法而无需任何额外的限定符。
这类似于扩展函数，它允你在函数体内访问接收者对象的成员。 其用法的最重要的示例之一是类型安全的 Groovy-风格构建器。

 */

//这样的函数字面值的类型是一个带有接收者的函数类型：

//sum : Int.(other: Int) -> Int
//该函数字面值可以这样调用，就像它是接收者对象上的一个方法一样：
//1.sum(2)

val sum22 = fun Int.(other: Int): Int = this + other
val ss = 1.sum22(2)



class HTML {
    fun body() {  }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // 创建接收者对象
    html.init()        // 将该接收者对象传给该 lambda
    return html
}


val xx =html {       // 带接收者的 lambda 由此开始
    body()   // 调用该接收者对象的一个方法
}
