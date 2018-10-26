package com.jiangli.doc.txt.sql

import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import com.jiangli.doc.txt.DB
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/30 15:15
 */
fun main(args: Array<String>) {
    val userId = Zhsutil.convertUUID("V4e1RbYN")
    println(userId)

    val jdbc = DB.getJDBCForWaiWang()
    val s = "select REAL_NAME,NICK_NAME,PASSWORD,PASSWORD2,E_MAIL,PHONE_NUMBER,IS_DELETED from db_G2S_OnlineSchool.TBL_USER WHERE ID=$userId;"
    val query = jdbc.query(s, ColumnMapRowMapper())
    println(query)


}