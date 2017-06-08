package com.jiangli.jvmlanguage.kotlinp.reference.classesAndObjects

/**
 *
 *
 * @author Jiangli
 * @date 2017/6/8 16:08
 */


/**
 * Declaring Properties
 * **/
class Address {
//    var name: String = ...
//    var street: String = ...
//    var city: String = ...
//    var state: String? = ...
//    var zip: String = ...
}

/**
 * Getters and Setters
 **/
//var <propertyName>[: <PropertyType>] [= <property_initializer>]
//[<getter>]
//[<setter>]
class Address2 {
    var size = 0

    val isEmpty get() = this.size == 0  // has type Boolean

    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation

    var setterWithAnnotation: Any? = null
//        @Inject set // annotate the setter with Inject
}


/**
 * Backing Fields
 *
 * Kotlin provides an automatic backing field which can be accessed using the field identifier:
 * The field identifier can only be used in the accessors of the property.
 **/
class Address3 {
    var size = 0

    var counter = 0 // the initializer value is written directly to the backing field
        set(value) {
            if (value >= 0) field = value
        }


    //"implicit backing field"
    //A backing field will be generated for a property if it
    //  1.uses the default implementation of at least one of the accessors,
    //  2.or if a custom accessor references it through the field identifier.


    //no backing field for follow
    val isEmpty: Boolean
        get() = this.size == 0

}

/**
 * Backing Properties
 *
 * does not fit into this "implicit backing field" scheme, fall back to having a backing property:
 **/
class Address4 {
    private var _table: Map<String, Int>? = null
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // Type parameters are inferred
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }
}

/**
 * Compile-Time Constants
 *
 *
    Top-level or member of an object
    Initialized with a value of type String or a primitive type
    No custom getter
 **/

const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

@Deprecated(SUBSYSTEM_DEPRECATED) fun foo() {  }


/**
 * Late-Initialized Properties
**/
//public class MyTest {
//    lateinit var subject: TestSubject
//
//    @SetUp fun setup() {
//        subject = TestSubject()
//    }
//
//    @Test fun test() {
//        subject.method()  // dereference directly
//    }
//}

/**
 * Late-Initialized Properties
 * Delegated Properties
 **/