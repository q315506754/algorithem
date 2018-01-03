package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.startScanY
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO


val MARK_COLOR:Int = RGB(255,0,0).toInt()

object Consts{
    var width:Int = 1080
    var height:Int = 1920
    var startScanY:Int = 550

    //小人配置
    var manStartColor = RGB(54,58,99)
    var manEndColor = RGB(58,58,102)
    var manOffsetY=11

    //精准白圈配置
    var accuratePointColor = RGB(245,245,245)
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

fun main(args: Array<String>) {
    val dirpath = "C:\\Users\\DELL-13\\Desktop\\temppic"

    File(dirpath).listFiles().forEach {
        if (it.isDirectory) {
            return@forEach
        }

        val sourceImage = ImageIO.read(FileInputStream(it))
//        println(sourceImage)
        println("input:"+it)

        val WIDTH = sourceImage.width
        val HEIGHT = sourceImage.height
//        println("$WIDTH x $HEIGHT")

        //小人中心点
        val (manX, manY) = getManPoint(WIDTH,HEIGHT,sourceImage)

        //标记-小人中心点
        sourceImage.setRGB(manX,manY,MARK_COLOR)


        //精准白点中心点
        val (accX, accY) = getAccuratePoint(HEIGHT, WIDTH, sourceImage)

        //标记-精准白点中心点
        sourceImage.setRGB(accX,accY,MARK_COLOR)

//        val output = ByteArrayOutputStream()

        File(dirpath + "\\rs").mkdirs()
        val file = File(dirpath + "\\rs\\" + it.name)
        if (!file.exists()) {
            file.createNewFile()
        }
        println("output:"+file)
        val output = FileOutputStream(file)
        ImageIO.write(sourceImage, "png", output)
        output.flush()
        output.close()

        println("----------------------------------")
    }
}

private fun getAccuratePoint(HEIGHT: Int, WIDTH: Int, sourceImage: BufferedImage): Point {
    var accStart = -1
    var accEnd = -1
    var accY = -1
    for (posY in startScanY until HEIGHT) {
        var b = false

        var t_accStart = -1
        var t_accEnd = -1
        var t_accY = -1
        for (posX in 0 until WIDTH) {
            if ((sourceImage.getRGB(posX, posY).toRGB() - Consts.accuratePointColor).error() < 1) {
//                    println(sourceImage.getRGB(it,posY).toRGB())
                t_accStart = posX
                t_accY = posY
                b = true
                break
            }
        }

        if (b) {
            for (posX in WIDTH - 1 downTo 0) {
                if ((sourceImage.getRGB(posX, posY).toRGB() - Consts.accuratePointColor).error() < 1) {
                    t_accEnd = posX
                    break
                }
            }

            //距离变长
            if ( (t_accEnd - t_accStart) > (accStart - accEnd) ) {
                var cons = true
                for (eX in t_accStart..t_accEnd) {
                    if (sourceImage.getRGB(eX, t_accY).toRGB() != Consts.accuratePointColor) {
                        cons = false
                        break
                    }
                }

                //中间连续为白色
                if (cons) {
                    accStart = t_accStart
                    accEnd = t_accEnd
                    accY = t_accY
                }
            }
        }

    }
    println("!!!精准白椭圆端点 $accStart,$accY $accEnd,$accY")

    paintLine(sourceImage, Point(accStart + 1, accY), Point(accEnd - 1, accY), MARK_COLOR)

    return Point(((accStart + accEnd) / 2),accY)
}

private fun getManPoint(WIDTH: Int,HEIGHT: Int,  sourceImage: BufferedImage): Point {
    var manStart = -1
    var manEnd = -1
    var manY = -1

    for (posY in HEIGHT - 1 downTo startScanY) {
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