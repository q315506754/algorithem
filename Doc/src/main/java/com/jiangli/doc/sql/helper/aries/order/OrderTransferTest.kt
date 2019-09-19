package com.jiangli.doc.sql.helper.aries.order

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */



fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


    val fromId = 100003685
//    val toId = Ariesutil.getUserId(jdbc,"张陆诗")
    val toId  = 100030298

//    100010567 爱尔丹珠 -> 100011250 null  2018年8月27日13:27:44

    Ariesutil.confirmUserId(jdbc, fromId)
    Ariesutil.confirmUserId(jdbc, toId)

//    val courseId=61
    val courseId=7

    //赠送者
    Ariesutil.validateNum("确保赠送者有2c购买记录", jdbc.query("""
SELECT * from db_aries_2c_course.TM_USER_COURSE WHERE USER_ID=$fromId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper()))
    Ariesutil.validateNum("确保赠送者有2c学习记录", jdbc.query("""
SELECT * from db_aries_2c_course.TM_USER_STUDY_COURSE WHERE USER_ID=$fromId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper()))

    //接收者
    Ariesutil.validateNum("确保接收者无2c购买记录", jdbc.query("""
SELECT * from db_aries_2c_course.TM_USER_COURSE WHERE USER_ID=$toId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper()), 0)
    Ariesutil.validateNum("确保接收者无2c学习记录", jdbc.query("""
SELECT * from db_aries_2c_course.TM_USER_STUDY_COURSE WHERE USER_ID=$toId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper()), 0)

//    赠送 购买权限
    println("""
update db_aries_2c_course.TM_USER_COURSE set USER_ID=$toId WHERE USER_ID=$fromId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """)

//    赠送 学习记录
    println("""
update db_aries_2c_course.TM_USER_STUDY_COURSE set USER_ID=$toId WHERE  USER_ID=$fromId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """)

}