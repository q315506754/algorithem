package com.jiangli.doc.sql.helper.aries.scan

import com.jiangli.common.utils.FileUtil
import com.jiangli.doc.checkExists
import com.jiangli.doc.queryOneField
import com.jiangli.doc.setHrefWhenNeed
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.apache.commons.io.IOUtils
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File
import java.io.FileOutputStream
import java.net.URL

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
    val code_jdbc = Ariesutil.getJDBC(env)

//    val  course_env = Env.YANFA
//    val  course_env = Env.YUFA
    val course_env = Env.WAIWANG
    val course_jdbc = Ariesutil.getJDBC( course_env)


    val outputpath="""C:\Users\Jiangli\Desktop\code_map\code_map.xlsx"""
    val dir = File(outputpath).parentFile
    FileUtil.deleteFilesUnderDir(dir.absolutePath)

    val sql = """
SELECT
qm.ID AS '二维码编号',
'' AS '外网存在记录',
  qm.IMG AS '图片地址',
  qm.COURSE_ID AS '2C课程id',
  '' AS '课程名称',
  qm.CHAPTER_ID AS '2C章节id',
  '' AS '章节名称'
from `db_aries_operation`.TBL_QRCODE_MAP qm
LEFT JOIN db_aries_2c_course.TM_COURSE tc on qm.COURSE_ID = tc.COURSE_ID
LEFT JOIN db_aries_2c_course.TM_COURSE_CHAPTER tcc on qm.CHAPTER_ID = tcc.ID

WHERE
  qm.TYPE =1 AND
  qm.ID > 100 AND qm.ID < 9999

  AND  qm.CHAPTER_ID > 1

  ;
""".trimIndent()

    //create
    val qIdList = code_jdbc.query(sql, ColumnMapRowMapper())

    qIdList.forEach {
        val id = it["二维码编号"].toString()
        it["预览链接"]="http://api.g2s.cn/scan/control?code=$id"

        val courseId = it["2C课程id"].toString()
        it["课程名称"] = queryOneField(course_jdbc, """
        SELECT * FROM db_aries_2c_course.TM_COURSE WHERE COURSE_ID = $courseId;
                """, "COURSE_NAME")

        val chapterId = it["2C章节id"].toString()
        it["章节名称"] = queryOneField(course_jdbc, """
        SELECT * FROM db_aries_2c_course.TM_COURSE_CHAPTER WHERE ID = $chapterId;
                """, "TITLE")

        it["外网存在记录"] = if (checkExists(course_jdbc,"""
        SELECT * FROM db_aries_operation.TBL_QRCODE_MAP WHERE ID = $id ;
                """) == true) "√" else "x"
    }

    println(qIdList)

    writeMapToExcel(outputpath,qIdList,{ workbook, page1, rowIdx, curRow, cellIdx, cell, columnName,cellValue,db ->
        cell.setCellValue(cellValue)

        setHrefWhenNeed(cellValue, workbook, cell)

        if (columnName == "图片地址") {
            val num = db["二维码编号"].toString().toInt()

            val file = File(dir, "$num.png")
            val inputStream = URL(cellValue).openConnection().getInputStream()
            IOUtils.copyLarge(inputStream,FileOutputStream(file))

            println(num)
            println(cellValue)
        }
    })


}



