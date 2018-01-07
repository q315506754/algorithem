package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.adbPath
import com.jiangli.jvmlanguage.Consts.height
import com.jiangli.jvmlanguage.Consts.whiteDiameter
import com.jiangli.jvmlanguage.Consts.width
import com.jiangli.jvmlanguage.GlobalContext.screenshotFilePath
import java.awt.image.BufferedImage
import java.io.File
import java.util.*

/**
 *
 *  基础类
 *
 * @author Jiangli
 * @date 2018/1/4 10:30
 */

val MARK_COLOR:Int = RGB(255,0,0).toInt()

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mode(val desp: String)

object GlobalContext{
    var lastScreenshotFilePath:String? = null
    var screenshotFilePath: MutableList<String> = mutableListOf<String>()
    var print=true
}
 object Consts{
    //根据机型调整
    var width:Int = 1080
    var height:Int = 1920

    //(PC依赖)
    var adbPath:String = ""
//    var analysePath:String = "C:\\Users\\DELL-13\\Desktop\\temppic"
    var analysePath:String = "C:\\Users\\Jiangli\\Desktop\\temppic"

    //像素系数(尺寸依赖)
    var pixelFactor:Double = 1.451
//    var pixelFactor:Double = 1.392
    //尺量系数(尺寸依赖)
    val rulerFactor = 700.0/33

     //分析顺序
     //先几何 后白点
//     val seq = arrayListOf(::getGeometryPoint, ::getAccuratePoint)
     val seq = arrayListOf(::getAccuratePoint, ::getGeometryPoint)

    //最小按压时长  ms
    val minPress = 200

    //自动跳跃模式随机等待时长 ms
    val randomSleep = 2345..4567

    //无法识别模式时随机跳跃时长 ms
    val randomJump = minPress..1000

    //小人配置
    val manStartColor = RGB(54,58,99)
    val manEndColor = RGB(58,58,102)

    //旗子距离底部像素(尺寸依赖)
    val manOffsetY=10
//    val manOffsetY=20
    val whiteDiameter=35..40

    //白色目标点颜色判别
    val accuratePointColor = RGB(245,245,245)

     //异常结束后清除手机快照
     val clearMobilePics = true
     //异常结束后只保留识别失败的图片
     val remainFailedPics = true

     //失败后重新开始
     val cycleLearningMode = true

    fun rule(dbl:String):Int {
        return rule(dbl.toDouble())
    }
    fun rule(dbl:Double):Int {
        return (dbl * rulerFactor).toInt()
    }
}

fun log(x: Any?): Unit {
    if (Thread.currentThread().name=="main" && GlobalContext.print) {
        println(x)
    } else if (Thread.currentThread().name!="main"){
        println(x)
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

@Mode("寻找白点模式")
fun getAccuratePoint(manPoint: Point,WIDTH: Int,HEIGHT: Int,  sourceImage: BufferedImage): Point {
    var accStart = -1
    var accEnd = -1
    var accY = -1
    var disSame = 0

    fun colorPrecisionCheck(posX:Int, posY:Int) =  (sourceImage.getRGB(posX, posY).toRGB() - Consts.accuratePointColor).error() < 1


    var scanXStart: Int
    var scanXEnd: Int
    if (manPoint.x < WIDTH / 2) {
        scanXStart = manPoint.x
        scanXEnd = WIDTH - 1
    } else {
        scanXStart = 0
        scanXEnd = manPoint.x
    }

    for (posY in HEIGHT/3 until manPoint.y) {
        var b = false

        var t_accStart = -1
        var t_accEnd = -1
        var t_accY = -1
        for (posX in scanXStart until scanXEnd) {
            if (colorPrecisionCheck(posX, posY)) {
//                    log(sourceImage.getRGB(it,posY).toRGB())
                t_accStart = posX
                t_accY = posY
                b = true
                break
            }
        }

        if (b) {
            for (posX in scanXEnd downTo scanXStart) {
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
//                    log("找到距离 $t_accStart,$t_accEnd,${t_accEnd-t_accStart},$t_accY")

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
//    log("!!!y修正前 $accY")
    accY = (accY + disSame/2)
//    log("!!!y修正后 $accY")

    val diameter = accEnd - accStart
    log("!!!白点端点 ($accStart,$accY) - ($accEnd,$accY)  直径($diameter)")

//    paintLine(sourceImage, Point(accStart + 1, accY), Point(accEnd - 1, accY), MARK_COLOR)

    return if (whiteDiameter.contains(diameter)) {
        Point(((accStart + accEnd) / 2),accY)
    } else {
        log("!!!白点可能找错,使用下一种模式分析")
        Point(-1,-1)
    }
}

fun getManPoint(WIDTH: Int,HEIGHT: Int,  sourceImage: BufferedImage): Point {
    var manStart = -1
    var manEnd = -1
    var manY = -1

    for (posY in HEIGHT*2/3 downTo HEIGHT/3) {
        var b = false

        for (posX in WIDTH/8 until WIDTH) {
            if ((sourceImage.getRGB(posX, posY).toRGB() - Consts.manStartColor).error() < 4) {
//                    log(sourceImage.getRGB(it,posY).toRGB())
                manStart = posX
                manY = posY
                b = true
                break
            }
        }

        if (b) {
            for (posX in WIDTH*7/8 downTo 0) {
                if ((sourceImage.getRGB(posX, posY).toRGB() - Consts.manEndColor).error() < 4) {
                    manEnd = posX
                    break
                }
            }
            break
        }

    }
    val width = manEnd - manStart
    log("!!!小人端点 ($manStart,$manY) ($manEnd,$manY) 底长($width)")

    paintLine(sourceImage, Point(manStart + 1, manY), Point(manEnd - 1, manY), MARK_COLOR)

    return Point(((manEnd + manStart) / 2),manY-Consts.manOffsetY)
}

@Mode("几何寻点模式")
fun getGeometryPoint(manPoint: Point, WIDTH: Int, HEIGHT: Int, sourceImage: BufferedImage): Point {

    var scanXStart: Int
    var scanXEnd: Int
    if (manPoint.x < WIDTH / 2) {
        scanXStart = manPoint.x
        scanXEnd = WIDTH - 1
    } else {
        scanXStart = 0
        scanXEnd = manPoint.x
    }

    var boardSum: Int = 0
    var boardC: Int = 0
    var boardX: Int = -1
    var boardY: Int = -1
    for (y in HEIGHT / 3..HEIGHT * 2 / 3) {
        val firstPointOfLine = sourceImage.getRGB(0, y).toRGB()
        var b = false


        for (x in scanXStart..scanXEnd) {
            //修掉脑袋比下一个小格子还高的情况的 bug
            if (Math.abs(x - manPoint.x) < 80) {
                continue
            }

            //和纯色差距过大
            if ((sourceImage.getRGB(x, y).toRGB() - firstPointOfLine).error() > 10) {
                boardSum += x
                boardC += 1
                b = true
            }
        }

        if (b) {
            boardY = y
            break
        }
    }

    if (boardC > 0) {
        boardX = boardSum / boardC
    }

    val pointTop = Point(boardX, boardY)
    val pointTopColor = sourceImage.getRGB(pointTop.x, pointTop.y).toRGB()
    log("几何顶点: $pointTop")

    //寻找底点
    val pointBottom = Point(boardX, 0)

    //纯色矩阵-面中最下面一点周围3像素范围内刚好为3色
    var lastY = pointTop.y
    for (y in boardY..(boardY + 274)) {
        lastY = y
        if (sourceImage.getRGB(boardX, y).toRGB() != pointTopColor) {
            break
        }
    }
    if(sourceImage.rangeColorsOfPoint(boardX,lastY,3) == 3){
        log("!!三色矩阵找到底点....")
        pointBottom.y = lastY
    } else {
        //通用-底部往上寻相同色
        for (y in (boardY + 274) downTo boardY) {
            if ((sourceImage.getRGB(boardX, y).toRGB() - pointTopColor).error() < 10) {
                log("!!通用模式找到底点....")
                pointBottom.y = y
                break
            }
        }
    }
    

    log("几何底点: $pointBottom")

    val pointMiddle = Point(boardX, 0)
    pointMiddle.y = (pointTop.y + pointBottom.y) / 2
    val distance = pointMiddle.distance(pointTop)
    log("几何中点: $pointMiddle 距顶点距离:$distance")

    //间距过小 很可能算错 修正
    //此时可能方块过大
    if (distance<10) {
        pointMiddle.y = pointTop.y +100
        log("中点可能算错 ..尝试修正为:$pointMiddle")
    }

//    //标记-顶点
    sourceImage.setRGB(pointTop.x, pointTop.y, MARK_COLOR)
//    //标记-底点
    sourceImage.setRGB(pointBottom.x, pointBottom.y, MARK_COLOR)

//    //标记-中点
//    sourceImage.setRGB(pointMiddle.x, pointMiddle.y, MARK_COLOR)

    return pointMiddle
}

fun BufferedImage.rangeColorsOfPoint(boardX: Int, lastY: Int, radius: Int): Int {
    val setOf = mutableSetOf<Int>()
    for (x in (boardX-radius)..(boardX+radius)) {
        for (y in (lastY-radius)..(lastY+radius)) {
            setOf.add(getRGB(x, y))
        }
    }
    return setOf.size
}

fun screenshot(output:String?="C:\\Users\\Jiangli\\Desktop"): String {
    val ouputDir = File(output)
    if (!ouputDir.exists()) {
        ouputDir.mkdirs()
    }
    if (!ouputDir.isDirectory) {
        return ""
    }


    val mobileShotPath = "/sdcard/temp"
    val mobileShotName = "screen.png"
    val currentTimeMillis = System.currentTimeMillis()
//    Runtime.getRuntime().exec("adb shell rm  $mobileShotPath")
//    Runtime.getRuntime().exec("adb shell rm -r $mobileShotPath")
//    Runtime.getRuntime().exec("adb shell mkdir -r $mobileShotPath")
    val absName = "$mobileShotPath/$currentTimeMillis-$mobileShotName"

    //需要等待一会
    val process1 = Runtime.getRuntime().exec("${adbPath}adb shell screencap -p $absName")
    val millis1 = System.currentTimeMillis()
    process1.waitFor()
    log("截图耗时:${System.currentTimeMillis()-millis1} ms")


    val outputAbsPath = "$output\\$currentTimeMillis-$mobileShotName"
    val file = File(outputAbsPath)
    file.let {
        if (it.exists())
            it.delete()
    }
    //保存全局变量
    GlobalContext.lastScreenshotFilePath = outputAbsPath
    screenshotFilePath.add(outputAbsPath)

    //拉至本地
    val outputCmd = "${adbPath}adb pull $absName $outputAbsPath"
    log(outputCmd)
    val process = Runtime.getRuntime().exec(outputCmd)
    val millis2 = System.currentTimeMillis()
    process.waitFor()
    log("pull本地耗时:${System.currentTimeMillis()-millis2} ms")


    return outputAbsPath
}

fun rnd(pg:IntProgression): Int {
    return pg.first+Random().nextInt(pg.last-pg.first)
}

fun rndTapPoint(): Point {
    val x = Consts.width / 2
    val y = Consts.height * 3 / 4
    return Point(rnd((x-width/8)..(x+width/8)), rnd((y-height/8)..(y+height/8)))
}

fun clearMobileTempDir() {
    log("正在清理手机文件夹...")
    val process = Runtime.getRuntime().exec("${adbPath}adb shell rm -f /sdcard/temp/*")
    process.waitFor()
}