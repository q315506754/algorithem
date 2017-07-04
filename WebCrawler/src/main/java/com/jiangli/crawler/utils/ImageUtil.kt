package com.jiangli.crawler.utils

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL




object ImageUtil{
    fun getFileName(url:String): String {
        val fileName = url.substring(url.lastIndexOf("/")+1)
        return fileName
    }

    fun save(url:String, path:String,allowDup:Boolean = false) {
        val fileType = getFileName(url)
        val outFilePath = PathUtil.buildPath(path,false, fileType)
        var outFile = File(outFilePath)
        if(!allowDup && outFile.exists()){
            outFile = FileUtil.getNoDupfile(outFile)
        }

        println(outFile)


        val connection = URL(url).openConnection() as HttpURLConnection

        //设置请求方式为"GET"
        connection.requestMethod = "GET"
        //超时响应时间为5秒
        connection.connectTimeout = 5 * 1000

        val inputStream = connection.inputStream
        // 1K的数据缓冲
        val bs = ByteArray(1024)
        // 读取到的数据长度
        var len: Int
        // 输出的文件流
        val os = FileOutputStream(outFile)

        // 开始读取
        len = inputStream.read(bs)
        while (len !== -1) {
            os.write(bs, 0, len)
            len = inputStream.read(bs)
        }

        inputStream.close()
        os.close()
    }
}

fun main(args: Array<String>) {
    ImageUtil.save("http://imgs.gamersky.com/upimg/2017/201707041145462163.jpg", "D:\\")

}
