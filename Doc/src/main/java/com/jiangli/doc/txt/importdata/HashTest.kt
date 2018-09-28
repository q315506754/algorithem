package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.YufaCodis
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/5/29 15:50
 */

fun main(args: Array<String>) {
    val userId = Zhsutil.convertUUID("VvM1r7zX")
    println(userId)

    val CURRENT_ENV = Env.YUFA
    val configMap = getConfig()
    val targetDB = configMap[CURRENT_ENV]!!.jdbc

    val query = targetDB.query("select ID from db_teacher_home.TH_TEACHER where USER_ID = $userId", ColumnMapRowMapper())
    if (query.size == 1){
        val thId = query[0]["ID"] as Int
        println(thId)

        val codis = YufaCodis()
        codis.execute("del th:teacher:$thId")
    }

}