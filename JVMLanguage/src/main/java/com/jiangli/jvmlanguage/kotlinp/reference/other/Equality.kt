package com.jiangli.jvmlanguage.kotlinp.reference.other

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 10:57
 */


/**
 * 引用相等

引用相等由 ===（以及其否定形式 !==）操作判断。a === b 当且仅当 a 和 b 指向同一个对象时求值为 true。

**/

/**
 * 结构相等由 ==（以及其否定形式 !=）操作判断。按照惯例，像 a == b 这样的表达式会翻译成

a?.equals(b) ?: (b === null)
**/

/**
 * 相等与不等操作符

表达式	翻译为
a == b	a?.equals(b) ?: (b === null)
a != b	!(a?.equals(b) ?: (b === null))
注意：=== 和 !==（同一性检查）不可重载，因此不存在对他们的约定

这个 == 操作符有些特殊：它被翻译成一个复杂的表达式，用于筛选 null 值。
null == null 总是 true，对于非空的 x，x == null 总是 false 而不会调用 x.equals()。
**/