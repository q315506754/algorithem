package com.jiangli.doc.sql

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

//    val toId = Ariesutil.getUserId(jdbc,"","13611637811").toLong()
//    val toId = Ariesutil.getUserId(jdbc,"","13766899939").toLong()
//    val toId = Ariesutil.getUserId(jdbc,"","13910973030").toLong()
    val toId = 100012344
    Ariesutil.confirmUserId(jdbc,toId)

    val courseId=3

    //接收者
    Ariesutil.validateNum("确保接收者无2c购买记录", jdbc.query("""
SELECT * from db_aries_2c_course.TM_USER_COURSE WHERE USER_ID=$toId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper()),0)
    Ariesutil.validateNum("确保接收者无2c学习记录", jdbc.query("""
SELECT * from db_aries_2c_course.TM_USER_STUDY_COURSE WHERE USER_ID=$toId AND COURSE_ID=$courseId AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper()),0)

//    插入 购买权限
    println("""
INSERT INTO `db_aries_2c_course`.`TM_USER_COURSE` (`USER_ID`, `BUY_TYPE`, `USER_TYPE`,  `COURSE_ID`,`CREATOR_ID`) VALUES ('${toId}', '100', '100', '$courseId','1000');
    """)
//
//    插入 学习记录
    println("""
INSERT INTO `db_aries_2c_course`.`TM_USER_STUDY_COURSE` (`USER_ID`, `STUDY_TYPE`, `USER_TYPE`,  `COURSE_ID`,`CREATOR_ID`) VALUES ('${toId}', '100', '100', '$courseId','1000');
    """)

}