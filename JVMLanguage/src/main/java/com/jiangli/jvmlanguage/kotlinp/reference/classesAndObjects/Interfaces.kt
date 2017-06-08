package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects.interfaces

/**
 * Interfaces
 */
interface MyInterface {
    fun bar()
    fun foo() {
        // optional body
    }
}

/**
 * Implementing Interfaces
 */
class Child : MyInterface {
    override fun bar() {
        // body
    }
}

/**
 * Properties in Interfaces
 *
 * A property declared in an interface can either
 *  1.be abstract,
 *  2.or it can provide implementations for accessors
 */
interface MyInterface2 {
    val prop: Int // abstract

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        print(prop)
    }
}
class Child2 : MyInterface2 {
    override val prop: Int = 29
}

/**
 * Resolving overriding conflicts
 */
internal interface A {
    fun foo() { print("A") }
    fun bar()
}

internal interface B {
    fun foo() { print("B") }
    fun bar() { print("bar") }
}

internal class C : A {
    override fun bar() { print("bar") }
}

internal class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}

/**
 * Interfaces
 */
