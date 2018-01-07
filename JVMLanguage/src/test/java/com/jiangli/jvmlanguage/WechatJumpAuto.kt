package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.analysePath
import com.jiangli.jvmlanguage.Consts.clearMobilePics
import com.jiangli.jvmlanguage.Consts.cycleLearningMode
import com.jiangli.jvmlanguage.Consts.height
import com.jiangli.jvmlanguage.Consts.randomJump
import com.jiangli.jvmlanguage.Consts.randomSleep
import com.jiangli.jvmlanguage.Consts.remainFailedPics
import com.jiangli.jvmlanguage.Consts.width
import com.jiangli.jvmlanguage.GlobalContext.screenshotFilePath
import com.jiangli.jvmlanguage.cmd.CmdThread


var loopTimes = 999999

fun main(args: Array<String>) {
    clearMobileTempDir()
    analyseClear()

    //输入线程
    Thread(CmdThread()).start()

    while (true) {
        try {
            //截图分析
            val screenshot = screenshot(analysePath)

            var presstime = analyse(screenshot)

            //最小跳跃时间
            if (presstime > 0) {
            } else {
                presstime = rnd(randomJump)
                log("分析不出来,随便跳了....$presstime ms")
            }

            val tapPoint = rndTapPoint()

            //按键跳跃
            val cmd = "${Consts.adbPath}adb shell input swipe ${tapPoint.x}  ${tapPoint.y} ${tapPoint.x} ${tapPoint.y} $presstime"
            log("执行指令:  $cmd")

            Runtime.getRuntime().exec(cmd)

            //等待跳跃动画
            val sleepTs = rnd(randomSleep).toLong()
            log("休息一下....$sleepTs ms")
            Thread.sleep(sleepTs)

            log("--------------------------------")

        } catch (e: Exception) {
            e.printStackTrace()

            if (clearMobilePics) {
                log("开始移除手机临时文件夹...")
            }
            if (remainFailedPics) {
                log("开始PC临时文件夹...")

                var lastPath: String? = when (GlobalContext.screenshotFilePath.size) {
                    0,1 -> null
                    else -> screenshotFilePath[screenshotFilePath.lastIndex - 1]
                }

                lastPath?.let {
                    log("最后截图: $it")

                    analyseRetain(it)
                }

                clearMobileTempDir()
                analyseClear()
                log("重新分析全部...")
                analyseAll()
            }

            //非学习模式返回
            if (!cycleLearningMode) {
                return
            }

            log("循环开始...剩余 ${--loopTimes} 次")
            if (loopTimes<0) {
                return
            }
            //若为学习模式,继续
            Thread.sleep(2000)

            //点击开始游戏
            val exec = Runtime.getRuntime().exec("${Consts.adbPath}adb shell input swipe ${width / 2}  ${height*82/100} ${width / 2} ${height*82/100} 123")
            exec.waitFor()

            Thread.sleep(5000)
        }
    }
}