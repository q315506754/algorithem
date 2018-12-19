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

    //    val inputpath="""C:\Users\Jiangli\Desktop\code_map\code_map.xlsx"""
//    val inputpath = """C:\Users\Jiangli\Desktop\code_map\安司长的11个课程.xlsx"""
    val inputpath = """C:\Users\Jiangli\Desktop\code_map\安司长的11个课程2.xlsx"""
    val dir = File(inputpath).parentFile

    val list = mutableListOf<String>()

    ExcelUtil.processRow(inputpath, 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val id = ExcelUtil.getCellValueByTitle(sheet, row, "二维码编号")!!.toInt()
            val courseName = ExcelUtil.getCellValueByTitle(sheet, row, "课程名称")
            val chapterName = ExcelUtil.getCellValueByTitle(sheet, row, "章节名称")

            var COURSE_ID: String? = null
            var CHAPTER_ID: String? = null

            if (chapterName != null) {
                val sql = """
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

                //query
                val qIdList = jdbc.query(sql, ColumnMapRowMapper())

                COURSE_ID = qIdList[0]["COURSE_ID"].toString()
                CHAPTER_ID = qIdList[0]["CHAPTER_ID"].toString()
            } else {
                //查小节
                var qIdList = jdbc.query("""
SELECT c.COURSE_ID,c.COURSE_NAME,tcc.IS_PUBLISHED, tcc.ID as CHAPTER_ID,tcc.TITLE,tcc.LAYER,tcc.SORT,tcc.IS_OPEN,tcc.IS_FREE,tcc.IS_DELETED FROM db_aries_2c_course.TM_COURSE  c
  LEFT JOIN db_aries_2c_course.TM_COURSE_CHAPTER tcc on tcc.COURSE_ID = c.COURSE_ID
WHERE
  c.COURSE_NAME like '%$courseName%' AND
  c.IS_DELETED=0
  AND tcc.IS_DELETED = 0
  AND tcc.LAYER=2
  AND tcc.VIDEO_ID > 0
ORDER BY tcc.ID ASC
LIMIT 1;
            """.trimIndent(), ColumnMapRowMapper())
                if (qIdList.isEmpty()) {
                    qIdList = jdbc.query("""
SELECT c.COURSE_ID,c.COURSE_NAME,tcc.ID as CHAPTER_ID,tcc.TITLE,tcc.LAYER,tcc.SORT,tcc.IS_OPEN,tcc.IS_FREE,tcc.IS_DELETED FROM db_aries_2c_course.TM_COURSE  c
  LEFT JOIN db_aries_2c_course.TM_COURSE_CHAPTER tcc on tcc.COURSE_ID = c.COURSE_ID
WHERE
  c.COURSE_NAME like '%$courseName%' AND
  c.IS_DELETED=0
  AND tcc.IS_DELETED = 0
  AND tcc.LAYER=1
  AND tcc.VIDEO_ID > 0
ORDER BY tcc.ID ASC
LIMIT 1;
            """.trimIndent(), ColumnMapRowMapper())
                }

                if (qIdList.isNotEmpty()) {
                    COURSE_ID = qIdList[0]["COURSE_ID"].toString()
                    CHAPTER_ID = qIdList[0]["CHAPTER_ID"].toString()
                }
            }

            println("excel:$id $courseName $chapterName")
            //            println(sql)
            val message = "db:courseName:$courseName COURSE_ID:$COURSE_ID CHAPTER_ID:$CHAPTER_ID"
            if (COURSE_ID == null || CHAPTER_ID == null) {
                System.err.println(message)
            } else {
                println(message)
            }

            println("http:///m.g2s.cn/courseVideoShare/getVideoSharePage?courseId=$COURSE_ID&chapterId=$CHAPTER_ID")
            list.add("UPDATE `db_aries_operation`.`TBL_QRCODE_MAP` SET `COURSE_ID` = $COURSE_ID, `CHAPTER_ID` = $CHAPTER_ID WHERE `ID` = $id;")
        }
    }

    list.forEach {
        println(it)
    }


}

