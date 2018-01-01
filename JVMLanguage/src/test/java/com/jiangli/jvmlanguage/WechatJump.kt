package com.jiangli.jvmlanguage

import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))

    val ratio = 700.0/33
    while (true) {
        try {
            val readLine = reader.readLine()

            val ps = readLine.toDouble() * ratio
            println("${readLine.toDouble()} -> $ps")


            Runtime.getRuntime().exec("adb shell input swipe 250  250 250 250 ${ps.toInt()}")
        } catch (e: Exception) {
            println("error ,re-input")
            e.printStackTrace()
        }
    }

//    input swipe 250  250 250 250 859
//    input tap 50 250 500
}