package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.analysePath
import com.jiangli.jvmlanguage.Consts.clearMobilePics
import com.jiangli.jvmlanguage.Consts.randomJump
import com.jiangli.jvmlanguage.Consts.randomSleep
import com.jiangli.jvmlanguage.Consts.remainFailedPics
import com.jiangli.jvmlanguage.GlobalContext.screenshotFilePath

fun main(args: Array<String>) {
    try {
        while (true) {
            //截图分析
            val screenshot = screenshot(analysePath)

            var presstime = analyse(screenshot)

            //最小跳跃时间
            if (presstime > 0) {
            } else {
                presstime = rnd(randomJump)
                println("分析不出来,随便跳了....$presstime ms")
            }

            val tapPoint = rndTapPoint()

            //按键跳跃
            val cmd = "${Consts.adbPath}adb shell input swipe ${tapPoint.x}  ${tapPoint.y} ${tapPoint.x} ${tapPoint.y} $presstime"
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

        if (clearMobilePics) {
            println("开始移除手机临时文件夹...")
        }
        if (remainFailedPics) {
            println("开始PC临时文件夹...")

            var lastPath:String? = when(GlobalContext.screenshotFilePath.size){
                0 -> null
                1 -> screenshotFilePath[0]
                else -> screenshotFilePath[screenshotFilePath.lastIndex-1]
            }

            lastPath?.let {
                println("最后截图: $it")

                analyseRetain(it)
                analyseClear()
                println("重新分析全部...")
                analyseAll()
            }
        }
    }
}