import com.jiangli.jvmlanguage.javap.HelloWorld

/**
 * We declare a package-level function main which returns Unit and takes
 * an Array of strings as a parameter. Note that semicolons are optional.
 */

fun main(args: Array<String>) {
        val user = HelloWorld()

    val message = user.javaClass
    println(message)
    
    val classLoader = message.classLoader
    println(classLoader)

//赋值
        user.name = "tutu"
        user.age = "23"
//取值
        val name = user.name
        val age = user.age

    for (name in args)
        println("Hello, $name!")

//    println("Hello, ${args[0]}!") // java.lang.ArrayIndexOutOfBoundsException: 0

    println(args.size)

        println("invoked")
        println(name)
        println(age)

        println("${name}") //tutu
        println("$name") //tutu
        println("${user.age}")
//        println("$user.age") //com.jiangli.jvmlanguage.javap.HelloWorld@77459877.age

    val language = if (args.size == 0) "EN" else args[0]
    println(when (language) {
        "EN" -> "Hello!"
        "FR" -> "Salut!"
        "IT" -> "Ciao!"
        else -> "Sorry, I can't greet you in $language yet"
    })

//    println(user.name.toInt())  //java.lang.NumberFormatException: For input string: "tutu"
    println(user.age.toInt())

//    var   not final
//    val   final
}