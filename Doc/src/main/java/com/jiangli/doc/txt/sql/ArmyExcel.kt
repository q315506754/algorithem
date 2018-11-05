package com.jiangli.doc.txt.sql

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.extractSetFromMap
import com.jiangli.doc.txt.DB
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val qaDB = DB.getWendaJDBCForWaiWang()
    val xuetangDB = DB.getJDBCForWaiWang()
//    val ouputFile = """C:\工作\项目\军职在线\107门课程（附带性质）0619.xlsx"""
    val ouputFile = """C:\工作\项目\军职在线\107门课程（课程ID）0620.xlsx"""
//    val modifyFile = """C:\工作\项目\军职在线\107门课程（附带性质）0619-rs.xlsx"""
    val modifyFile = """C:\工作\项目\军职在线\107门课程（课程ID）0620-rs.xlsx"""

    val courseIdIdx = 12
    val recruitIdIdx = 12
    val recruitName = 13

    ExcelUtil.processRowCell(ouputFile) {
        file: File, workbook: XSSFWorkbook, sheet: XSSFSheet, lastRowIdx: Int, lastColIdx: Int, rowIdx: Int, row: XSSFRow?, cellIdx: Int, cell: XSSFCell?, cellValue: String? ->
        if (rowIdx>0 && cellIdx ==2) {
            println(cellValue)

            val sql = """
select c.NAME,c.COURSE_ID,r.
ID,r.NAME,r.COURSE_STARTTIME,r.COURSE_ENDTIME from db_G2S_OnlineSchool.TBL_COURSE c LEFT JOIN db_G2S_OnlineSchool.V2_RECRUIT  r ON c.COURSE_ID = r.COURSE_ID
WHERE c.NAME = '$cellValue'
  AND c.IS_DELETED=0 AND r.IS_DELETE=0
AND r.COURSE_STARTTIME > '2018-02-01 00:00:00'
AND r.COURSE_ENDTIME < '2018-07-01 00:00:00';
            """

            val query_q = xuetangDB.query(sql, ColumnMapRowMapper())
            val set = extractSetFromMap(query_q, "COURSE_ID")
            val createCell = row?.createCell(courseIdIdx)
            if (set.size == 1) {
                createCell?.setCellValue(set.iterator().next().toString())
            } else {
                createCell?.setCellValue("set.size:"+set.size)
            }
        }

        if (lastRowIdx==rowIdx) {
            workbook.write(FileOutputStream(modifyFile))
        }
    }
}
