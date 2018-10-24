package com.jiangli.doc.sql.helper.aries

import com.jiangli.common.utils.SplitUtil
import org.apache.commons.io.IOUtils
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL

/**
 *
 *
 * @author Jiangli
 * @date 2018/10/23 15:56
 */
fun main(args: Array<String>) {
    val ip ="122.112.211.73"
    val inetSocketAddress = InetSocketAddress(ip, 20011)
    println(inetSocketAddress)
    inetSocketAddress.isUnresolved
    inetSocketAddress.address
    inetSocketAddress.hostName
    inetSocketAddress.hostString
    println("-----------------")

    SplitUtil.splitByPageSize(1,65535,2000,{ from, to ->
//        println("$from $to")
        Thread(Runnable {

                (from..to).forEach { l_port ->

                    try {
                        val socket = Socket()
                        socket.connect(InetSocketAddress(ip, l_port),50)
                        println("ok $l_port")

                        try {
                            val l_domain = "http://$ip:$l_port"
                            val urlConnection = URL("$l_domain").openConnection() as HttpURLConnection
//                            println(urlConnection)
                            urlConnection.connect()

                            //设置请求方式为"GET"
//                            urlConnection.requestMethod = "POST"
                            //超时响应时间为5秒
//                            urlConnection.connectTimeout = 2 * 1000

                            val code = getsCode(urlConnection)
                            if (code > 0) {
                                println("http port scanned! ($code) -> $l_port")

                                setOf(
                                        "$l_domain/config/reset/17621168106"
                                        ,"$l_domain/aries-base-message/config/reset/17621168106"
                                        ,"$l_domain/getservicelines"
                                        ,"$l_domain/index.jsp"
                                        ,"$l_domain/index"
                                        ,"$l_domain/login.jsp"
                                        ,"$l_domain/login"
                                ).forEach {
                                    val l_url = it

                                    listOf("GET","POST").forEach {
                                        val l_type = it

                                        val testC = URL(l_url).openConnection() as HttpURLConnection
                                        testC.requestMethod=l_type
                                        testC.connect()

                                        val getsCode = getsCode(testC)
                                        var text = ""
                                        if (getsCode == 200) {
                                            val stringWriter = StringWriter()
                                            IOUtils.copy(testC.inputStream,stringWriter)
                                            text = stringWriter.toString()

                                            if (text.isBlank()) {
                                                text = "(Empty)"
                                            }
                                        }

                                        if (!setOf(404).contains(getsCode)) {
                                            println("http:$l_port-$l_type $getsCode $l_url $text")
                                        }
                                    }

                                }

                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } catch (e: Exception) {
                        //            e.printStackTrace()
                        //            println("!!! $l_port")
                    }
                }

        }).start()
    })

}

private fun getsCode(urlConnection: HttpURLConnection): Int {
    try {
        return urlConnection.responseCode
    } catch (e: Exception) {
    }
    return -1
}