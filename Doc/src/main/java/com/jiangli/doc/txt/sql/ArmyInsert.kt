package com.jiangli.doc.txt.sql

import com.jiangli.doc.ExcelUtil
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

fun main(args: Array<String>) {
    val ouputFile = """C:\工作\项目\军职在线\107门课程（课程ID）0620.xlsx"""

    val colIdx = ExcelUtil.c("L")

    ExcelUtil.processRowCell(ouputFile) {
        file: File, workbook: XSSFWorkbook, sheet: XSSFSheet, lastRowIdx: Int, lastColIdx: Int, rowIdx: Int, row: XSSFRow?, cellIdx: Int, cell: XSSFCell?, cellValue: String? ->
        if (rowIdx>0 && cellIdx ==colIdx) {
//            println(cellValue)

            val sql =
"""INSERT INTO DB_ARMY_STUDY.ARMY_COURSE(COURSE_ID, START_TIME) VALUES ($cellValue,'2018-03-01 00:00:00');"""
            println(sql)
        }

    }
}
