package com.jiangli.doc.sql.helper.zhihuishu.bbs

import com.jiangli.doc.sql.BaseConfig
import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.UserIdQueryer
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Timestamp

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/26 16:17
 */

fun main(args: Array<String>) {
    val onlineSchooljdbc = Zhsutil.getJDBC(Env.WAIWANG_ONLINESCHOOL)
    val qajdbc = Zhsutil.getJDBC(Env.WAIWANG_BBS,"ZHS_BBS")
    val qaDB = Zhsutil.getDB(Env.WAIWANG_BBS)
    val q  = UserIdQueryer()
    BaseConfig.printSql=false

    val split = """
        169514793
183134959
187543193
186364671
187451009
184688071
187554229
187556857

        169363035
169165283
168645123
187465031
184637617
187475283
182370769
    """.split("\n")
    println(split)

    val fixedUsers = linkedSetOf<Int>(
            162347707
//            ,163401949
//            ,170125517
//            ,187900739
//            ,187691671
//            ,187168025
//            ,182529777
//            ,183927377
//            ,185628497
//            ,169116471
//            ,183276641
//            ,183877873
//            ,187255287
//            ,187455927
//            ,182443379
//            ,183613593
//            ,187239895
//            ,168305113
//            ,168361357
//
//            ,189277745
    )


//    if (split.isNotEmpty()) {
//        split.forEach {
//            val trim = it.trim()
//            if (trim.isNotBlank()) {
//                fixedUsers.add(trim.toInt())
//            }
//        }
//    }


    queryByGroup(onlineSchooljdbc,qajdbc, q,"固定组", fixedUsers,null,{ type, id, content, mutableMap -> System.err.println("$type $id $content") })

//    ⑧1⑤Æㄉ3⑨42⑦0

    val sqls = linkedSetOf<String>(
//        "妈妈再也不用担心我的学习"
//        "大家做完了没"
//    ,"三年来专为学生网课服务 高效质量好"
//    ,"如果缺少乙醇脱氢酶能体外补充么?"
//    ,"劳动法律关系主体"
    "中国手语的造词类型只有象形和指事两类"
    ,"社会心理学家会支持以下哪种想法？"
    )

    sqls.forEach {
        val userIds = linkedSetOf<Int>(
        )

        val sql  = """
SELECT * from ZHS_BBS.QA_QUESTION WHERE CONTENT LIKE '%${it}%' ;
        """.trimIndent()
//        println(sql)

        val quesList = qajdbc.query(sql, ColumnMapRowMapper())
        quesList.forEach {
            userIds.add(it["CREATE_USER"] as Int)
        }

        queryByGroup(onlineSchooljdbc,qajdbc, q,"模糊匹配:$it", userIds,null,{ type, id, content, mutableMap -> System.err.println("$type $id $content") })
    }

}

fun queryByGroup(onlineSchooljdbc: JdbcTemplate,qajdbc: JdbcTemplate, q: UserIdQueryer, s:String, users: LinkedHashSet<Int>, isDeleted:Boolean?=null, werror: ((type:Int, id: String, content: String, MutableMap<Any?, Any?>) -> Unit)? = { type, id, content, mutableMap ->  }) {
    println("--------------group:$s-------------------")

    users.forEach {
        val curUid = it
        val userMap = Zhsutil.injectTest(onlineSchooljdbc, curUid, q)

        val rights = onlineSchooljdbc.query("""
            SELECT
  COURSE_ID as courseId,
  STUDENT_ID,
  RECRUIT_ID as recruitId,
  SCHOOL_ID,SCHOOL_NAME,IS_DELETE
FROM db_G2S_OnlineSchool.STUDENT WHERE  STUDENT_ID=$it
        """.trimIndent(), ColumnMapRowMapper())

        val classCourseIdMap = mutableMapOf<String, Map<Any?, Any?>>()
        rights.forEach {
//            classCourseIdMap.put(it["recruitId"].toString(), it as Map<Any?, Any?>)
            classCourseIdMap.put(it["courseId"].toString(), it as Map<Any?, Any?>)
        }

        val allCourseIds = mutableSetOf<String>()
        allCourseIds.addAll(classCourseIdMap.keys)

        var eachRootCourseIds = mutableSetOf<String>()
        eachRootCourseIds.addAll(allCourseIds)

        while (eachRootCourseIds.isNotEmpty()) {
            val queryList = onlineSchooljdbc.query("""
    select COURSE_ID,ROOT_COURSE_ID from db_G2S_OnlineSchool.TBL_COURSE WHERE ROOT_COURSE_ID is not null and COURSE_ID IN (${eachRootCourseIds.joinToString(",")});
""".trimIndent(), ColumnMapRowMapper())

            eachRootCourseIds.clear()
            queryList.forEach {
                eachRootCourseIds.add(it["ROOT_COURSE_ID"].toString())

                allCourseIds.add(it["COURSE_ID"].toString())
                allCourseIds.add(it["ROOT_COURSE_ID"].toString())
            }
        }

        println("user:$curUid  ${userMap["REAL_NAME"]} ${userMap["NICK_NAME"]} ${userMap["E_MAIL"]} ${Zhsutil.convertUUID(curUid)} $allCourseIds")


        val condition = if (isDeleted == null) "" else if(isDeleted) "AND IS_DELETED = 1" else "AND IS_DELETED = 0"

        val prefix = "    "

        val quesList = queryParticipate(qajdbc, { each ->
            allCourseIds.contains(each["ANCESTOR_COURSE_ID"].toString())
        }, prefix, "问", """
            SELECT
  QUESTION_ID as ID
  ,COURSE_ID
  ,RECRUIT_ID
,ANCESTOR_COURSE_ID
,CONTENT AS CONTENT
,CREATE_TIME
 ,CREATE_USER AS CREATOR
FROM ZHS_BBS.QA_QUESTION WHERE  CREATE_USER=$it $condition ORDER BY QUESTION_ID DESC ;
        """,{ id, content, mutableMap -> werror?.invoke(1, id, content, mutableMap) })

        queryParticipate(qajdbc,{ each ->
            allCourseIds.contains(each["ANCESTOR_COURSE_ID"].toString())
        },prefix,"答","""
            SELECT
  ID
  ,COURSE_ID
  ,ANCESTOR_COURSE_ID
  ,A_CONTENT  AS CONTENT
  ,CREATE_TIME
  ,A_USER_ID AS CREATOR
FROM ZHS_BBS.QA_ANSWER WHERE  A_USER_ID=$it $condition ORDER BY ID DESC ;
        """,{ id, content, mutableMap -> werror?.invoke(2, id, content, mutableMap) })

        queryParticipate(qajdbc,{ each ->
            allCourseIds.contains(each["ANCESTOR_COURSE_ID"].toString())
        },prefix,"评","""
            SELECT
  ID
  ,COURSE_ID
  ,ANCESTOR_COURSE_ID
  ,COMMENT_CONTENT  AS CONTENT
  ,CREATE_TIME
  ,COMMENT_USER_ID AS CREATOR
FROM ZHS_BBS.QA_COMMENT WHERE  COMMENT_USER_ID=$it $condition ORDER BY ID DESC ;
        """,{ id, content, mutableMap -> werror?.invoke(3, id, content, mutableMap) })


//        val pl: (Any?) -> Unit = if (failQ == 0) ::println else System.err::println
    }
}

fun queryParticipate(qajdbc: JdbcTemplate, check:(MutableMap<Any?, Any?>)->Boolean, prefix: String, module: String, sql: String, werror: ((id: String, content: String, MutableMap<Any?, Any?>) -> Unit)? = null): MutableList<MutableMap<String, Any>>? {
    val particiQues = qajdbc.query(sql.trimIndent(), ColumnMapRowMapper())

    var sucQ = 0
    var failQ = 0
    particiQues.forEach {
        val map = it as MutableMap<Any?, Any?>
//            if (!classMap.containsKey(map["RECRUIT_ID"].toString())) {
        if (!check(map)) {
//                System.err.println(map)
            if (werror != null) {
                werror(map["ID"].toString(),map["CONTENT"].toString(),map)
            }
            failQ++
        } else {
            sucQ++
        }
    }

    val comparator = compareBy<MutableMap<String, Any>> {
        it["CREATE_TIME"] as Timestamp?
    }
    val maxCreateTimeDt = particiQues.maxWith(
            comparator
    )
    val minCreateTimeDt = particiQues.minWith(
            comparator
    )

    val pl: (Any?) -> Unit = if (failQ == 0) ::println else System.err::println
    pl("${prefix}${module}（s:$sucQ f:$failQ /t:${particiQues.size}）(${minCreateTimeDt?.get("CREATE_TIME")}~${maxCreateTimeDt?.get("CREATE_TIME")})")

    return particiQues
}


