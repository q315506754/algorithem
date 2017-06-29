package com.jiangli.jvmlanguage.kotlinp.reference.other

/**
 *
 *  与大多数语言不同，Kotlin 区分可变集合和不可变集合（lists、sets、maps 等）。
 *
 *
 *  Kotlin 的 List<out T> 类型是一个提供只读操作如 size、get等的接口。
 *  改变 list 的方法是由 MutableList<T> 加入的。这一模式同样适用于 Set<out T>/MutableSet<T> 及 Map<K, out V>/MutableMap<K, V>。
 *
 * @author Jiangli
 * @date 2017/6/29 15:59
 */
fun main(args: Array<String>) {
    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
    val readOnlyView: List<Int> = numbers
    println(numbers)        // 输出 "[1, 2, 3]"
    numbers.add(4)
    println(readOnlyView)   // 输出 "[1, 2, 3, 4]"
//    readOnlyView.clear()    // -> 不能编译

    val strings = hashSetOf("a", "b", "c", "c")
    assert(strings.size == 3)

    val controller = Controller()
    val message = controller.items
    println(message)
    controller._items.add("asdasds")
    controller._items.add("xxxxx")
    println(message)//just a snapshot
    println(controller.items)

    val items = listOf(1, 2, 3, 4)
    items.first() == 1
    items.last() == 4
    items.filter { it % 2 == 0 }   // 返回 [2, 4]

    val rwList = mutableListOf(1, 2, 3)
    rwList.requireNoNulls()        // 返回 [1, 2, 3]
    if (rwList.none { it > 6 }) println("No items above 6")  // 输出“No items above 6”
    val item = rwList.firstOrNull()

    val readWriteMap = hashMapOf("foo" to 1, "bar" to 2)
    println(readWriteMap["foo"])  // 输出“1”
    val snapshot: Map<String, Int> = HashMap(readWriteMap)
}


class Controller {
     val _items = mutableListOf<String>()
    val items: List<String> get() = _items.toList()
}