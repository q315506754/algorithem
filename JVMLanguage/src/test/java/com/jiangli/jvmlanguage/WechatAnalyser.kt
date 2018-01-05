package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.analysePath
import com.jiangli.jvmlanguage.Consts.minPress
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    analyseAll()
}


fun analyseAll() {
    File(analysePath).listFiles().forEach {
        analyse(it)
    }
}

fun analyseClear() {
    File(Consts.analysePath).listFiles().forEach {
//        println(it)
        if (it.isDirectory) {
            it.deleteRecursively()
        } else {
            if (it.name.matches(Regex("""\d+-screen\.png"""))) {
                println("remove $it")

                it.delete()
            }
        }
    }
}

fun analyseRetain(retainFile:String?=null) {
    File(Consts.analysePath).listFiles().forEach {
//        println(it)
        if (it.isDirectory) {
        } else {
            if (it.name.matches(Regex("""\d+-screen\.png"""))) {
                if (retainFile == null || it.absolutePath == retainFile) {
                    val newFile = File(it.parent, "retained-${System.currentTimeMillis()}-${rnd(1..999)}.png")
                    println("rename $it -> $newFile")

                    //1515113465457-screen
                    println(it.renameTo(newFile))
//                    copyFile(it,newFile)

                }
            }
        }
    }
}

@Throws(Exception::class)
fun copyFile(src: File, dest: File) {
    if(!dest.exists()){
        dest.createNewFile()
    }

    val length = 2097152
    val `in` = FileInputStream(src)
    val out = FileOutputStream(dest)
    val buffer = ByteArray(length)
    while (true) {
        val ins = `in`.read(buffer)
        if (ins == -1) {
            `in`.close()
            out.flush()
            out.close()
            return
        } else
            out.write(buffer, 0, ins)
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

    val inputStream = FileInputStream(file)
    val sourceImage = ImageIO.read(inputStream)
    inputStream.close()

//        println(sourceImage)
    println("input:"+file)

    val WIDTH = sourceImage.width
    val HEIGHT = sourceImage.height

    //小人中心点
    val manPoint = getManPoint(WIDTH, HEIGHT, sourceImage)

    //标记-小人中心点
    sourceImage.setRGB(manPoint.x,manPoint.y,MARK_COLOR)

    //精准白点中心点
    var targetPoint = getAccuratePoint(HEIGHT, WIDTH, sourceImage,manPoint)


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





