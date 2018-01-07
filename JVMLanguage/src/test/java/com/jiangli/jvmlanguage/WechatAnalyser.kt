package com.jiangli.jvmlanguage

import com.jiangli.jvmlanguage.Consts.analysePath
import com.jiangli.jvmlanguage.Consts.minPress
import com.jiangli.jvmlanguage.Consts.seq
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO
import kotlin.reflect.full.findAnnotation

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
//        log(it)
        if (it.isDirectory) {
            it.deleteRecursively()
        } else {
            if (it.name.matches(Regex("""\d+-screen\.png"""))) {
                log("remove $it")

                it.delete()
            }
        }
    }
}

fun analyseRetain(retainFile:String?=null) {
    File(Consts.analysePath).listFiles().forEach {
//        log(it)
        if (it.isDirectory) {
        } else {
            if (it.name.matches(Regex("""\d+-screen\.png"""))) {
                if (retainFile == null || it.absolutePath == retainFile) {
                    val newFile = File(it.parent, "retained-${System.currentTimeMillis()}-${rnd(1..999)}.png")
                    log("rename $it -> $newFile")

                    //1515113465457-screen
                    log(it.renameTo(newFile))
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

    log("input:"+file)

    val WIDTH = sourceImage.width
    val HEIGHT = sourceImage.height

    //小人中心点
    val manPoint = getManPoint(WIDTH, HEIGHT, sourceImage)

    //标记-小人中心点
    sourceImage.setRGB(manPoint.x,manPoint.y,MARK_COLOR)

    //精准白点中心点
    var targetPoint:Point?=null

    //依次执行，直至找到目标点
    for (func in seq) {
        val point = func.invoke(manPoint, WIDTH, HEIGHT, sourceImage)
        val mode = func.findAnnotation<Mode>()

        if (point.valid()) {
            targetPoint = point

            mode?.let {
                log("[Mode]${mode.desp}模式")
            }
            log("目标点: ${targetPoint}")
            break
        }
    }


    if (targetPoint!=null && targetPoint.valid()) {
        //标记-精准白点中心点
        sourceImage.setRGB(targetPoint.x,targetPoint.y,MARK_COLOR)

        paintLine(sourceImage, targetPoint,manPoint, MARK_COLOR)
    } else {
        log("[Mode]所有模式均不可用")
        return pressts
    }


    val distance = targetPoint.distance(manPoint)
    pressts = (distance * Consts.pixelFactor).toInt()
    log("distance: $distance")
    log("presstime: $pressts")

    //output
    File(file.parent + "\\rs").mkdirs()
    val file = File(file.parent + "\\rs\\" + file.name)
    if (!file.exists()) {
        file.createNewFile()
    }
    log("output:"+file)
    val output = FileOutputStream(file)
    ImageIO.write(sourceImage, "png", output)
    output.flush()
    output.close()


    if (pressts < minPress) {
        pressts = minPress
        log("---------按压时间修正为:$minPress------------")
    }

    log("----------------------------------")
    return pressts
}





