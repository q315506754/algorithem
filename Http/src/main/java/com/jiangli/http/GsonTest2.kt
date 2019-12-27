package com.jiangli.http

import net.sf.json.JSONObject


/**
 *
 *
 * @author Jiangli
 * @date 2019/12/24 9:28
 */
fun main(args: Array<String>) {
    var str = """
{"code":200,"msg":"ok","data":{"playurl":"http:\/\/vqzone.gtimg.com\/1006_e8bbe02da07e46ff92d067b99d4ddbbb.f20.mp4?ptype=http&vkey=EA6A590516BA3D2D6EF1E6A615197E156A5FB300B219FE3B2B3691C4B3DD7D9DAD4F65BDD5ADF9C7540583E153B4C97F0EB0C30AB116FAEF&sdtfrom=v1000&owner=0","playtype":"dmp4"}}
    """.trimIndent()

    val gson = JSONObject.fromObject(str)
    val data = gson.getJSONObject("data")
    val playurl = data.getString("playurl")
    println(data)
    println(playurl)

}