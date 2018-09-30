package com.jiangli.doc.sql.helper.aries.order

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.CourseName2CQueryer
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/4 16:30
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    //课程名字 姓名 手机
    val excelName = """副本 开课名单-0930-上午"""
//    val excelName = """副本 开课名单-0920-上午.xlsx"""

    val endIndex = excelName.lastIndexOf(".xlsx")
    val realExcelPrefix = excelName.substring(0, if(endIndex<0) excelName.length else endIndex)
    println(realExcelPrefix)

    val path = """C:\Users\Jiangli\Desktop\$realExcelPrefix.xlsx"""
    var count = 0
    var suc_count = 0
    var fail_count = 0

    val fixedCourseId = mutableMapOf<String, Int>()
    fixedCourseId.put("未知课程名字",-1)

    val insertCourse = mutableListOf<String>()
    val insertUser = mutableListOf<String>()
    val inf = CourseName2CQueryer()
    ExcelUtil.processRow(path){
        file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx>=1) {
            count++

            val courseName = ExcelUtil.getCellValue(row?.getCell(0))
            var courseId:Any?
            if (fixedCourseId.containsKey(courseName)) {
                courseId = fixedCourseId[courseName]
            } else {
                courseId = Ariesutil.injectTest(jdbc, "'$courseName'", inf)["COURSE_ID"]
            }
//            val courseId = courseIdMap[courseName]
            val name = ExcelUtil.getCellValue(row?.getCell(1))
            val mobile = ExcelUtil.getCellValue(row?.getCell(2))

            val s = "SELECT ID FROM db_aries_user.TBL_USER WHERE MOBILE=$mobile and IS_DELETED = 0;"
            val query = jdbc.query(s, ColumnMapRowMapper())
            if (query.isEmpty()) {
//                注册账号
                insertUser.add("INSERT INTO `db_aries_user`.`TBL_USER` (`AREA_CODE`, `MOBILE`, `PASSWORD`, `NAME`, `CREATE_TIME`, `IS_DELETED`, `IS_DISABLED`) VALUES ( '+86',  '$mobile',  '1aa14247ac5623ca07a25a099cd66527d7c39705e29a225e5e0f2a6a6216b53e', '$name', '2018-09-04 20:08:08', '0', '0');")
            } else {
                val toId =query[0]["ID"]

                try {//接收者
                    Ariesutil.validateNum("确保接收者无2c购买记录", jdbc.query("""
    SELECT * from db_aries_2c_course.TM_USER_COURSE WHERE USER_ID=$toId AND COURSE_ID=$courseId AND IS_DELETED=0;
        """.trimIndent(), ColumnMapRowMapper()), 0)
                    Ariesutil.validateNum("确保接收者无2c学习记录", jdbc.query("""
    SELECT * from db_aries_2c_course.TM_USER_STUDY_COURSE WHERE USER_ID=$toId AND COURSE_ID=$courseId AND IS_DELETED=0;
        """.trimIndent(), ColumnMapRowMapper()), 0)

//    插入 购买权限
                    insertCourse.add("""
    INSERT INTO `db_aries_2c_course`.`TM_USER_COURSE` (`USER_ID`, `BUY_TYPE`, `USER_TYPE`,  `COURSE_ID`,`CREATOR_ID`) VALUES ('${toId}', '100', '100', '$courseId','1000');
        """.trim())
//
//    插入 学习记录
                    insertCourse.add("""
    INSERT INTO `db_aries_2c_course`.`TM_USER_STUDY_COURSE` (`USER_ID`, `STUDY_TYPE`, `USER_TYPE`,  `COURSE_ID`,`CREATOR_ID`) VALUES ('${toId}', '100', '100', '$courseId','1000');
        """.trim())
                    suc_count++
                } catch (e: Exception) {
                    fail_count++
                    System.err.println("$name  $mobile $toId 已经购买过课程 $courseId $courseName")
//                    println("$name  $mobile $toId 已经购买过课程 $courseId $courseName")
                }
            }
        }
    }


    println(insertCourse.size)
    println("count:$count")
    println("suc_count:$suc_count")
    println("fail_count:$fail_count")

    println("###################################;")
    insertUser.forEach {
        println(it)
    }
    println("###################################;")
    insertCourse.forEach {
        println(it)
    }
    println("###################################;")
}