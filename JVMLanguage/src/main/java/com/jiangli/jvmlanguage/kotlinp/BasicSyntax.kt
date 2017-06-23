package com.jiangli.jvmlanguage.kotlinp

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/8 18:31
 */
fun main(args: Array<String>) {
    val language = "tt"

    println(when (language) {
        "EN" -> "Hello!"
        "FR" -> "Salut!"
        "IT" -> "Ciao!"
        else -> "Sorry, I can't greet you in $language yet"
    })
//    val aaa="EN" -> "Hello!"
    //if here
    //it is called
    fun max(a: Int, b: Int) = if (a > b) a else b

    println(max(1,3))

    //if here
    //the outer one is called
//    fun max(a: Int, b: Int) = if (a > b) a else b


    if (args.size >= 2) {
        val x = parseInt(args[0])
        val y = parseInt(args[1])
        if (x != null && y != null) {
            print(x * y) // Now we can
        } else {
            println("One of the arguments is null")
        }
    }

    println("" is Any)  //equals object
    println(1 is Any)
    println("" is String)
    println(null is Any) // false
    println(false is Any)

    val arr = arrayOf(1, 2, 3);
    val intArr = intArrayOf(1, 2, 3)    //同理还有 booleanArrayOf() 等
    val asc = Array(5, { i -> i * i })  //0,1,4,9,16
//    val ascSS = Array<String>(5, { i -> (i * i).toString().plus("X") })
    val ascSS = Array(5, { i -> (i * i).toString().plus("X") }) //ok

    fun p(a:Any)=println(a)

//    ascSS.forEach { ::p } //no result
    ascSS.forEach(::p)//0x 1x 4x 6x 9x
//    ascSS.forEach { a->p(a) } // 0x 1x 4x 6x 9x
//    ascSS.forEach ({a->p(a)})// 0x 1x 4x 6x 9x
    val t2 =  { ::p }
    p(t2.javaClass) //class com.jiangli.jvmlanguage.kotlinp.BasicSyntaxKt$main$t2$1
    val t3 =  ::p
    p(t3.javaClass) //class com.jiangli.jvmlanguage.kotlinp.BasicSyntaxKt$main$t3$1

    val empty = emptyArray<Int>()
    println(asc[3])
    println(asc.get(3))
    val fixedSizeArray = arrayOfNulls<Int>(5)
    println(empty.lastIndex)
    println(fixedSizeArray.lastIndex)
    println(fixedSizeArray[2])

    for(eac in asc){
        print("$eac ")
    }
    println()

    //遍历下标
    for(idx in asc.indices){
        print(idx)
    }
    println("---foreach---")

    asc.forEach{a->println(a)}
//    asc.forEach({a->println(a)}) //also ok

    fun log2(a: Any) = println(a)

    log2("hahahah--------")
    log2("hahahah $asc")

//    val status=1
    val status=1.32
    val srs = doSth(status) //equal 1 prev
    println(srs)

    var k=123
    val t = when(k){
        20 -> 20
        else -> 33
    }
    println(t)


    log2("aaaa".plus("adasdsa"))


    //RxJava
    var a = (0 until 100)
    p(a)
    var b = 0..99
    p(b)

    val map = a.map { it }
    println(map)

    println(9 downTo 0 )
    for (x in 9 downTo 0 step 3) {
        p(x)
    }

    //Kotlin 中有一个专为 Android 开发量身打造的库，名为 anko，其中包含了许多可以简化开发的代码，其中就对线程进行了简化。
//    val filter = map.filter { it % 2 == 0 }
//    val filter = map.filter ( { y ->  y % 2 == 0 })
    val filter = map.filter {y ->  y % 2 == 0 }
    println(filter)

    SingleInstance.doSth()

//    val ins =  SingleInstance() // compile error


    //dsl style
//    android {
//        compileSdkVersion 23
//        buildToolsVersion "23.0.2"
//
//        defaultConfig {
//            applicationId "com.zll.demo"
//            minSdkVersion 15
//            targetSdkVersion 23
//            versionCode 1
//            versionName "1.0"
//        }
//        buildTypes {
//            release {
//                minifyEnabled false
//                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//            }
//        }
//    }



    println("asdasdsa".extentionFun())

//    var x = null as Int
//    var y = null as Int
//    p(x?*y?)

    var x:Int?=null
    val y = x?:-1
    println(y)

    (5..100 step 2).mapNotNull { when(it){
       in 20..30->it
        else ->null
    } }.forEach{
        println(it)
    }
}
fun doSth(status: Any):Any? {
    when(status){
        1 -> println("equal 1 prev")
        is Int -> println("is int")
        1 -> println("equal 1 after")
        is Boolean -> println("is Boolean")
        else -> return status //return can't be emitted
    }
    return null
}

fun max(a: Int, b: Int) = if (a < b) a else b

// Return null if str does not hold a number
fun parseInt(str: String): Int? { //? marks nullable
    try {
        return str.toInt()
    } catch (e: NumberFormatException) {
        println("One of the arguments isn't Int")
    }
    return null
}


fun cases(obj: Any) {
    when (obj) {
        1 -> println("One")
        "Hello" -> println("Greeting")
        is Long -> println("Long")
        !is String -> println("Not a string")
        else -> println("Unknown")
    }
}

object SingleInstance{
    fun doSth(){
        println("do sth")
//        "asd"
    }
}

fun String.extentionFun(){
    //like String.prototype.extentionFun = xxx
    println("String.extentionFun called $this")
}
