package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects

/**
 *
 *
 * Java 类型系统中最棘手的部分之一是通配符类型（参见 Java Generics FAQ）。
 * 而 Kotlin 中没有。 相反，它有两个其他的东西：声明处型变（declaration-site variance）与类型投影（type projections）。
 *
 *
 * 通配符类型参数 ? extends E
 *
 * 带 extends 限定（上界）的通配符类型使得类型是协变的（covariant）。
 *
 *
 * 如果只能从集合中获取项目，那么使用 String 的集合， 并且从其中读取 Object 也没问题 。
 * 反过来，如果只能向集合中 放入 项目，就可以用 Object 集合并向其中放入 String：
 * 在 Java 中有 List<? super String> 是 List<Object> 的一个超类。
 * 后者称为逆变性（contravariance）
 *
 * PECS 代表生产者-Extens，消费者-Super（Producer-Extends, Consumer-Super）。
 *
 *通配符（或其他类型的型变）保证的唯一的事情是类型安全。不可变性完全是另一回事。
 *
 *
 * @author Jiangli
 * @date 2017/6/27 9:47
 */

/**
 * 声明处型变：我们可以标注 Source 的类型参数 T 来确保它仅从 Source<T> 成员中返回（生产），并从不被消费。
 * 为此，我们提供 out 修饰符：
 *
 * 一般原则是：当一个类 C 的类型参数 T 被声明为 out 时，它就只能出现在 C 的成员的输出-位置，但
 * 回报是 C<Base> 可以安全地作为 C<Derived>的超类。
 *
 * 类 C 是在参数 T 上是协变的，或者说 T 是一个协变的类型参数。 你可以认为 C 是 T 的生产者，而不是 T 的消费者。
 *
 * out修饰符称为型变注解，并且由于它在类型参数声明处提供，所以我们讲声明处型变。
 * 这与 Java 的使用处型变相反，其类型用途通配符使得类型协变。
 */
internal abstract class Source<out T> {
    abstract fun nextT(): T
}

internal fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs // 这个没问题，因为 T 是一个 out-参数
    // ……
}

/**
 * ，Kotlin 又补充了一个型变注释：in。它使得一个类型参数逆变：只可以被消费而不可以被生产。逆变类的一个很好的例子是 Comparable：
 *
 * 消费者 in, 生产者 out! :-)
 */
internal abstract class MyComparable<in T> {
    abstract fun compareTo(other: T): Int
}

internal fun demo(x: MyComparable<Number>) {
    x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的子类型
    // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
    val y: MyComparable<Double> = x // OK！
}


/**
 * 使用处型变：类型投影
 *
 */
internal class MyArray<T>(val size: Int) {
    fun get(index: Int): T { TODO()}
        fun set(index: Int, value: T) {TODO()}
}

/**
 * Array <T> 在 T 上是不型变的，因此 Array <Int> 和 Array <Any> 都不是另一个的子类型。
 * 为什么？ 再次重复，因为 copy 可能做坏事，也就是说，例如它可能尝试写一个 String 到 from，
 * 并且如果我们实际上传递一个 Int 的数组，一段时间后将会抛出一个 ClassCastException 异常。
 */
//internal fun copy(from: MyArray<Any>, to: MyArray<Any>) {
////    assert(from.size == to.size)
////    for (i in from.indices)
////        to[i] = from[i]
//}

/**
 * 我们说from不仅仅是一个数组，而是一个受限制的（投影的）数组：
 * 我们只可以调用返回类型为类型参数 T 的方法，如上，这意味着我们只能调用 get()。
 * 这就是我们的使用处型变的用法，并且是对应于 Java 的 Array<? extends Object>、 但使用更简单些的方式。
 */
internal fun copy(from: MyArray<out Any>, to: MyArray<Any>) {
    // ……
}

/**
 * Array<in String> 对应于 Java 的 Array<? super String>，也就是说，
 * 你可以传递一个 CharSequence 数组或一个 Object 数组给 fill() 函数。
 */
internal fun fill(dest: MyArray<in String>, value: String) {
    // ……
}

internal  fun test123(){
    val ints: MyArray<Int> = MyArray(3)
    val any = MyArray<Any>(3)
    copy(ints, any)

    val strs: MyArray<CharSequence> = MyArray(3)
    fill(strs,"sdfds")
}

/**
 * 星投影
 *
 * 对于 Foo <out T>，其中 T 是一个具有上界 TUpper 的协变类型参数，Foo <*> 等价于 Foo <out TUpper>。 这意味着当 T 未知时，你可以安全地从 Foo <*> 读取 TUpper 的值。
 * 对于 Foo <in T>，其中 T 是一个逆变类型参数，Foo <*> 等价于 Foo <in Nothing>。 这意味着当 T 未知时，没有什么可以以安全的方式写入 Foo <*>。
 * 对于 Foo <T>，其中 T 是一个具有上界 TUpper 的不型变类型参数，Foo<*> 对于读取值时等价于 Foo<out TUpper> 而对于写值时等价于 Foo<in Nothing>。
 */

internal class  Fooo<T> {
     fun nextT(): T {TODO()}
     fun compareTo(other: T): Int {TODO()}
}

internal  fun test2(){
//    val listOf = listOf("a", "b")
//    List<>
    val bb = Fooo<String>()
//    bb.compareTo("") //ok

    val cc:Fooo<*> = bb
    val nextT = cc.nextT() //Any?
//    cc.compareTo()  //Nothing
//    cc.compareTo("")  //error
}

/**
 * 泛型函数

不仅类可以有类型参数。函数也可以有。类型参数要放在函数名称之前：
 */


internal fun <T> singletonList(item: T): List<T> {
    TODO()
}

internal fun <T> T.basicToString() : String {  // 扩展函数
    TODO()
}

val l = singletonList<Int>(1)
//val l = singletonList(1)


/**
 * 泛型约束
 *
 */

/**
 *
 * 上界

最常见的约束类型是与 Java 的 extends 关键字对应的 上界：

 */
internal fun <T : MyComparable<T>> sort(list: List<T>) {
    // ……
}

/**
默认的上界（如果没有声明）是 Any?。在尖括号中只能指定一个上界。
如果同一类型参数需要多个上界，我们需要一个单独的 where-子句：
 *
 */
internal class CusCls:Comparable<CusCls>,Cloneable{
    override fun compareTo(other: CusCls): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
internal fun <T> cloneWhenGreater(list: List<T>, threshold: T): List<T>
        where T : Comparable<T>, T : Cloneable {
    val map:List<T> = list.filter { it > threshold }.map { it }
    return map
}

internal val l2 = cloneWhenGreater(listOf<CusCls>(),CusCls())
