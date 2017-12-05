package com.jiangli.http.downloader

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.*

object UrlFileDownloader {
    private val proxyHost: String? = null
    private val proxyPort: Int? = null

    @Throws(IOException::class)
    private fun openConnection(localURL: URL): URLConnection {
        val connection: URLConnection
        if (proxyHost != null && proxyPort != null) {
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(proxyHost, proxyPort))
            connection = localURL.openConnection(proxy)
        } else {
            connection = localURL.openConnection()
        }
        return connection
    }

    fun download(url:String,outPath:String){
        val connection = openConnection(URL(url))
        val httpURLConnection = connection as HttpURLConnection

        httpURLConnection.setRequestProperty("Accept-Charset", "utf8")
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

        val inputStream = httpURLConnection.inputStream

        val outputStream = FileOutputStream(outPath)
        IOUtils.copyLarge(inputStream, outputStream)
    }
}
fun main(args: Array<String>) {
    val filePath = "C:\\FormatAnything"
    val remoteBase = "https://www.bejson.com"
    val featureUrl = "/static/bejson/jsonview/ico1/"
    val outputBase = "C:\\downloaded_res"
    val suffixWhiteList = arrayListOf("css","js","html")


    val paths = FileUtil.getFilePathFromDirPath(filePath,true)
    println(paths)
    val pset = mutableSetOf<String>()

    //collect paths
    paths.filter {
        p ->
        if (suffixWhiteList.isEmpty())
           true
        else
            suffixWhiteList.any {
                p.endsWith("."+it)
            }
    }.forEach {
        FileUtil.processVisit(File(it)){
            line ->
            if (line.contains(featureUrl)) {
                var substring = line.substring(line.indexOf(featureUrl))

                arrayListOf(
                        substring.indexOf(" "),
                        substring.indexOf(","),
                        substring.indexOf(")"),
                        substring.indexOf("'")
                ).filter {
                    it > 0
                }.min()?.let {
                    substring = substring.substring(0,it)
                }

                pset.add(substring)
            }
            null
        }
    }

    println(pset)

    PathUtil.ensurePath(outputBase)

    //caculate
    pset.forEach {
        val relap = it.substring(featureUrl.length).replace("/","\\")
        val outputFilePath = outputBase + "\\" + relap
        val dFile = File(outputFilePath)
        PathUtil.ensureFilePath(outputFilePath)
        if (!dFile.exists()) {
            val downUrl = remoteBase + "" + it
//            println(downUrl)
            try {
                UrlFileDownloader.download(downUrl,outputFilePath)
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
        println(outputFilePath)
    }
}