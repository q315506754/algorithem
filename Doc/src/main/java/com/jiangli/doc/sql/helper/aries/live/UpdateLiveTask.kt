package com.jiangli.doc.sql.helper.aries.live

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
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


//    企业头衔sql
    val mutableList = jdbc.query("""
 SELECT * FROm db_aries_live.live_task WHERE LIVE_NAME like '直播|%' or LIVE_NAME like '直播｜%' or LIVE_NAME like '直播 %';
    """.trimIndent(), ColumnMapRowMapper())

    for (map in mutableList) {
//        println("$map")

       var id =  map["ID"] as Int
       var name =  map["LIVE_NAME"] as String
       var oldName = name

        name = name.substring(2).trim()
        if (name.startsWith("|")) {
            name = name.substring(1).trim()
        }
        if (name.startsWith("｜")) {
            name = name.substring(1).trim()
        }
        if (name.startsWith("l")) {
            name = name.substring(1).trim()
        }
//        name = name.replace("直播","").trim()
//        if () {
//        }
//        println(name)
        println("####recover sql")
        println("##UPDATE  db_aries_live.live_task SET LIVE_NAME='$oldName' WHERE ID =$id;")
        println("UPDATE  db_aries_live.live_task SET LIVE_NAME='$name' WHERE ID =$id;")
    }

}