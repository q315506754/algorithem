package com.jiangli.doc.sql.helper.aries.common

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import net.sf.json.JSONObject
import org.springframework.jdbc.core.ColumnMapRowMapper

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
13564901520
    """.trimIndent()

    mobiles.split("\n").forEach {
        val DOMAIN = "api.g2s.cn"
        val REDIS_DOMAIN = "api-pay.g2s.cn/aries-pay-app-server"
//        val REDIS_DOMAIN = "127.0.0.1:84/aries-pay-app-server"

        val mobile = it.trim()
//        println(mobile)

        val query = jdbc.queryForObject("""
    SELECT * FROM   db_aries_user.TBL_USER  WHERE MOBILE='$mobile' AND IS_DELETED = 0;
""".trimIndent(), ColumnMapRowMapper())

        val USER_ID = query["ID"].toString().toLong()
        println(USER_ID)


        println("#sql")

//        清除 用户企业
        println("UPDATE  db_aries_user.TBL_USER_COMPANY SET IS_DELETED=1 WHERE USER_ID = $USER_ID;")
//        清除 用户管理企业
        println("UPDATE  db_aries_user.TBL_USER_SAAS_COMPANY_ADMIN SET IS_DELETED=1 WHERE USER_ID = $USER_ID;")


        println("#redis")
        val delSaasCache = "del aries:ser:USER_SAAS_CHOOSE_COMPANY_LIST:d:$USER_ID"
        var aa = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",delSaasCache))
        println("#$delSaasCache:${aa}")

        println("#validate")
        val param = JSONObject()
        param.put("uid",Ariesutil.convertUUID(USER_ID))
        var classCoachrs = HttpPostUtil.postUrl("http://$DOMAIN/saas/saasList", param)
        println("#查找用户所属的SAAS企业列表:${classCoachrs}")
    }


//


}