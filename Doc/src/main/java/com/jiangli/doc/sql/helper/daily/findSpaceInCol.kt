package com.jiangli.doc.sql.helper.daily

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

/**
 *
 *
 * @author Jiangli
 * @date 2020/6/4 15:00
 */
fun main(args: Array<String>) {
    val inputpath = PathUtil.desktop("""订单销售情况导出.xlsx""")

    ExcelUtil.processRowCell(inputpath) {
        file: File, workbook: XSSFWorkbook, sheet: XSSFSheet, lastRowIdx: Int, lastColIdx: Int, rowIdx: Int, row: XSSFRow?, cellIdx: Int, cell: XSSFCell?, cellValue: String? ->
        if (rowIdx>0 && cellIdx ==1) {
//            println(cellValue)

            if (cellValue?.trim()?.length != cellValue?.length) {
                println("${rowIdx+1}行 $cellValue")
            }
        }
    }
}