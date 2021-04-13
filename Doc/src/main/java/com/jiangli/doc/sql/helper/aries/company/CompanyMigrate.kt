package com.jiangli.doc.sql.helper.aries.company

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

    val createTime = "2021-01-01 01:01:01"

//    mobiles.split("\n").forEach {
//        val DOMAIN = "api.g2s.cn"
//        val REDIS_DOMAIN = "127.0.0.1:84/aries-pay-app-server"

//        val mobile = it.trim()
//        println(mobile)
        val REDIS_DOMAIN = "api-pay.g2s.cn/aries-pay-app-server"

//        val query = jdbc.query("""
//    SELECT uc1.COMPANY_ID,uc1.USER_ID,uc1.GENDER,uc1.CREATE_TIME FROm
//    db_aries_user.TBL_USER_COMPANY uc1
//WHERE   uc1.IS_DELETED=0
//AND uc1.CREATE_TIME = '$createTime';
//""".trimIndent(), ColumnMapRowMapper())
        val query = jdbc.query("""
    
SELECT DISTINCT (uc.USER_ID)  FROm
    db_aries_user.TBL_USER_COMPANY uc
    LEFT JOIN db_aries_company.TBL_COMPANY c on uc.COMPANY_ID = c.ID
WHERE
c.IS_DELETED = 1
AND c.ID IN(
            83,
            434,
            490,
            252,
            250,
            63,
            140,
            458,
            182,
            225,
            86,
            485,
            229,
            163,
            430,
            275,
            464,
            186,
            193,
            129,
            204,
            418
    )
;
""".trimIndent(), ColumnMapRowMapper())

    var total = query.size
    var n = 0
    for (mutableMap in query) {
        val USER_ID = mutableMap["USER_ID"].toString().toLong()
//        println(USER_ID)
        n++

        println("#redis")
        var cmd = "del aries:ser:USER_SAAS_CHOOSE_COMPANY_LIST:d:$USER_ID"
        var aa = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",cmd))
        println("${n}/${total} #$cmd:${aa}")

         cmd = "del aries:ser:USER_SAAS_COMPANY_LIST_WHICH_USER_IN:d:$USER_ID"
         aa = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",cmd))
        println("${n}/${total} #$cmd:${aa}")

         cmd = "del aries:ser:USER_CHOOSE_COMPANY:l:$USER_ID"
         aa = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",cmd))
        println("${n}/${total} #$cmd:${aa}")
    }


}