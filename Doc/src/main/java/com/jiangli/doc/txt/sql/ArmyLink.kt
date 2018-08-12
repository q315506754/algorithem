package com.jiangli.doc.txt.sql

import com.jiangli.doc.ExcelUtil
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val ouputFile = """C:\工作\项目\军职在线\107门课程（课程ID）0620.xlsx"""
    val modifyFile = """C:\工作\项目\军职在线\107门课程（课程ID）0620-link.xlsx"""

    val linkIdx = 12
    val courseIdx = ExcelUtil.c("L")

    ExcelUtil.processRowCell(ouputFile) {
        file: File, workbook: XSSFWorkbook, sheet: XSSFSheet, lastRowIdx: Int, lastColIdx: Int, rowIdx: Int, row: XSSFRow?, cellIdx: Int, cell: XSSFCell?, cellValue: String? ->
        if (rowIdx>0 && cellIdx ==courseIdx) {
            println(cellValue)

//            val url = """http://armystudy.zhihuishu.com/armystudy/militaryDetail?userId=37&courseId=$cellValue&ts=1528853470&token=5aeb66e2"""
            val url = """http://armystudy.zhihuishu.com/armystudy/militaryDetail?userId=37&courseId=$cellValue&ts=1528854470&token=5aeb66e2"""

            val createCell = row?.createCell(linkIdx)
            createCell?.setCellValue(url)

        }

        if (lastRowIdx==rowIdx) {
            workbook.write(FileOutputStream(modifyFile))
        }
    }
}
