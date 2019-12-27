package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import net.sf.json.JSONObject
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    val user = Ariesutil.getUser(jdbc, "陈慧慧", "", "")
//    val user = Ariesutil.getUser(jdbc, "", "13331832190", "")

//    val user = Ariesutil.getUser(jdbc, "王晖", "", "")
    val user = Ariesutil.getUser(jdbc, "", "18621616363", "")

//    val user = Ariesutil.getUser(jdbc, "葛新", "", "")
//    val user = Ariesutil.getUser(jdbc, "", "18616356337", "")

    var userId = user!![0]["ID"] as Long
    val uuid = Ariesutil.convertUUID(userId)
    var userName = user!![0]["NAME"]
    var userMOBILE = user!![0]["MOBILE"]
    println("${user.size} $uuid $userId $userName $userMOBILE")

//    #saas入口图标
    val query = JSONObject()
    query.put("uid",uuid)
    var rs = HttpPostUtil.postUrl("http://api.g2s.cn/saas/which", query)
    println("saas入口图标:${rs}")

    //    #企业头衔
    rs = HttpPostUtil.postUrl("http://api.g2s.cn/getUserInfo", query)
    println("企业头衔:${rs}")


//    企业头衔sql
    val mutableList = jdbc.query("""
SELECT uc.ID as ucId ,c.Id as companyId,c.NAME,c.IS_SAAS,uc.IS_GUEST,uc.CREATE_TIME,uc.UPDATE_TIME
FROM db_aries_user.TBL_USER_COMPANY  uc
         LEFT JOIN  db_aries_company.TBL_COMPANY c on uc.COMPANY_ID = c.ID
WHERE
        uc.IS_DELETED = 0
  AND c.IS_DELETED = 0
  AND uc.USER_ID = ${userId}
ORDER BY c.IS_SAAS DESC,FIELD(COMPANY_ID,47) DESC,uc.ID DESC;
    """.trimIndent(), ColumnMapRowMapper())

    println("用户企业基础认证信息:")
    for (map in mutableList) {
        println("$map")
    }

}