package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.randomJump
import com.jiangli.jvmlanguage.Consts.randomSleep
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    val reader = BufferedReader(InputStreamReader(System.`in`))

//    val dirpath = "C:\\Users\\DELL-13\\Desktop\\temppic"
    val dirpath = "C:\\Users\\Jiangli\\Desktop\\temppic"

    try {
        while (true) {
            //截图分析
            val screenshot = screenshot(dirpath)

            var presstime = analyse(screenshot)

            //最小跳跃时间
            if (presstime > 0) {
            } else {
                presstime = rnd(randomJump)
                println("分析不出来,随便跳了....$presstime ms")
            }

            val tapPoint = rndTapPoint()

            //按键跳跃
            val cmd = "adb shell input swipe ${tapPoint.x}  ${tapPoint.y} ${tapPoint.x} ${tapPoint.y} $presstime"
            println("执行指令:  $cmd")

            Runtime.getRuntime().exec(cmd)

            //等待跳跃动画
            val sleepTs = rnd(randomSleep).toLong()
            println("休息一下....$sleepTs ms")
            Thread.sleep(sleepTs)

            println("--------------------------------")

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}