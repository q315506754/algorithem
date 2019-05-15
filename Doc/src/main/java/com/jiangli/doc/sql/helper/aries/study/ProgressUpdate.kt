package com.jiangli.doc.sql.helper.aries.study

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *
 * 修改学习进度 100%
 *
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

    val COURSE_NAME = "家园沟通"
    val NAMES = arrayListOf("卢艺菲","赵宸轩")
    val exclude_USERID = arrayListOf("100012039")
    val TIME_STR = """2018-12-17 00:00:00"""
    val sqls = arrayListOf<String>()

    val e:(Any?)->Unit = System.err::println
    val p:(Any?)->Unit = System.out::println

    NAMES.forEach {
        val NAME = it
        val sql = """
SELECT c.COURSE_NAME, c.COURSE_ID,u.ID AS USER_ID,u.NAME as USER_NAME,u.MOBILE,u.EMAIL,u.CREATE_TIME as USER_CREATE_TIME,uc.RECRUIT_ID,uc.COMPANY_ID,co.NAME AS COMPANY_NAME
,tc.CLASS_ID
,tc.CLASS_NAME
 FROM db_aries_study.TBL_USER_COURSE uc
LEFT JOIN db_aries_course.TBL_COURSE c on uc.COURSE_ID = c.COURSE_ID
LEFT JOIN db_aries_user.TBL_USER u on uc.USER_ID = u.ID
LEFT JOIN db_aries_company.TBL_COMPANY co on co.ID = uc.COMPANY_ID
LEFT JOIN db_aries_run.TBL_CLASS tc on tc.CLASS_ID = uc.CLASS_ID
WHERE
  c. COURSE_NAME LIKE  '%$COURSE_NAME%'
  AND u.NAME LIKE '%$NAME%'
;
""".trimIndent()
        val userAndCourse = jdbc.query(sql, ColumnMapRowMapper())

        val distinct = userAndCourse.map { it["COURSE_ID"] }.distinct()
//        println(distinct)

        if (distinct.size > 1) {
            e(userAndCourse)
            System.exit(1)
        }

//        var COURSE_ID = distinct[0]

//        每一个人
        for (map in userAndCourse) {
            var USER_ID  = map["USER_ID"].toString()
            var USER_NAME  = map["USER_NAME"].toString()
            var COURSE_ID  = map["COURSE_ID"].toString()
            var COURSE_NAME  = map["COURSE_NAME"].toString()
            var MOBILE  = map["MOBILE"].toString()
            var USER_CREATE_TIME  = map["USER_CREATE_TIME"].toString()
            var RECRUIT_ID  = map["RECRUIT_ID"].toString()
            var COMPANY_ID  = map["COMPANY_ID"].toString()
            var COMPANY_NAME  = map["COMPANY_NAME"].toString()
            var CLASS_ID  = map["CLASS_ID"].toString()
            var CLASS_NAME  = map["CLASS_NAME"].toString()


            if(exclude_USERID.contains(USER_ID)){
                continue
            }

            val courseCount = queryCourseCount(jdbc, COURSE_ID)
            val legacyWatchPerSum = queryWatchPercentSum(jdbc, RECRUIT_ID,USER_ID)
            val legacyPercent = calcPerStr(courseCount, legacyWatchPerSum)

            val chapterPercent = queryChapterPercent(jdbc, COURSE_ID, RECRUIT_ID, USER_ID)
            val watchPerSum = chapterPercent.sumBy { it["WATCH_PERCENT"]?.toString()?.toInt() ?: 0 }
            val percent = calcPerStr(courseCount, watchPerSum)
            val indent = "----"

            p("[$COURSE_ID $COURSE_NAME] [$RECRUIT_ID] [c:$courseCount ws:$watchPerSum P:$percent% l-ws:$legacyWatchPerSum  l-P:${legacyPercent}% ] [$USER_ID $USER_NAME $MOBILE $USER_CREATE_TIME] [$COMPANY_ID $COMPANY_NAME] ")

//            历史记录
//            if ((legacyWatchPerSum * 1.0/ courseCount) < 100) {

//            修改章节观看记录
                chapterPercent.forEach {
                    val WATCH_PERCENT = it["WATCH_PERCENT"]?.toString()?.toInt()
                    val LAYER = it["LAYER"]?.toString()?.toInt()
                    val CH_ID = it["CH_ID"]?.toString()
                    val PARENT_ID = it["PARENT_ID"]?.toString()
                    val VIDEO_ID = it["VIDEO_ID"]?.toString()
                    val AUDIO_ID = it["AUDIO_ID"]?.toString()
                    var CHAPTER_ID:String?=null
                    var LESSON_ID:String?=null
                    var SECTION_ID:String?=null



                    if (WATCH_PERCENT == null) {
                        println("${indent}create $it")


                        //                        INSERT INTO `db_aries_study`.`TBL_VIDEO_WATCH_INFO`(`USER_ID`, `COURSE_ID`, `CHAPTER_ID`, `LESSON_ID`, `SECTION_ID`, `VIDEO_ID`, `AUDIO_ID`, `RECRUIT_ID`, `TOTAL_WATCH_TIME`, `TOTAL_VIDEO_WATCH_TIME`, `WATCH_STATE`, `TIME_COVERY`, `SOURCE_TYPE`, `TYPE`, `WATCH_PERCENT`, `LAST_WATCH_TIME`, `CREATE_TIME`, `UPDATE_TIME`, `IS_DELETE`) VALUES ( $USER_ID, $COURSE_ID, $CHAPTER_ID, $LESSON_ID, $SECTION_ID, $VIDEO_ID, $AUDIO_ID, $RECRUIT_ID, 999, 999, 1, '["#"]', 2, #layer, 100, '00:00:00', '2018-12-17 00:00:00', '2018-12-17 00:00:00', 0);
                        when (LAYER) {
                            1 -> {
                                CHAPTER_ID = CH_ID
                            }
                            2 -> {
                                CHAPTER_ID = PARENT_ID
                                LESSON_ID = CH_ID
                            }
                            3 -> {
                                LESSON_ID = PARENT_ID
                                SECTION_ID = CH_ID

                                CHAPTER_ID = queryChapterParentId(jdbc,LESSON_ID)
                            }
                        }

//                        INSERT INTO `db_aries_study`.`TBL_VIDEO_WATCH_INFO`(`USER_ID`, `COURSE_ID`, `CHAPTER_ID`, `LESSON_ID`, `SECTION_ID`, `VIDEO_ID`, `AUDIO_ID`, `RECRUIT_ID`, `TOTAL_WATCH_TIME`, `TOTAL_VIDEO_WATCH_TIME`, `WATCH_STATE`, `TIME_COVERY`, `SOURCE_TYPE`, `TYPE`, `WATCH_PERCENT`, `LAST_WATCH_TIME`, `CREATE_TIME`, `UPDATE_TIME`, `IS_DELETE`) VALUES ( $USER_ID, $COURSE_ID, $CHAPTER_ID, $LESSON_ID, $SECTION_ID, $VIDEO_ID, $AUDIO_ID, $RECRUIT_ID, 999, 999, 1, '["#"]', 2, #layer, 100, '00:00:00', '2018-12-17 00:00:00', '2018-12-17 00:00:00', 0);

                          sqls.add("""INSERT INTO `db_aries_study`.`TBL_VIDEO_WATCH_INFO`(`USER_ID`, `COURSE_ID`, `CHAPTER_ID`, `LESSON_ID`, `SECTION_ID`, `VIDEO_ID`, `AUDIO_ID`, `RECRUIT_ID`, `TOTAL_WATCH_TIME`, `TOTAL_VIDEO_WATCH_TIME`, `WATCH_STATE`, `TIME_COVERY`, `SOURCE_TYPE`, `TYPE`, `WATCH_PERCENT`, `LAST_WATCH_TIME`, `CREATE_TIME`, `UPDATE_TIME`, `IS_DELETE`) VALUES ( $USER_ID, $COURSE_ID, $CHAPTER_ID, $LESSON_ID, $SECTION_ID, $VIDEO_ID, $AUDIO_ID, $RECRUIT_ID, 999, 999, 1, '["#"]', 2, $LAYER, 100, '00:00:00', '$TIME_STR', '$TIME_STR', 0);""")
                    } else {
                        if (WATCH_PERCENT<100) {
                            val WATCH_ID = it["WATCH_ID"].toString()

                            println("${indent}update $it")

                            sqls.add("""
                            UPDATE  db_aries_study.`TBL_VIDEO_WATCH_INFO` SET WATCH_PERCENT = 100,WATCH_STATE=1,TIME_COVERY='["#"]',UPDATE_TIME='$TIME_STR' WHERE ID=$WATCH_ID;
                        """.trimIndent())
                        }
                    }
                }
//            }
        }

    }


    println("--------------sql-------------------")
    println()
    sqls.forEach {
        println(it)
    }
    println()
}

var parentIdCacheMap = mutableMapOf<String,String?>()
fun queryChapterParentId(jdbc: JdbcTemplate,chId:String?): String? {
    if (chId == null) {
        return null
    }
    val s = parentIdCacheMap[chId]
    if (s != null) {
//        println("hit cache $chId -> $s")
        return s
    }

    val sql = """
SELECT PARENT_ID FROM db_aries_course.TBL_CHAPTER WHERE Id = $chId;
""".trimIndent()
    //    println(sql)
    val userAndCourse = jdbc.query(sql, ColumnMapRowMapper())
    val toString = userAndCourse[0]["PARENT_ID"]?.toString()

    parentIdCacheMap[chId] = toString

    return toString
}


fun queryChapterPercent(jdbc: JdbcTemplate, coursE_ID: String, recruiT_ID: String, useR_ID: String): MutableList<MutableMap<String, Any>> {
    val sql = """
SELECT cs.SORT_LAYER_STR,c.ID AS CH_ID,c.LAYER,c.PARENT_ID,c.TITLE,c.VIDEO_ID,c.AUDIO_ID,wi.ID as WATCH_ID,wi.WATCH_STATE,wi.WATCH_PERCENT,wi.RECRUIT_ID
FROM db_aries_course.TBL_CHAPTER c
  LEFT JOIN db_aries_interaction.TBL_CHAPTER_SORT cs on c.ID = CS.CHAPTER_ID
  LEFT JOIN db_aries_study.TBL_VIDEO_WATCH_INFO wi on wi.VIDEO_ID = c.VIDEO_ID AND wi.COURSE_ID = c.COURSE_ID AND  wi.USER_ID=$useR_ID AND wi.RECRUIT_ID=$recruiT_ID
WHERE c.COURSE_ID=$coursE_ID
AND c.IS_DELETED =0
AND c.IS_PUBLISHED = 1
AND c.VIDEO_ID > -1
ORDER BY cs.SORT_LAYER_STR ASC;
""".trimIndent()
    //    println(sql)
    val userAndCourse = jdbc.query(sql, ColumnMapRowMapper())
    return userAndCourse
}


fun calcPerStr(courseCount: Int, watchPerSum: Int): String {
    return calcPer(courseCount, watchPerSum).toString()
}
fun calcPer(courseCount: Int, watchPerSum: Int): BigDecimal {
    var i = watchPerSum * 1.0/ courseCount
    if (i>100) {
        i = 100.0
    }
    return BigDecimal(i).setScale(2,RoundingMode.HALF_UP)
}

fun queryWatchPercentSum(jdbc: JdbcTemplate, recruiT_ID: String, useR_ID: String): Int {
    val sql = """
SELECT
  SUM(WATCH_PERCENT) as COUNT
FROM db_aries_study.TBL_VIDEO_WATCH_INFO
WHERE
IS_DELETE =0
AND RECRUIT_ID=$recruiT_ID
AND USER_ID=$useR_ID
;
""".trimIndent()
//    println(sql)
    val userAndCourse = jdbc.query(sql, ColumnMapRowMapper())
    return userAndCourse[0]["COUNT"]?.toString()?.toInt()?:0
}

fun queryCourseCount(jdbc: JdbcTemplate, coursE_ID: String): Int {
    val sql = """
SELECT COUNT(1) as COUNT FROM db_aries_course.TBL_CHAPTER
WHERE
COURSE_ID=$coursE_ID
AND IS_DELETED =0
AND IS_PUBLISHED = 1
AND VIDEO_ID > -1
;
""".trimIndent()
    val userAndCourse = jdbc.query(sql, ColumnMapRowMapper())
return userAndCourse[0]["COUNT"].toString().toInt()
}
