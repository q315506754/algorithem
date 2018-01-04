package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.minPress
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val dirpath = "C:\\Users\\DELL-13\\Desktop\\temppic"

    File(dirpath).listFiles().forEach {
        analyse(it)
    }
}


fun analyse(file:String): Int {
    return analyse(File(file))
}
fun analyse(file:File): Int {
    var pressts = -1
    if (file.isDirectory) {
        return pressts
    }

    val sourceImage = ImageIO.read(FileInputStream(file))
//        println(sourceImage)
    println("input:"+file)

    val WIDTH = sourceImage.width
    val HEIGHT = sourceImage.height

    //小人中心点
    val manPoint = getManPoint(WIDTH, HEIGHT, sourceImage)

    //标记-小人中心点
    sourceImage.setRGB(manPoint.x,manPoint.y,MARK_COLOR)

    //精准白点中心点
    var targetPoint = getAccuratePoint(HEIGHT, WIDTH, sourceImage)


    if (targetPoint.valid()) {
        println("[Mode]目标白点模式")
        //标记-精准白点中心点
        sourceImage.setRGB(targetPoint.x,targetPoint.y,MARK_COLOR)

        paintLine(sourceImage, targetPoint,manPoint, MARK_COLOR)
    } else {
        targetPoint = getGeometryPoint(manPoint, WIDTH, HEIGHT, sourceImage)

        if (!targetPoint.valid()) {
            println("[Mode]所有模式均不可用")
            return pressts
        }

        println("[Mode]几何分析模式")

        //标记-几何中心点
        sourceImage.setRGB(targetPoint.x,targetPoint.y,MARK_COLOR)

        paintLine(sourceImage, targetPoint,manPoint, MARK_COLOR)
    }


    val distance = targetPoint.distance(manPoint)
    pressts = (distance * Consts.pixelFactor).toInt()
    println("distance: $distance")
    println("presstime: $pressts")

    //output
    File(file.parent + "\\rs").mkdirs()
    val file = File(file.parent + "\\rs\\" + file.name)
    if (!file.exists()) {
        file.createNewFile()
    }
    println("output:"+file)
    val output = FileOutputStream(file)
    ImageIO.write(sourceImage, "png", output)
    output.flush()
    output.close()



    if (pressts < minPress) {
        pressts = minPress
        println("---------按压时间修正为:$minPress------------")
    }

    println("----------------------------------")
    return pressts
}





