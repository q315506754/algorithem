package com.jiangli.doc.sql.helper.aries.study

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 根据企业关键字查询 企业
 * 以及其班级成员下的学习进度在
 *  [min%,max%]的学员
 *
 *  打印出各自未看完的章节
 *
 *  最后打印出修复到100%的执行sql
 *
 */
fun main(args: Array<String>) {
    //    val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val QUERY_COMPANY_NAME = "万华"
    val QUERY_CLASS_NAME = "万华"
    val PERCENT_MIN=90 //包含
    val PERCENT_MAX=101 //不包含
    val TIME_STR = """2019-05-14 00:00:00"""

    val sqls = arrayListOf<String>()

    val e:(Any?)->Unit = System.err::println
    val p:(Any?)->Unit = System.out::println

//    查询企业和班级
    val queryClassAndCompany = jdbc.queryForObject("""
SELECT * from db_aries_run.TBL_CLASS cls
 LEFT JOIN db_aries_company.TBL_COMPANY c on cls.COMPANY_ID=c.ID
WHERE cls.IS_DELETE=0
AND c.IS_DELETED=0
AND c.NAME like '%${QUERY_COMPANY_NAME}%'
AND cls.CLASS_NAME like '%${QUERY_CLASS_NAME}%';
;
""".trimIndent(), ColumnMapRowMapper())

    p(queryClassAndCompany)

    val CLASS_ID =queryClassAndCompany["CLASS_ID"]
    val COMPANY_ID =queryClassAndCompany["COMPANY_ID"]
    val COURSE_ID =queryClassAndCompany["COURSE_ID"]
    val RECRUIT_ID =queryClassAndCompany["RECRUIT_ID"]
    val COMPANY_NAME =queryClassAndCompany["NAME"]
    val CLASS_NAME =queryClassAndCompany["CLASS_NAME"]
    p("COMPANY_NAME:$COMPANY_NAME CLASS_NAME:$CLASS_NAME COURSE_ID:$COURSE_ID")


    val userAndCourse = jdbc.query("""
SELECT c.COURSE_NAME, c.COURSE_ID,u.ID AS USER_ID,u.NAME as USER_NAME,u.MOBILE,u.EMAIL,u.CREATE_TIME as USER_CREATE_TIME,uc.RECRUIT_ID,uc.COMPANY_ID,co.NAME AS COMPANY_NAME
,tc.CLASS_ID
,tc.CLASS_NAME
 FROM db_aries_study.TBL_USER_COURSE uc
LEFT JOIN db_aries_course.TBL_COURSE c on uc.COURSE_ID = c.COURSE_ID
LEFT JOIN db_aries_user.TBL_USER u on uc.USER_ID = u.ID
LEFT JOIN db_aries_company.TBL_COMPANY co on co.ID = uc.COMPANY_ID
LEFT JOIN db_aries_run.TBL_CLASS tc on tc.CLASS_ID = uc.CLASS_ID
WHERE
  uc.COMPANY_ID = $COMPANY_ID
  AND uc.CLASS_ID = $CLASS_ID
  AND uc.COURSE_ID = $COURSE_ID
  AND uc.IS_DELETE=0
;
""".trimIndent(), ColumnMapRowMapper())
//    p(userAndCourse)
    p("班级用户数量:${userAndCourse.size}")


//    查每个人的学习进度
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

        val courseCount = queryCourseCount(jdbc, COURSE_ID)
//        用户每个章节百分比
        val legacyWatchPerSum = queryWatchPercentSum(jdbc, RECRUIT_ID,USER_ID)
//        遗留逻辑 课程数量
        val legacyPercentStr = calcPerStr(courseCount, legacyWatchPerSum)
        val legacyPercent = calcPer(courseCount, legacyWatchPerSum)

//        app逻辑是总的WATCH_PERCENT / 视频数量
//        从章节出发 关联查询 用户每个章节百分比
        val chapterPercent = queryChapterPercent(jdbc, COURSE_ID, RECRUIT_ID, USER_ID)
        val watchPerSum = chapterPercent.sumBy { it["WATCH_PERCENT"]?.toString()?.toInt() ?: 0 }
        // 新逻辑关联到章节
        val newPercentStr = calcPerStr(courseCount, watchPerSum)
        val newPercent = calcPer(courseCount, watchPerSum)

        if ( !(legacyPercent.toDouble() >= PERCENT_MIN && legacyPercent.toDouble() < PERCENT_MAX)) {
            continue
        }

        val indent = "----"

        p("[$COURSE_ID $COURSE_NAME] [$RECRUIT_ID] [c:$courseCount ws:$watchPerSum P:$newPercentStr% l-ws:$legacyWatchPerSum  l-P:${legacyPercentStr}% ] [$USER_ID $USER_NAME $MOBILE $USER_CREATE_TIME] [$COMPANY_ID $COMPANY_NAME] ")

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

    println("--------------sql-------------------")
    println()
    sqls.forEach {
        println(it)
    }
    println()
}

