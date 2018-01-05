package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.analysePath
import com.jiangli.jvmlanguage.Consts.rule
import java.io.BufferedReader
import java.io.InputStreamReader


fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))

    while (true) {
        try {
            log("按键跳跃.")
            //截图分析
            val screenshot = screenshot(analysePath)

            val presstime = analyse(screenshot)

            //最小跳跃时间

            if (presstime > 0) {
                //按键跳跃
                Runtime.getRuntime().exec("${Consts.adbPath}adb shell input swipe 250  250 250 250 ${presstime}")
            } else {
                log("请输入距离手动执行....")

                val dis = reader.readLine()

                //按键跳跃
                Runtime.getRuntime().exec("${Consts.adbPath}adb shell input swipe 250  250 250 250 ${rule(dis)}")
            }

            //等待跳跃动画
//            Thread.sleep(1000)

            log("--------------------------------")
        } catch (e: Exception) {
            log("error ,re-input")
            e.printStackTrace()
        }
    }

}