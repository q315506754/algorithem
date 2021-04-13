package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env

/**
 *
 * 注销用户
 * redis
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val mobiles = """
100011054
    """.trimIndent()

    mobiles.split("\n").forEach {
        val DOMAIN = "api.g2s.cn"
//        val REDIS_DOMAIN = "yf-api-pay.g2s.cn/aries-pay-app-server"
        val REDIS_DOMAIN = "api-pay.g2s.cn/aries-pay-app-server"
//        val REDIS_DOMAIN = "127.0.0.1:84/aries-pay-app-server"

        val mobile = it.trim().replace(",","")
        if (mobile.isBlank()) {
            return@forEach
        }
//        println(mobile)

        val USER_ID = mobile
        println(USER_ID)


        println("#redis")

        // 清除 入班cache
        val delUcCache = "del aries:ser:USER_V2:d:$USER_ID"
        var aa = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",delUcCache))
        println("#$delUcCache:${aa}")

//        println("#validate")
//        val param = JSONObject()
//        param.put("uid",Ariesutil.convertUUID(USER_ID))
//        var classCoachrs = HttpPostUtil.postUrl("http://$DOMAIN/saas/saasList", param)
//        println("#查找用户所属的SAAS企业列表:${classCoachrs}")
    }


//


}