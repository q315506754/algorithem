package com.jiangli.jvmlanguage

import java.awt.image.BufferedImage
import java.io.File

/**
 *
 *  基础类
 *
 * @author Jiangli
 * @date 2018/1/4 10:30
 */

val MARK_COLOR:Int = RGB(255,0,0).toInt()

object Consts{
    var width:Int = 1080
    var height:Int = 1920
    var startScanY:Int = 550

    var pixelFactor = 1.429
    var rulerFactor = 700.0/33

    //小人配置
    var manStartColor = RGB(54,58,99)
    var manEndColor = RGB(58,58,102)
    var manOffsetY=11

    //精准白圈配置
    var accuratePointColor = RGB(245,245,245)


    fun rule(dbl:String):Int {
        return rule(dbl.toDouble())
    }
    fun rule(dbl:Double):Int {
        return (dbl * rulerFactor).toInt()
    }
}

data class RGB(var r:Int=0,var g:Int=0,var b:Int=0) {
    fun toInt():Int{
        return b + (g shl 8) + (r shl 16)
    }

    operator fun minus(o:RGB):RGB {
        return RGB(this.r-o.r,this.g-o.g,this.b-o.b)
    }

    fun error():Int {
        return maxOf(Math.abs(this.r),Math.abs(this.r),Math.abs(this.b))
    }

}

fun Int.toRGB(): RGB {
    return RGB(this shr 16 and 0xff,this shr 8 and 0xff,this  and 0xff)
}

data class Point(var x:Int=0,var y:Int=0) {
    fun valid():Boolean {
        return this.x > 0 && this.y > 0
    }

    fun distance(p2:Point):Double{
        return Math.sqrt(Math.pow((this.x-p2.x).toDouble(),2.0)+Math.pow((this.y-p2.y).toDouble(),2.0))
    }
}

fun paintLine(img:BufferedImage,pfrom:Point,pto:Point,rgb:Int=0) {
    if (pto.x == pfrom.x) {
        (Math.min(pfrom.y,pto.y)..Math.max(pfrom.y,pto.y)).forEach {
            img.setRGB(pto.x,it,rgb)
        }
        return
    }

    val slope:Double = (pto.y - pfrom.y)*1.0/(pto.x - pfrom.x)
    val c:Double = pto.y - slope*pto.x

    if( Math.abs(pto.y - pfrom.y) >  Math.abs(pto.x - pfrom.x)) {
        (Math.min(pfrom.y,pto.y)..Math.max(pfrom.y,pto.y)).forEach {
            img.setRGB(((it-c)/slope).toInt(),it,rgb)
        }
    } else {
        (Math.min(pfrom.x,pto.x)..Math.max(pfrom.x,pto.x)).forEach {
            img.setRGB(it,(slope*it+c).toInt(),rgb)
        }
    }
}

fun getAccuratePoint(HEIGHT: Int, WIDTH: Int, sourceImage: BufferedImage): Point {
    var accStart = -1
    var accEnd = -1
    var accY = -1
    var disSame = 0

    fun colorPrecisionCheck(posX:Int, posY:Int) =  (sourceImage.getRGB(posX, posY).toRGB() - Consts.accuratePointColor).error() < 1

    for (posY in Consts.startScanY until HEIGHT) {
        var b = false

        var t_accStart = -1
        var t_accEnd = -1
        var t_accY = -1
        for (posX in 0 until WIDTH) {
            if (colorPrecisionCheck(posX, posY)) {
//                    println(sourceImage.getRGB(it,posY).toRGB())
                t_accStart = posX
                t_accY = posY
                b = true
                break
            }
        }

        if (b) {
            for (posX in WIDTH - 1 downTo 0) {
                if (colorPrecisionCheck(posX, posY)) {
                    t_accEnd = posX
                    break
                }
            }

            //距离变长
            val disDiff = (t_accEnd - t_accStart) - (accEnd - accStart)
            if (disDiff > 0) {
                disSame = 0

                var cons = true
                for (eX in t_accStart..t_accEnd) {
                    if (!colorPrecisionCheck(eX, t_accY) ) {
                        cons = false
                        break
                    }
                }

                //中间连续为白色
                if (cons) {
                    println("const $t_accStart,$t_accEnd,${t_accEnd-t_accStart},$t_accY")


                    accStart = t_accStart
                    accEnd = t_accEnd
                    accY = t_accY
                }
            } else if (disDiff == 0) {
                disSame ++
            } else {

            }
        }

    }
    println("!!!y修正前 $accY")
    accY = (accY + disSame/2)
    println("!!!y修正后 $accY")

    println("!!!精准白椭圆端点 $accStart,$accY $accEnd,$accY")

//    paintLine(sourceImage, Point(accStart + 1, accY), Point(accEnd - 1, accY), MARK_COLOR)

    return Point(((accStart + accEnd) / 2),accY)
}

fun getManPoint(WIDTH: Int,HEIGHT: Int,  sourceImage: BufferedImage): Point {
    var manStart = -1
    var manEnd = -1
    var manY = -1

    for (posY in HEIGHT - 1 downTo Consts.startScanY) {
        var b = false

        for (posX in 0 until WIDTH) {
            if ((sourceImage.getRGB(posX, posY).toRGB() - Consts.manStartColor).error() < 4) {
//                    println(sourceImage.getRGB(it,posY).toRGB())
                manStart = posX
                manY = posY
                b = true
                break
            }
        }

        if (b) {
            for (posX in WIDTH - 1 downTo 0) {
                if ((sourceImage.getRGB(posX, posY).toRGB() - Consts.manEndColor).error() < 4) {
                    manEnd = posX
                    break
                }
            }
            break
        }

    }
    println("!!!小人端点 $manStart,$manY $manEnd,$manY")

    paintLine(sourceImage, Point(manStart + 1, manY), Point(manEnd - 1, manY), MARK_COLOR)

    return Point(((manEnd + manStart) / 2),manY-Consts.manOffsetY)
}

fun screenshot(output:String?="C:/Users/Jiangli/Desktop"): String {
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


    val outputAbsPath = "$output\\$currentTimeMillis-$mobileShotName"
    File(outputAbsPath).let {
        if (it.exists())
            it.delete()
    }

    //拉至本地
    val outputCmd = "adb pull $absName $outputAbsPath"
    println(outputCmd)
    Runtime.getRuntime().exec(outputCmd)
    Thread.sleep(1000)

    //分析 计算出按键时长

    //等待特殊板块奖分时间 3s

    return outputAbsPath
}