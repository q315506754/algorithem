package com.jiangli.jvmlanguage.kotlinp.reference.other

/**
 *
 *区间
 *区间表达式由具有操作符形式 .. 的 rangeTo 函数辅以 in 和 !in 形成。 区间是为任何可比较类型定义的，但对于整型原生类型，它有一个优化的实现。
 *区间实现了该库中的一个公共接口：ClosedRange<T>。
 * ClosedRange<T> 在数学意义上表示一个闭区间，它是为可比较类型定义的。 它有两个端点：start 和 endInclusive 他们都包含在区间内。 其主要操作是 contains，通常以 in/!in 操作符形式使用。
 *
 *
 * 数列
 *整型数列（IntProgression、 LongProgression、 CharProgression）表示等差数列。 数列由 first 元素、last 元素和非零的 step 定义。
 * 第一个元素是 first，后续元素是前一个元素加上 step。 last 元素总会被迭代命中，除非该数列是空的。
 *
 * @author Jiangli
 * @date 2017/6/30 10:20
 */
