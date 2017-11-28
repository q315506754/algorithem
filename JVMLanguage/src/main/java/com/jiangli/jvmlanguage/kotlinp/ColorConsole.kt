package com.jiangli.jvmlanguage.kotlinp


object ConsoleColorHelper {
    val reset = "\u001B[0m"

    enum class Pos(val s: String) {
        fore("3"),
        back("4")
    }

    object Style {
        val bold = "\u001b[1m"
        val underline = "\u001b[4m"
        val revcolor = "\u001b[7m"
    }

    fun c8s(str: String, color: ConsoleColor = ConsoleColor.WHITE, pos: Pos = Pos.fore): String = "${c8(color, pos)}$str$reset"
    fun c8(color: ConsoleColor = ConsoleColor.WHITE, pos: Pos = Pos.fore): String = "\u001b[${pos.s}${color.code}m"
    fun c16s(str: String, color: ConsoleColor = ConsoleColor.WHITE, pos: Pos = Pos.fore): String = "${c16(color, pos)}$str$reset"
    fun c16(color: ConsoleColor = ConsoleColor.WHITE, pos: Pos = Pos.fore): String = "\u001b[${pos.s}${color.code}m;1"
}

enum class ConsoleColor(val code: Int) {
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    LRED(5),
    LBLUE(6),
    WHITE(7)
}

/**
 *
 *
 * @author Jiangli
 * @date 2017/11/28 13:31
 */
fun main(args: Array<String>) {
    println("aa")

    //ANSI ESCAPE CODE
    //背景色
    // 8
    (0..7).forEach {
        println("8 color:\u001b[3${it}m 3${it}m ${ConsoleColorHelper.reset}")
    }

    // 16
    (0..7).forEach {
        println("16 color:\u001b[3${it}m 3${it}m ${ConsoleColorHelper.reset} \u001b[3${it};1m 3${it};1m ${ConsoleColorHelper.reset}")
    }

    //256 = 16*16
    //\u001b[38;5;${ID}m
    (0..15).forEach { x ->
        print("256 color:")
        (0..15).forEach { y ->
            val id = x * 16 + y
            System.out.printf("\u001b[38;5;${id}m %-4s ${ConsoleColorHelper.reset}",id)
//            print("\u001b[38;5;${id}m ${id}\t ${ConsoleColorHelper.reset}")
        }
        println()
    }

    println("--------------font color--------------")
    //前景色
    // 8
    (0..7).forEach {
        println("8 color:\u001b[4${it}m 4${it}m ${ConsoleColorHelper.reset}")
    }

    // 16
    (0..7).forEach {
        println("16 color:\u001b[4${it}m 4${it}m ${ConsoleColorHelper.reset} \u001b[4${it};1m 4${it};1m ${ConsoleColorHelper.reset}")
    }

    //256 = 16*16
    //\u001b[48;5;${ID}m
    (0..15).forEach { x ->
        print("256 color:")
        (0..15).forEach { y ->
            val id = x * 16 + y
            print("\u001b[48;5;${id}m ${id} ${ConsoleColorHelper.reset}")
        }
        println()
    }

    println(ConsoleColorHelper.c8s("哈哈哈"))
    println(ConsoleColorHelper.c8s("哈哈哈", ConsoleColor.RED))
    println("${ConsoleColorHelper.c8(ConsoleColor.RED)}${ConsoleColorHelper.Style.bold}${ConsoleColorHelper.Style.underline}${ConsoleColorHelper.Style.revcolor}呵呵呵呵${ConsoleColorHelper.reset}")

    println("--------------sf--------------")

    println("上上\u001b[100A插1")
    println("下下\u001b[100B插2")
    println("右右\u001b[100C插3")
    println("左左\u001b[100D插4")
    print("左左")
    print("\u001b[1000D插4")
    (0..100).forEach {
        Thread.sleep(100)
        print("\u001b[1000D$it%")
    }

}