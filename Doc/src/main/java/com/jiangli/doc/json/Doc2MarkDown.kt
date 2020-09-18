package com.jiangli.doc.json

import net.sf.json.JSONObject


/**
 *
 *
 * @author Jiangli
 * @date 2020/8/26 17:10
 */
fun main(args: Array<String>) {
    var res = """
1.访问域名		
		
数据中心	访问域名地址	
数据中心	open-api.g2s.cn	
		
		
		
		
		
每次请求 API 接口时，均需要提供 4 个 HTTP Request Header，具体如下：		
名称	类型	说明
App-Key 或 RC-App-Key	String	开发者平台分配的 App Key。
Nonce 或 RC-Nonce	String	随机数，不超过 18 个字符。
Timestamp或RC-Timestamp	String	时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的毫秒数。
Signature 或 RC-Signature	String	数据签名。


    """.trimIndent()

    val split = res.split("\n".toRegex())

    val gson = JSONObject()
    var prev = ""
    var status = 0

    split.forEach {
        val line = it.trim()

        if (line.isBlank()) {
            return@forEach
        }

        if (!line.contains("\t")) {
            status = 0
        } else {
            status ++
        }

        if (status == 0) {
            println("")
            println("#${line}#")
        }  else{
            val arr = line.split("\t")

            if(status == 1){
                arr.forEach {
                    print("|${it}")
                }
                println("|")

                arr.forEach {
                    print("|:---")
                }
                println("|")
            } else {
                arr.forEach {
                    print("|${it}")
                }
                println("|")
            }
        }
    }

}