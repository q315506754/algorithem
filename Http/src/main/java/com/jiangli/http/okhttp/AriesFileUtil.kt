package com.jiangli.http.okhttp

import com.google.gson.Gson
import com.jiangli.common.utils.FileUtil
import okhttp3.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 *
 * @author Jiangli
 * @date 2019/8/30 17:02
 */
fun main(args: Array<String>) {
    val client = OkHttpClient.Builder()
            // 连接池最大空闲连接30，生存周期30秒(空闲30秒后将被释放)
            .connectionPool(ConnectionPool(30, 30, TimeUnit.SECONDS))
            // 连接超时，10秒，失败时重试，直到10秒仍未连接上，则连接失败
            .connectTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true)
            // 写超时，向服务端发送数据超时时间
            .writeTimeout(10, TimeUnit.SECONDS)
            // 读超时，从服务端读取数据超时时间
            .readTimeout(10, TimeUnit.SECONDS).build()

    FileUtil.acceptDragFile(false){
        val sb=StringBuilder()

        it.forEach {
            // 设置文件以及文件上传类型封装
            val mimeType = Files.probeContentType(Paths.get(it.absolutePath))

            val requestBody:RequestBody = RequestBody.create(MediaType.parse(mimeType),it)

            // 文件上传的请求体封装
            val multipartBody:MultipartBody =  MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("userId", "42")
                    .addFormDataPart("appName", "ablecommons")
                    .addFormDataPart("qqpartindex", "0")
                    .addFormDataPart("qqtotalparts", "1")
                    .addFormDataPart("qquuid", UUID.randomUUID().toString())
                    .addFormDataPart("videoConvert", "true")
                    .addFormDataPart("autoConvert", "true")
                    .addFormDataPart("qqtotalfilesize", it.length().toString())
                    .addFormDataPart("qqfile", it.getName())
                    .addFormDataPart("qqfilename", it.getName())
                    .addFormDataPart("file", it.getName(), requestBody)
                    .build()

            
            val request = Request.Builder().url("http://base1.g2s.cn/aries-commons/upload/receiver")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                    .addHeader("Referer", "http://base1.g2s.cn/aries-commons/resources/cdn/letv/uploadLetv.jsp")
                    .addHeader("Host", "base1.g2s.cn")
                    .addHeader("Origin", "Origin")
                    //.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param))
                    .post(multipartBody).build()

            try {
                val execute = client.newCall(request).execute()
//                println(execute)

                val body = execute.body()
//                println(body)
                val message = body!!.string()
//                println(message)

                val gson = Gson();
                val fromJson = gson.fromJson(message, Map::class.java)
                val dt = fromJson["data"] as Map<*, *>
                val filePath = dt["filePath"]
                val fileId = dt["fileId"]
//                println(filePath)
                val s = "${it.name} -> id:${fileId} path: ${filePath}\r\n "
                println(s)

                sb.append(s)
            } catch (e: Exception) {
                e.printStackTrace()
                sb.append( "[异常]${it.name} -> ${e.message}\r\n")
            }

        }

        sb.toString()
    }
}