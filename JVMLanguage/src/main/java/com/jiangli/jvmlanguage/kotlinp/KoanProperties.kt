package com.jiangli.jvmlanguage.kotlinp

import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/26 11:48
 */
fun main(args: Array<String>) {
    val example = PropertyExample()
    example.propertyWithCounter=3
    example.propertyWithCounter=5
    println(example.counter)
    println(example.table)

//    println(example.str2) //kotlin.UninitializedPropertyAccessException: lateinit property str2 has not been initialized
//    println(example.str2) //Exception in thread "main" java.lang.IllegalStateException: Property str2 should be initialized before get.
}

//只能放顶层
//Compile-Time Constants
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

class PropertyExample {
    private var _table: Map<String, Int>? = null
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // Type parameters are inferred
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }
//    A backing field will be generated for a property if it uses the default
//    implementation of at least one of the accessors, or if
//    a custom accessor references it through the field identifier.
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value) {
            field = value
            counter++
        }


    //no backing field
    val isEmpty: Boolean
        get() = this.counter == 0

//    var str:String//compile error

    //var  must
    //no custom setter&getter
    //not primitive type
//    lateinit var str2:String //ok
     var str2:String by Delegates.notNull()//ok

}

class LazyProperty(val initializer: () -> Int) {
    var _lazy: Int?=null
    val lazy: Int
        get() {
            if(_lazy==null)
                _lazy =initializer()
            return  _lazy ?:throw AssertionError("Still null value")
        }
}

class D {
    var date: MyDate by EffectiveDate()
}

class EffectiveDate<R> : ReadWriteProperty<R, MyDate> {

    var timeInMillis: Long? = null

    override fun getValue(thisRef: R, property: KProperty<*>): MyDate {
        return this.timeInMillis?.toDate()?:MyDate(1970,1,1)
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: MyDate) {
        this.timeInMillis=value.toMillis()
    }
}

fun MyDate.toMillis(): Long {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth, 0, 0, 0)
    c.set(Calendar.MILLISECOND, 0)
    return c.getTimeInMillis()
}

fun Long.toDate(): MyDate {
    val c = Calendar.getInstance()
    c.setTimeInMillis(this)
    return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE))
}

