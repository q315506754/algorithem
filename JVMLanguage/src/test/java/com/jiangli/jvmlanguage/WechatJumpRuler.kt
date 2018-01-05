package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.rule
import java.io.BufferedReader
import java.io.InputStreamReader


fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))

    while (true) {
        try {
            val readLine = reader.readLine()

            val ps = rule(readLine)
            println("${readLine.toDouble()} -> $ps")

            //按键跳跃
            Runtime.getRuntime().exec("${Consts.adbPath}adb shell input swipe 250  250 250 250 ${ps}")

            //等待跳跃动画
//            Thread.sleep(1000)

            println("--------------------------------")
        } catch (e: Exception) {
            println("error ,re-input")
            e.printStackTrace()
        }
    }

    // adb shell screencap /sdcard/temp/screen.png
    // adb pull /sdcard/temp/screen.png C:/Users/Jiangli/Desktop/screen.png
    // adb shell rm -f /sdcard/temp/screen.png
    // adb shell rm -f /sdcard/temp
//    input swipe 250  250 250 250 859
//    input tap 50 250 500
}