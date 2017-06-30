package com.jiangli.jvmlanguage.kotlinp.reference.other.Nullable.kt

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 11:08
 */

/**
 * 在条件中检查 null
**/
//首先，你可以显式检查 b 是否为 null，并分别处理两种可能：

//val l = if (b != null) b.length else -1
//编译器会跟踪所执行检查的信息，并允许你在 if 内部调用 length。 同时，也支持更复杂（更智能）的条件：

//if (b != null && b.length > 0) {
//    print("String of length ${b.length}")
//} else {
//    print("Empty string")
//}


/**
 * 安全的调用
**/

//bob?.department?.head?.name


/**
 * Elvis 操作符
**/

//当我们有一个可空的引用 r 时，我们可以说“如果 r 非空，我使用它；否则使用某个非空的值 x”：

//val l: Int = if (b != null) b.length else -1

//除了完整的 if-表达式，这还可以通过 Elvis 操作符表达，写作 ?:：
//val l = b?.length ?: -1

//请注意，因为 throw 和 return 在 Kotlin 中都是表达式，所以它们也可以用在 elvis 操作符右侧。这可能会非常方便，例如，检查函数参数：

fun foo(node: Node): String? {
    val parent = node.getParent() ?: return null
    val name = node.getName() ?: throw IllegalArgumentException("name expected")
    // ……
}

class Node {
    fun getParent(): Nothing? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getName() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

/**
 * !! 操作符

第三种选择是为 NPE 爱好者准备的。我们可以写 b!! ，这会返回一个非空的 b 值 （例如：在我们例子中的 String）或者如果 b 为空，就会抛出一个 NPE 异常：

val l = b!!.length

因此，如果你想要一个 NPE，你可以得到它，但是你必须显式要求它，否则它不会不期而至。
**/

/**
 * 安全的类型转换

如果对象不是目标类型，那么常规类型转换可能会导致 ClassCastException。 另一个选择是使用安全的类型转换，如果尝试转换不成功则返回 null：

val aInt: Int? = a as? Int


**/


//可空类型的集合
//如果你有一个可空类型元素的集合，并且想要过滤非空元素，你可以使用 filterNotNull 来实现。

val nullableList: List<Int?> = listOf(1, 2, null, 4)
val intList: List<Int> = nullableList.filterNotNull()