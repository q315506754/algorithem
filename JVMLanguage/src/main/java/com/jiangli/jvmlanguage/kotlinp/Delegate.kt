package com.jiangli.jvmlanguage.kotlinp
import kotlin.Pair
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/9 13:31
 */

class Example {
    var p: String by Delegate()

    override fun toString() = "Example Class"
}

/**
 * There's some new syntax: you can say `val 'property name': 'Type' by 'expression'`.
 * The expression after by is the delegate, because get() and set() methods
 * corresponding to the property will be delegated to it.
 * Property delegates don't have to implement any interface, but they have
 * to provide methods named getValue() and setValue() to be called.</p>
 */
class Delegate() {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${prop.name}'='' to me!"
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
        println("$value has been assigned to ${prop.name} in $thisRef")
    }
}


/**
 * Delegates.lazy() is a function that returns a delegate that implements a lazy property:
 * the first call to get() executes the lambda expression passed to lazy() as an argument
 * and remembers the result, subsequent calls to get() simply return the remembered result.
 * If you want thread safety, use blockingLazy() instead: it guarantees that the values will
 * be computed only in one thread, and that all threads will see the same value.
 */

class LazySample {
    val lazy: String by lazy {
        println("computed!")
        "my lazy"
    }
}

/**
 * The observable() function takes two arguments: initial value and a handler for modifications.
 * The handler gets called every time we assign to `name`, it has three parameters:
 * a property being assigned to, the old value and the new one. If you want to be able to veto
 * the assignment, use vetoable() instead of observable().
 */
class User {
    var name: String by Delegates.observable("no name") {
        d, old, new ->
        println("$d $old -> $new")
    }

    /**
     * Users frequently ask what to do when you have a non-null var, but you don't have an
     * appropriate value to assign to it in constructor (i.e. it must be assigned later)?
     * You can't have an uninitialized non-abstract property in Kotlin. You could initialize it
     * with null, but then you'd have to check every time you access it. Now you have a delegate
     * to handle this. If you read from this property before writing to it, it throws an exception,
     * after the first assignment it works as expected.
     */
    var address: String by Delegates.notNull()

    fun init(str: String) {
        this.address = str
    }
}
/**
 * Properties stored in a map. This comes up a lot in applications like parsing JSON
 * or doing other "dynamic" stuff. Delegates take values from this map (by the string keys -
 * names of properties). Of course, you can have var's as well,
 * that will modify the map upon assignment (note that you'd need MutableMap instead of read-only Map).
 */

class UserMap(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

fun main(args: Array<String>) {
    fun p(a:Any) = println(a)
    val ex = Example()
    p(ex)

    ex.p="aasdsadas"
    p(ex.p)

    val sample = LazySample()
    println("lazy = ${sample.lazy}")
    println("lazy = ${sample.lazy}")

    val user = User()
    user.name="张三"
    user.name="李四"

//    user.address  // Property address should be initialized before get.
    user.init("上海松江区xx路")
    println(user.address)

    val map = mapOf(
            "name" to "John Doe",
            "age" to 25
    )
    p(map)
    val userMap = UserMap(map)

    println("name = ${userMap.name}, age = ${userMap.age}")

//    val pair = "aaa" to "bbb"
    val pair = "aaa" kktt "bbb"
    p(pair)
    p(pair.javaClass)
}

public infix fun <A, B> A.kktt(that: B): Pair<A, B> = Pair(this, that)
