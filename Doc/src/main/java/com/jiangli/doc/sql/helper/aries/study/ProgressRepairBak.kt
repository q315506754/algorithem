package com.jiangli.doc.sql.helper.aries.study

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 学习进度去重
 * 后台会变
 *
 * @author Jiangli
 * @date 2018/12/10 15:57
 */
fun main(args: Array<String>) {
    //    val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val groupBY = arrayListOf(
            "USER_ID"
            , "COURSE_ID"
            , "RECRUIT_ID"
            , "VIDEO_ID"
//            , "CHAPTER_ID"
//            , "SECTION_ID"
            , "IS_DELETE"
    )

    val tbl = "db_aries_study.`TBL_VIDEO_WATCH_INFO`"
    val INDENT = "----"


    val updateSqls = arrayListOf<String>()

    val fields = groupBY.joinToString(",")
//    println(fields)
    val sql = """
SELECT $fields FROM $tbl
WHERE IS_DELETE=0
GROUP BY $fields
HAVING count(1) > 1 ;
""".trimIndent()
    val query = jdbc.query(sql, ColumnMapRowMapper())
//    println(query)
    println(query.size)

    for (mutableMap in query) {
        var condition = ""

        val mapKeys = mutableMap.keys
        for (key in mapKeys) {
            val any = mutableMap[key]
            var suffix = ""
            suffix = if (any != null) {
                "="+any
            } else {
                " is NULL"
            }
            suffix +=" AND "

            condition +="$key $suffix"
        }
        condition +=" 1=1"
        println(condition)

        val queryDup = """
SELECT * FROM $tbl
WHERE
$condition;
""".trimIndent()

//        println(queryDup)
        val queryDupRs = jdbc.query(queryDup, ColumnMapRowMapper())

//        重复的每一条数据  需要去重方案
        val sortList = mutableListOf<Map<String?, Any?>>()
        for (queryDupOne in queryDupRs) {
            val filterKeys = queryDupOne.filterKeys { k -> !mapKeys.contains(k) }
            sortList.add(filterKeys)
            println("${INDENT} $filterKeys")
        }


        sortList.sortByDescending {
            map ->  map.get("WATCH_PERCENT").toString().toInt()
        }


        val toBeDeletedId  = arrayListOf<String>()
        sortList.filterIndexed { index, map ->  index>0}.forEach {
            toBeDeletedId.add(it["ID"].toString())
        }
        println("${INDENT} 删掉:${toBeDeletedId.joinToString(",")}")

//删除
        val updater = """
update db_aries_study.`TBL_VIDEO_WATCH_INFO` set IS_DELETE=1 , UPDATE_TIME='2018-12-12 12:12:12' WHERE ID in (${toBeDeletedId.joinToString(",")});
        """.trimIndent()

        updateSqls.add(updater)

//        break
    }

    println(".................................")
    updateSqls.forEach {
//        System.err.println(it)
        println(it)
    }
}