package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.Extensions

/**
 * Extension Functions
 */
internal fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
val l = mutableListOf(1, 2, 3)

fun main(args: Array<String>) {
    l.swap(0, 2)
    println( l)

//    the extension function being called is determined by the type of the expression on which the function is invoked,
// not by the type of the result of evaluating that expression at runtime. For example:
    printFoo(D())

    C2().foo()
    C2().foo(2)

    println(null)
    val o:Any?=null
    println(o)
    println(o.toString())


    println(listOf("asda","bbb").lastIndex2)

    MyClass.a()
    MyClass.foo()
}


/**
 * Extensions are resolved statically
 *
 * By defining an extension, you do not insert new members into a class,
 * but merely make new functions callable with the dot-notation on variables of this type.
 *
 *
 */
internal open class C

internal class D: C()

internal fun C.foo() = "c"

internal fun D.foo() = "d"

internal fun printFoo(c: C) {
    println(c.foo())
}

// the member always wins
class C2 {
    fun foo() { println("member") }
}

fun C2.foo() { println("extension") }
fun C2.foo(i: Int) { println("extension 2") }

/**
 * Nullable Receiver
 */
internal fun Any?.toString(): String {
    if (this == null) return "this == null"
    // after the null check, 'this' is autocast to a non-null type, so the toString() below
    // resolves to the member function of the Any class
    return toString()
}

/**
 * Extension Properties
 *
 * extensions do not actually insert members into classes,
 * there's no efficient way for an extension property to have a backing field.
 * This is why initializers are not allowed for extension properties.
 * Their behavior can only be defined by explicitly providing getters/setters.
 */
internal val <T> List<T>.lastIndex2: Int
    get() = size - 1
//val Foo.bar = 1 // error: initializers are not allowed for extension properties

/**
 * Companion Object Extensions
 */
internal class MyClass {
    companion object {
        var b = 1
        fun a(){
            println("aaa:"+b)
        }
    }  // will be called "Companion"

    fun c(){
        println("ccc:"+b)
    }
}

internal fun MyClass.Companion.foo() {
    // ...
    b++
    a()
}

/**
 * Scope of Extensions
 */
//package foo.bar
//fun Baz.goo() { ... }

//package com.example.usage
//
//import foo.bar.goo // importing all extensions by name "goo"
//// or
//import foo.bar.*   // importing everything from "foo.bar"
//
//fun usage(baz: Baz) {
//    baz.goo()
//}