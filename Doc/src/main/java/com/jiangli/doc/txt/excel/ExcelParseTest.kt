package com.jiangli.doc.txt.excel

import com.jiangli.common.utils.CommonUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.txt.importdata.*
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.math.BigDecimal

/**
 *
 *
 * @author Jiangli
 * @date 2017/9/14 14:18
 */

fun main(args: Array<String>) {
    val base = "C:\\Users\\DELL-13\\Desktop\\codeReview\\教师主页\\教师主页二期汇总"
    val file = File(PathUtil.buildPath(base, "教师主页 ID 汇总0914 .xlsx"))
    println(file.exists())

    parseExcel(file)


//    val workbook = XSSFWorkbook(FileInputStream(file))
//    val page1 = workbook.getSheetAt(0)
//    val ROW_START_INDEX=0
//    val MAX_INDEX=page1.lastRowNum
//
//
//    for (i in ROW_START_INDEX..MAX_INDEX) {
//        val row = page1.getRow(i)
//        val cell = row.getCell(0)
//        val cell2 = row.getCell(1)
//        println("i:$i ${cell.stringCellValue} ${cell2.stringCellValue} " )
//    }

}

fun parseExcel(file:File):Excel {
    val ret = Excel()
    val workbook = XSSFWorkbook(FileInputStream(file))
    val page1 = workbook.getSheetAt(0)

    val numMergedRegions = page1.numMergedRegions
    for (i in 0..numMergedRegions-1) {
        val mergedRegion = page1.getMergedRegion(i)
        val firstColumn = mergedRegion.firstColumn
        val lastColumn = mergedRegion.lastColumn
        val firstRow = mergedRegion.firstRow
        val lastRow = mergedRegion.lastRow

        if (firstColumn==0 && lastColumn==0) {
            val username = getString(page1,firstRow,0)
            val courseName = getString(page1,firstRow,1)
            val courseId = getString(page1,firstRow,2)
            ret.row(username,{
                course(courseName,courseId.toInt()){
//                    println("    章节:")
                    for (i in firstRow..lastRow) {
                        val chapterName = getString(page1,i,3)
                        val videoId = getString(page1,i,4)

                        if(CommonUtil.isStringNotNull(chapterName) && CommonUtil.isStringNotNull(videoId))
                            lesson(chapterName,videoId.toInt()){
                            }
                    }
                }

//                println("    倾听:")
                for (i in firstRow..lastRow) {
                    val listenName = getString(page1,i,6)
                    val audioId = getString(page1,i,7)
                    if(CommonUtil.isStringNotNull(listenName) && CommonUtil.isStringNotNull(audioId))
                        listen(listenName,audioId.toInt()){
                        }
                }
            })

//            println("---------------------------------")
        }

//        println("$firstRow,$firstColumn -> $lastRow,$lastColumn   ${getString(page1,firstRow,firstColumn)}")
    }
    return ret
}

fun getString(sheet: XSSFSheet, rIdx:Int, cIdx:Int):String {
    val row = sheet.getRow(rIdx)
    val cell = row.getCell(cIdx)
    if (cell!=null) {
        val cellValue = getCellValue(cell)
        println("$rIdx,$cIdx = $cellValue")
        return cellValue
    }
    return ""
}

fun getCellValue(cell: Cell):String{

    if(cell == null) return "";

    if(cell.getCellType() == Cell.CELL_TYPE_STRING){

        return cell.getStringCellValue();

    }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){

        return java.lang.String.valueOf(cell.getBooleanCellValue());

    }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){

        return cell.getCellFormula() ;

    }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){

        return BigDecimal(cell.getNumericCellValue()).setScale(0).toString();
    }
    return "";
}
