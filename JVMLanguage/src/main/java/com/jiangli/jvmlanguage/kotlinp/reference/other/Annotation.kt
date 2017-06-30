package com.jiangli.jvmlanguage.kotlinp.reference.other.Annotation.kt

//可以使用相同的语法来标注整个文件。
//要做到这一点，把带有目标 file 的注解放在文件的顶层、package 指令之前或者在所有导入之前（如果文件在默认包中的话）：
//@file:JvmName("Foo")



import kotlin.reflect.KClass

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/30 13:14
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy

//用法

@Fancy class Foo {
    @Fancy fun baz(@Fancy foo: Int): Int {
        return (@Fancy 1)
    }
}

//如果需要对类的主构造函数进行标注，则需要在构造函数声明中添加 constructor 关键字 ，并将注解添加到其前面：
class Foo2 @Fancy constructor(dependency: String) {
    // ……
    var x: String? = null
        @Fancy set
}

annotation class ReplaceWith(val expression: String)

annotation class Deprecated(
        val message: String,
        val replaceWith: ReplaceWith = ReplaceWith(""))

@Deprecated("This function is deprecated, use === instead", ReplaceWith("this === other"))
class A

/**
 * 如果需要将一个类指定为注解的参数，请使用 Kotlin 类 （KClass）。
 * Kotlin 编译器会自动将其转换为 Java 类，以便 Java 代码能够正常看到该注解和参数 。
**/
annotation class Ann(val arg1: KClass<*>, val arg2: KClass<out Any>)
@Ann(String::class, Int::class) class MyClass

//Lambda 表达式
annotation class Suspendable
val f = @Suspendable {
//    Fiber.sleep(10)
}


annotation class Ann2


//class Example22(@field:Ann2 val foo,    // 标注 Java 字段
//              @get:Ann2 val bar,      // 标注 Java getter
//              @param:Ann2 val quux)   // 标注 Java 构造函数参数


//如果你对同一目标有多个注解，那么可以这样来避免目标重复——在目标后面添加方括号并将所有注解放在方括号内：
class Example {
    @set:[Fancy Ann2]
    var collaborator: String = ""
}

//支持的 [使用处目标] 的完整列表为：
//
//file
//property（具有此目标的注解对 Java 不可见）
//field
//get（属性 getter）
//set（属性 setter）
//receiver（扩展函数或属性的接收者参数）
//param（构造函数参数）
//setparam（属性 setter 参数）
//delegate（为委托属性存储其委托实例的字段）


//要标注扩展函数的接收者参数，请使用以下语法：
fun @receiver:Fancy String.myExtension() { }

//如果不指定使用处目标，则根据正在使用的注解的 @Target 注解来选择目标 。如果有多个适用的目标，则使用以下列表中的第一个适用目标：
//param
//property
//field

//如果 Java 中的 value 参数具有数组类型，它会成为 Kotlin 中的一个 vararg 参数：

// Java
annotation class AnnWithArrayMethod2(vararg val value: String)
// Kotlin
@AnnWithArrayMethod2("abc", "foo", "bar") class D
//@AnnWithArrayMethod2(value = arrayOf("abc", "foo", "bar")) class DA





//对于具有数组类型的其他参数，你需要显式使用 arrayOf：
// Java
annotation class AnnWithArrayMethod(val names: Array<String>)


// Kotlin
@AnnWithArrayMethod(names = arrayOf("abc", "foo", "bar")) class C