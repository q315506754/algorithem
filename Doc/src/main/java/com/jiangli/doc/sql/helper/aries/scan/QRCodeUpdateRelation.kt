package com.jiangli.doc.sql.helper.aries.scan

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File

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

    val inputpath="""C:\Users\Jiangli\Desktop\code_map\code_map.xlsx"""
    val dir = File(inputpath).parentFile

    ExcelUtil.processRow(inputpath,0,1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val id = ExcelUtil.getCellValueByTitle(sheet,row, "二维码编号")!!.toInt()
            val courseName = ExcelUtil.getCellValueByTitle(sheet,row, "课程名称")!!
            val chapterName = ExcelUtil.getCellValueByTitle(sheet,row, "章节名称")!!

//            println("$id $courseName $chapterName")

            val sql= """
SELECT c.COURSE_ID,c.COURSE_NAME,tcc.ID as CHAPTER_ID,tcc.TITLE,tcc.SORT,tcc.IS_OPEN,tcc.IS_FREE,tcc.IS_DELETED FROM db_aries_2c_course.TM_COURSE  c
  LEFT JOIN db_aries_2c_course.TM_COURSE_CHAPTER tcc on tcc.COURSE_ID = c.COURSE_ID
WHERE
  c.COURSE_NAME like '%$courseName%' AND
    tcc.TITLE like '%$chapterName%' AND
  c.IS_DELETED=0
AND tcc.LAYER = 2
ORDER BY tcc.SORT ASC,tcc.ID ASC
;
            """.trimIndent()

//            println(sql)

            //query
            val qIdList = jdbc.query(sql, ColumnMapRowMapper())
            if (qIdList.isEmpty()) {
                System.err.println("找不到对应章节 $id $courseName $chapterName")
            } else if (qIdList.size > 1){
                System.err.println("找到多个章节 $id $courseName $chapterName $qIdList")
            } else {
                val COURSE_ID = qIdList[0]["COURSE_ID"].toString()
                val CHAPTER_ID = qIdList[0]["CHAPTER_ID"].toString()
                println("$id $COURSE_ID-$courseName $CHAPTER_ID-$chapterName  ")
                println("UPDATE `db_aries_operation`.`TBL_QRCODE_MAP` SET `COURSE_ID` = $COURSE_ID, `CHAPTER_ID` = $CHAPTER_ID WHERE `ID` = $id;")

            }
        }
    }


}

