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
    val env = Env.WAIWANG_ALL
    val jdbc = Zhsutil.getJDBC(env)
    val q  = UserIdQueryer()
    BaseConfig.printSql=false

    val fixedUsers = linkedSetOf<Int>(
            163401949
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
            ,168298089
            ,163282629
            ,163492815
            ,187566035
            ,165259725
    )
    queryByGroup(jdbc, q,"固定组", fixedUsers)

//    ⑧1⑤Æㄉ3⑨42⑦0

    val sqls = linkedSetOf<String>(
//        "妈妈再也不用担心我的学习"
//        "大家做完了没"
//    ,"三年来专为学生网课服务 高效质量好"
//    ,"如果缺少乙醇脱氢酶能体外补充么?"
//    ,"劳动法律关系主体"
    )

    sqls.forEach {
        val userIds = linkedSetOf<Int>(
        )
        val sql  = """
SELECT * from ZHS_BBS.QA_QUESTION WHERE (CONTENT LIKE '%$it%' ) ORDER BY QUESTION_ID DESC ;
        """.trimIndent()
        val quesList = jdbc.query(sql, ColumnMapRowMapper())
        quesList.forEach {
            userIds.add(it["CREATE_USER"] as Int)
        }

        queryByGroup(jdbc, q,"模糊匹配:$it", userIds)
    }

}

private fun queryByGroup(jdbc: JdbcTemplate, q: UserIdQueryer, s:String, users: LinkedHashSet<Int>) {
    println("--------------group:$s-------------------")

    users.forEach {
        val curUid = it
        val userMap = Zhsutil.injectTest(jdbc, curUid, q)

        val rights = jdbc.query("""
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

        println("user:$curUid ${userMap["REAL_NAME"]} ${userMap["NICK_NAME"]} ${userMap["E_MAIL"]} ${Zhsutil.convertUUID(curUid)}")


        val prefix = "    "

        val quesList = queryParticipate(jdbc, { each ->
            classCourseIdMap.containsKey(each["COURSE_ID"].toString())
        }, prefix, "问", """
            SELECT
  QUESTION_ID as questionId
  ,COURSE_ID
  ,RECRUIT_ID
,ANCESTOR_COURSE_ID
,CONTENT
,CREATE_TIME
FROM ZHS_BBS.QA_QUESTION WHERE  CREATE_USER=$it ORDER BY QUESTION_ID DESC ;
        """)
//        val ancestorIdMap = mutableMapOf<String, Map<Any?, Any?>>()
//        rights.forEach {
//            classCourseIdMap.put(it["courseId"].toString(), it as Map<Any?, Any?>)
//        }

//        queryParticipate(jdbc,classCourseIdMap,prefix,"答","""
//            SELECT
//  ID
//  ,COURSE_ID
//  ,ANCESTOR_COURSE_ID
//  ,A_CONTENT
//  ,CREATE_TIME
//FROM ZHS_BBS.QA_ANSWER WHERE  A_USER_ID=$it ORDER BY ID DESC ;
//        """)


//        val pl: (Any?) -> Unit = if (failQ == 0) ::println else System.err::println
    }
}

fun queryParticipate(jdbc: JdbcTemplate, check:(MutableMap<Any?, Any?>)->Boolean, prefix: String, module: String, sql: String): MutableList<MutableMap<String, Any>>? {
    val particiQues = jdbc.query(sql.trimIndent(), ColumnMapRowMapper())

    var sucQ = 0
    var failQ = 0
    particiQues.forEach {
        val map = it as MutableMap<Any?, Any?>
//            if (!classMap.containsKey(map["RECRUIT_ID"].toString())) {
        if (!check(map)) {
//                System.err.println(map)
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


