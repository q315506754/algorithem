package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.pixelFactor
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO


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
    val accuratePoint = getAccuratePoint(HEIGHT, WIDTH, sourceImage)

//        val output = ByteArrayOutputStream()

    if (accuratePoint.valid()) {
        println("[Mode]目标白点模式")
        //标记-精准白点中心点
        sourceImage.setRGB(accuratePoint.x,accuratePoint.y,MARK_COLOR)

        paintLine(sourceImage, accuratePoint,manPoint, MARK_COLOR)

        val distance = accuratePoint.distance(manPoint)
        pressts = (distance * pixelFactor).toInt()
        println("distance: $distance")
        println("presstime: $pressts")
    }


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

    println("----------------------------------")

    return pressts
}

fun main(args: Array<String>) {
    val dirpath = "C:\\Users\\DELL-13\\Desktop\\temppic"

    File(dirpath).listFiles().forEach {
        analyse(it)
    }
}

