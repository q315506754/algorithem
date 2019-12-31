package com.jiangli.http.helper

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.common.utils.RecurUtil

/**
 *
 *
 * @author Jiangli
 * @date 2019/12/27 21:26
 */
data class HanLoginRs(val UserID:String,val Token:String)

fun main(args: Array<String>) {
    println(hanLogin())
}

internal fun cachedLogin(): HanLoginRs? {
    return HanLoginRs(FileUtil.readKv("han_USERID", ""),FileUtil.readKv("han_TOKEN",  ""))
}
internal fun hanLogin(): HanLoginRs? {
    val loginUrl = "http://66api.pihoda.cn:8688//User/Login"

    val st = HttpPostUtil.postUrl(loginUrl, mapOf(
            "UserName" to FileUtil.readKv("han_username", "1052096033@qq.com")
            ,"Password" to FileUtil.readKv("han_pwd", null)
            ,"ClientType" to 2
    ), mapOf(
            "token" to "dasdas"
    ))

    println(st)
//    val token =RecurUtil.recursiveJSON(st,"data.Token")
//    val UserID =RecurUtil.recursiveJSON(st,"data.UserID")
//    return HanLoginRs(UserID,token)
    val ret = RecurUtil.recursiveJSON(st, "data", HanLoginRs::class.java)


    FileUtil.writeKv("han_USERID", ret.UserID)
    FileUtil.writeKv("han_TOKEN", ret.Token)

    return ret
}