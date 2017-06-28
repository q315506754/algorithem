package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/8 15:56
 */

/**
 *  Classes
 */
class Invoice {
}
class Empty

/**
 *  Constructors
 */
class Person constructor(firstName: String) {
}

//The primary constructor cannot contain any code. Initialization code can be placed in initializer blocks, which are prefixed with the init keyword:
class Customer(val firstName: String, val lastName: String, var age: Int) {
    init {
//        logger.info("Customer initialized with value ${name}")
    }
}

/**
 *  Secondary Constructors
 */
class Person2(val name: String) {
    constructor(name: String, parent: Person) : this(name) {
//        parent.children.add(this)
    }
}

/**
 *  Creating instances of classes
 */
val invoice = Invoice()
val customer = Customer("Joe","Smith",13)

/**
 *  Class Members

    Classes can contain

    Constructors and initializer blocks
    Functions
    Properties
    Nested and Inner Classes
    Object Declarations
 */


/**
 *  Inheritance
 *
 */
open class View(p: Context){
    constructor(ctx: Context, attrs: AttributeSet):this(ctx){

    }
}
 class Context()
 class AttributeSet()
class MyView : View {
    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
}

/**
 *  Overriding Methods
 */
open class Base {
    open fun v() {}
    fun nv() {}
}
class Derived() : Base() {
    override fun v() {}
}

/**
 *  Overriding Properties
 */
interface Foo {
    val count: Int

    fun doSomething() {
        println("doSomething...")
    }
}

class Bar1(override val count: Int) : Foo

class Bar2 : Foo {
    override var count: Int = 0
}

/**
 *  Overriding Rules
 */
open internal class A {
    open fun f() { print("A") }
    fun a() { print("a") }
}

internal interface B {
    fun f() { print("B") } // interface members are 'open' by default
    fun b() { print("b") }
}

internal class C() : A(), B {
    // The compiler requires f() to be overridden:
    override fun f() {
//        super.f() //compile error
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f()
    }
}

/**
 *  Abstract Classes
 */
open class Base2 {
    open fun f() {}
}

abstract class Derived2 : Base2() {
    open override abstract fun f()  //open could be emitted
}