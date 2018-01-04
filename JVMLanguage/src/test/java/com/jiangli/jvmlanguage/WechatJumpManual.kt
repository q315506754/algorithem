package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.rule
import java.io.BufferedReader
import java.io.InputStreamReader


fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))

    val dirpath = "C:\\Users\\DELL-13\\Desktop\\temppic"

    while (true) {
        try {
            println("按键跳跃.")
            val readLine = reader.readLine()

            //截图分析
            val screenshot = screenshot(dirpath)

            val presstime = analyse(screenshot)

            if (presstime > 0) {
                //按键跳跃
                Runtime.getRuntime().exec("adb shell input swipe 250  250 250 250 ${presstime}")
            } else {
                println("请输入距离手动执行....")

                val dis = reader.readLine()

                //按键跳跃
                Runtime.getRuntime().exec("adb shell input swipe 250  250 250 250 ${rule(dis)}")
            }

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