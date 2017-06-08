package foo

private fun foo() {} // visible inside example.kt

public var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in example.kt

internal val baz = 6    // visible inside the same module


//a module is a set of Kotlin files compiled together:
//
//an IntelliJ IDEA module;
//a Maven or Gradle project;
//a set of files compiled with one invocation of the Ant task.

//包级别
//public:default, which means that your declarations will be visible everywhere;
//internal:it is visible everywhere in the same module;
//protected: /
//private:only be visible inside the file containing the declaration;

class VisiDemoCls
//构造器级别
//public:default
//internal:
//protected:
//private:
protected constructor(a: Int){
    //类级别
    //public:default,  any client who sees the declaring class sees its public members.
    //internal:any client inside this module who sees the declaring class sees its internal members;
    //protected: same as private + visible in subclasses too;
    //private: visible inside this class only (including all its members);
    /** NOTE for Java users: outer class does not see private members of its inner classes in Kotlin. **/

}
