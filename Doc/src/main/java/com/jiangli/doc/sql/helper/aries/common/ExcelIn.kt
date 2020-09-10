package com.jiangli.doc.sql.helper.aries.common

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
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

    val inputpath = """code_map\code_map.xlsx"""
    val dir = File(inputpath).parentFile

    val list = mutableListOf<String>()

    ExcelUtil.processRow(PathUtil.desktop(inputpath), 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val id = ExcelUtil.getCellValueByTitle(sheet, row, "二维码编号")!!.toInt()
            val courseName = ExcelUtil.getCellValueByTitle(sheet, row, "课程名称")
            val chapterName = ExcelUtil.getCellValueByTitle(sheet, row, "章节名称")

            var COURSE_ID: String? = courseName
            var CHAPTER_ID: String? = chapterName

            println("$COURSE_ID $CHAPTER_ID")
            list.add("UPDATE `db_aries_operation`.`TBL_QRCODE_MAP` SET `COURSE_ID` = $COURSE_ID, `CHAPTER_ID` = $CHAPTER_ID WHERE `ID` = $id;")
        }
    }

    list.forEach {
        println(it)
    }


}

