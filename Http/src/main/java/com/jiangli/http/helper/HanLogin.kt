package com.jiangli.http.helper

import com.jiangli.common.utils.HttpPostUtil
import net.sf.json.JSONObject

/**
 *
 *
 * @author Jiangli
 * @date 2019/12/27 21:26
 */

fun main(args: Array<String>) {
    val loginUrl = "http://66api.pihoda.cn:8688//User/Login"
    val p = JSONObject()

    p.put("UserName","1052096033@qq.com")
    p.put("Password", "")
    p.put("ClientType",2)
    val st = HttpPostUtil.postUrl(loginUrl, p, mapOf(
            "token" to "dasdas"
    ))
    println(st)
    val fromObject = JSONObject.fromObject(st)
    val data = fromObject.getJSONObject("data")
    val token = data.getString("Token")
    val UserID = data.getInt("UserID")
    println(UserID)
    println(token)
}