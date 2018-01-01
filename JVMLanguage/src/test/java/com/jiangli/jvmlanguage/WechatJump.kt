package com.jiangli.jvmlanguage

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun screenshot(path:String?="C:/Users/Jiangli/Desktop") {
    val mobileShotPath = "/sdcard/temp"
    val mobileShotName = "screen.png"
    val currentTimeMillis = System.currentTimeMillis()
//    Runtime.getRuntime().exec("adb shell rm  $mobileShotPath")
//    Runtime.getRuntime().exec("adb shell rm -r $mobileShotPath")
//    Runtime.getRuntime().exec("adb shell mkdir -r $mobileShotPath")
    val absName = "$mobileShotPath/$currentTimeMillis-$mobileShotName"
//    println(absName)

    //需要等待一会
    Runtime.getRuntime().exec("adb shell screencap $absName")
    Thread.sleep(1000)


    val outputAbsPath = "$path/screen.png"
    File(outputAbsPath).let {
        if (it.exists())
            it.delete()
    }

    //拉至本地
    val outputCmd = "adb pull $absName $outputAbsPath"
    println(outputCmd)
    Runtime.getRuntime().exec(outputCmd)

    //分析 计算出按键时长

    //等待特殊板块奖分时间 3s
}

fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))


    val ratio = 700.0/33
    while (true) {
        try {
            val readLine = reader.readLine()

            //截图分析
            screenshot()

            val ps = readLine.toDouble() * ratio
            println("${readLine.toDouble()} -> $ps")

            //按键跳跃
            Runtime.getRuntime().exec("adb shell input swipe 250  250 250 250 ${ps.toInt()}")

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