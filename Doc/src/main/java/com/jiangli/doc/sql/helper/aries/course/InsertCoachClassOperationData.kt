package com.jiangli.doc.sql.helper.aries.course

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


//  w xiaoping  100007071
//  w wo  100001239
//    val users = arrayListOf(
//            "100001239"
//    )

//    zhengshi
    val users = arrayListOf(
            "100003653"
            ,"100003731"
    )

    val inputpath = PathUtil.desktop("处理数据.xlsx")

    val list = mutableListOf<String>()

    var prevCourseId:String? = null

    users.forEach { userId ->
        println("update `db_aries_run`.`COACH_CLASS` SET IS_DELETED=1,UPDATE_TIME='2020-06-13' WHERE USER_ID=$userId AND IS_DELETED=0;")
    }

    users.forEach {
        userId ->
        ExcelUtil.processRow(inputpath, 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
            if (rowIdx >= 1) {
//            val tagName = ExcelUtil.getCellValueByTitle(sheet, row, "板块")
                val courseId = ExcelUtil.getCellValueByTitle(sheet, row, "课程ID")
                val classId = ExcelUtil.getCellValueByTitle(sheet, row, "班级ID")

                if (courseId != null && courseId?.isNotBlank()) {
                    prevCourseId = courseId
                }

//                if (prevCourseId == "600143") {
//                    return@processRow
//                }

                println("##$prevCourseId $courseId $classId ;")
                val time = "2020-02-02 04:02:02"
                println("INSERT INTO `db_aries_run`.`COACH_CLASS`(`USER_ID`, `COURSE_ID`, `CLASS_ID`, `DELI_TRAN_ID`, `DELI_PLAN_ID`, `REMARK`, `IS_DELETED`, `CREATE_TIME`, `UPDATE_TIME`, `CREATE_PERSON`, `DELETE_PERSON`) VALUES ($userId, $prevCourseId, $classId, NULL, NULL, NULL, 0, '$time', '$time', -1, NULL);")
                println("################;")

            }

        }
    }


    list.forEach {
        println(it)
    }



}