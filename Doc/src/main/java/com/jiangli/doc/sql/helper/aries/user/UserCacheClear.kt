package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
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
18988910498,
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

        val query = jdbc.queryForObject("""
    SELECT * FROM   db_aries_user.TBL_USER  WHERE MOBILE='$mobile' AND AREA_CODE='+86' AND IS_DELETED = 0;
""".trimIndent(), ColumnMapRowMapper())

        val USER_ID = query["ID"].toString().toLong()
        println(USER_ID)


//        println("#sql")
//        val sql = """
//
//            UPDATE `db_aries_user`.`tbl_user` SET `PASSWORD` = '7fa6c5620d75aeb526668985e46f19b8c638ddeb087ccca21925ec88a82454b8' where `MOBILE` in(
//18606232766,
//15825566882,
//15852751290,
//13306527268,
//13671569009,
//13858074020,
//18968009696,
//13720885406,
//13634181054,
//13858076323,
//18610161536,
//18258135566,
//13868131939,
//15068888966,
//15858273789,
//15251635363,
//18767162267,
//15858281439,
//13761156786
//)
//
//UPDATE  db_aries_pay_core.TBL_ORDER o
//LEFT JOIN db_aries_operation.TBL_READ_BOOK b on b.Android_GOOD_ID = o.ITEM_ID
//LEFT JOIN db_aries_study.TBL_USER_COURSE uc on uc.USER_ID = o.USER_ID AND uc.CLASS_ID = b.CLASS_ID
//SET uc.IS_DELETE=1
//WHERE
//o.USER_ID = $USER_ID
//ANd o.STATUS=1
// ;
//        """.trimIndent()
//
//        jdbc.execute(sql.trimIndent())
//
////        清除 入班记录
//        println(sql)


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