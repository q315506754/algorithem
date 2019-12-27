package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/11/16 16:29
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    ,TYPE4(4, true, "知识教练活动")
//    ,TYPE5(5, true, "对话企业家")
//    ,TYPE1000(1000, true, "读书会")
//    ,TYPE1001(1001, true, "公开课")
//    ,TYPE1002(1002, true, "研习社")
//    ,TYPE1003(1003, true, "共生课堂")
//    ,TYPE1004(1004, true, "学员")

    var type = 1003

    var videoIds = mutableListOf<Int>(
            0
//            ,13049
    )

    videoIds.remove(0)

    var list: MutableList<MutableMap<String, Any>>?

    if (videoIds.isNotEmpty()) {
        list = jdbc.query("""
    SELECT * FROM  `db_aries_operation`.`TBL_2C_WONDER_VIDEO` WHERE VIDEO_ID in (${videoIds.joinToString(",")});
                """.trimIndent(), ColumnMapRowMapper())
    } else {
        list = jdbc.query("""
    SELECT * FROM  `db_aries_operation`.`TBL_2C_WONDER_VIDEO` WHERE TYPE_ID=$type AND IS_DELETED = 0;
                """.trimIndent(), ColumnMapRowMapper())
    }


    list.forEach {
        val wId = it["ID"]
        val videoId = it["VIDEO_ID"]

        val vInfo = jdbc.queryForObject("""
SELECT * FROM  `db_aries_base`.`TBL_VIDEO` WHERE ID=$videoId;
            """.trimIndent(), ColumnMapRowMapper())
        val newLog = "http://image.g2s.cn/"+vInfo["THUMBNAIL"]
//        println(newLog)

        println("#cur data ${it["COVER"]}")
        println("UPDATE `db_aries_operation`.`TBL_2C_WONDER_VIDEO` SET COVER='$newLog',STATUS=1 WHERE ID =$wId;")
    }
}