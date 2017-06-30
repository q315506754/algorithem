package com.jiangli.jvmlanguage.kotlinp.reference.other

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 10:27
 */

/**
 * is 和 !is 操作符
**/

/**
 * 智能转换
**/

//编译器足够聪明，能够知道如果反向检查导致返回那么该转换是安全的：
//if (x !is String) return
//print(x.length) // x 自动转换为字符串


// `||` 右侧的 x 自动转换为字符串
//if (x !is String || x.length == 0) return

// `&&` 右侧的 x 自动转换为字符串
//if (x is String && x.length > 0) {
//    print(x.length) // x 自动转换为字符串
//}

//这些 智能转换 用于 when-表达式 和 while-循环 也一样：
//when (x) {
//    is Int -> print(x + 1)
//    is String -> print(x.length + 1)
//    is IntArray -> print(x.sum())
//}

/**
 * “不安全的”转换操作符
**/
//通常，如果转换是不可能的，转换操作符会抛出一个异常。因此，我们称之为不安全的。
// Kotlin 中的不安全转换由中缀操作符 as（参见operator precedence）完成：

//val x: String = y as String

//请注意，null 不能转换为 String 因该类型不是可空的， 即如果 y 为空，上面的代码会抛出一个异常。
// 为了匹配 Java 转换语义，我们必须在转换右边有可空类型，就像：

//val x: String? = y as String?

/**
 * “安全的”（可空）转换操作符
**/
//为了避免抛出异常，可以使用安全转换操作符 as?，它可以在失败时返回 null：

//val x: String? = y as? String

//请注意，尽管事实上 as? 的右边是一个非空类型的 String，但是其转换的结果是可空的。