package com.jiangli.doc.sql.helper.aries.common

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
    //    val env = Env.YANFA
    //    val env = Env.YUFA


    ExcelUtil.processRow(PathUtil.desktop("""code_map\code_map.xlsx"""), 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val id = ExcelUtil.getCellValueByTitle(sheet, row, "二维码编号")!!.toInt()
            val courseName = ExcelUtil.getCellValueByTitle(sheet, row, "课程名称")
            val chapterName = ExcelUtil.getCellValueByTitle(sheet, row, "章节名称")

            println("$courseName $chapterName")
        }
    }


}

