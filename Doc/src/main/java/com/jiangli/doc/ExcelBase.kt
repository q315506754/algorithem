package com.jiangli.doc

import com.jiangli.common.utils.PathUtil
import org.apache.commons.lang.StringUtils
import org.apache.poi.common.usermodel.HyperlinkType
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.NumberToTextConverter
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.io.*
import java.text.SimpleDateFormat


open  class ExcelFileMeta {
     open lateinit var  file: File
     open var  sheetIdx:Int = 0
    open lateinit var  rowRange:IntRange
     open lateinit var  colRange:IntRange

    constructor()

    constructor(file: File, sheetIdx: Int, rowRange: IntRange, colRange: IntRange) {
        this.file = file
        this.sheetIdx = sheetIdx
        this.rowRange = rowRange
        this.colRange = colRange
    }

    fun initial(file: File, sheetIdx: Int, rowRange: IntRange, colRange: IntRange) {
        this.file = file
        this.sheetIdx = sheetIdx
        this.rowRange = rowRange
        this.colRange = colRange
    }

    override fun toString(): String {
        return "ExcelFileMeta(file=$file, sheetIdx=$sheetIdx, rowRange=$rowRange, colRange=$colRange)"
    }


}

/**
 *
 *
 * @author Jiangli
 * @date 2018/3/12 10:02
 */
 enum class ExCode(str: String) {
    NO_SUCH_FILE("找不到文件")
    ,NOT_DIRECTORY("不是文件夹")
    ,NUM_ROW_OVERFLOW("所选行数%d超过了最大限制%d")
    ,NUM_COL_OVERFLOW("所选列数%d超过了最大限制%d")
    ;

    val code:String = str
}

data class InterruptException(val code:ExCode) :Exception() {
   var args:MutableList<Any> = mutableListOf()

    constructor( code:ExCode,  vararg args:Any) :this(code){
        args.forEach {
            this.args.add(it)
        }
    }

    fun getMsg():String {
        //spread
        return code.code.format(*(args.toTypedArray()))
    }

    override fun toString(): String {
        return "InterruptException(code=$code,msg=${getMsg()})"
    }


}

object FUtil {
    private val EOF = -1
    private val DEFAULT_BUFFER_SIZE = 1024 * 4

    @Throws(IOException::class)
    fun copyLarge(input: InputStream, output: OutputStream): Long {
        return copyLarge(input, output, ByteArray(kotlin.io.DEFAULT_BUFFER_SIZE))
    }

    @Throws(IOException::class)
    fun copyLarge(input: InputStream, output: OutputStream, buffer: ByteArray): Long {
        var count: Long = 0
        var n = input.read(buffer)
        while (EOF != n) {
            output.write(buffer, 0, n)
            count += n.toLong()

            n = input.read(buffer)
        }
        return count
    }
    fun copy(from: File,to: File): File {
        val inputStream = FileInputStream(from)
        val out = FileOutputStream(to)
        copyLarge(inputStream, out)
        inputStream.close()
        out.close()
        return to
    }

    fun suffix(str: String): String {
        return str.substring(str.lastIndexOf(".")+1)
    }
    fun prefix(str: String): String {
        return str.substring(0,str.lastIndexOf("."))
    }

    fun file(str: String): File {
        val file = File(str)
        if (!file.exists()) {
            throw InterruptException(ExCode.NO_SUCH_FILE)
        }
        return file
    }
    fun files(str: String): List<File> {
        val file = File(str)
        if (!file.exists()) {
            throw InterruptException(ExCode.NO_SUCH_FILE)
        }
        val mutableListOf = mutableListOf<File>()
        if (!file.isDirectory()) {
            mutableListOf.add(file)
        }else {
            mutableListOf.addAll(files(file.absolutePath))
        }
        return mutableListOf
    }
    fun dir(str: String): File {
        val file = File(str)
        if (!file.exists()) {
            file.mkdirs()
        } else {
            if (!file.isDirectory) {
                throw InterruptException(ExCode.NO_SUCH_FILE)
            }
        }

        return file
    }
}

object ExcelUtil {
    val MAX_COL = 255

    fun c(c:String):Int {
        val c1 = c.toUpperCase()[0].toInt() - 65
        return c1
    }

    fun process(inputSrc:String, sheetIdx:Int=0, fc:(file: File, workbook: XSSFWorkbook, sheet:XSSFSheet)->Unit) {
        val files = FUtil.files(inputSrc)

        files.filter { it.name.endsWith(".xlsx") }.forEach {
            val fileInputStream = FileInputStream(it)
            val workbook = XSSFWorkbook(fileInputStream)
            val sheet1 = workbook.getSheetAt(sheetIdx)

            fc(it,workbook,sheet1)

            try {
                fileInputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    fun processRow(inputSrc:String, sheetIdx:Int=0, startRow:Int=0, fc:(file: File, workbook: XSSFWorkbook, sheet:XSSFSheet, lastRowIdx:Int, lastColIdx:Int, rowIdx:Int, row:XSSFRow?)->Unit) {
        process(inputSrc, sheetIdx){
            file, workbook, sheet1 ->

            var lastColNum = ExcelUtil.maxSheetColIdx(sheet1)
            var lastRowNum = ExcelUtil.maxSheetRowIdx(sheet1,lastColNum)

            if (startRow > lastRowNum) {
                throw InterruptException(ExCode.NUM_ROW_OVERFLOW,startRow+1,lastRowNum+1)
            }

            (startRow..lastRowNum).forEach {
                val row = sheet1.getRow(it)
                fc(file, workbook, sheet1,lastRowNum,lastColNum,it,row)
            }
        }
    }

    fun processRowCell(inputSrc:String, sheetIdx:Int=0, startRow:Int=0, fc:(file: File, workbook: XSSFWorkbook, sheet:XSSFSheet, lastRowIdx:Int, lastColIdx:Int, rowIdx:Int, row:XSSFRow?, cellIdx:Int, cell:XSSFCell?, cellValue:String?)->Unit) {
        processRow(inputSrc, sheetIdx,startRow){
            file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
            if (row!=null) {
                (0..lastColIdx).forEach {
                    val cell = row.getCell(it)
                    val cellValue = getCellValue(cell)

                    fc(file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row,it,cell,cellValue)
                }
            }
        }
    }

    fun maxSheetRowIdx(sheet: XSSFSheet?, colMAX: Int = MAX_COL): Int {
        if (sheet == null) {
            return -1
        }

        var lastRowNum = sheet.lastRowNum

        return (lastRowNum downTo 0 step 1).firstOrNull { !isEmptyRow(sheet.getRow(it), colMAX) }
                ?: 0
    }

    fun maxSheetColIdx(sheet: XSSFSheet?, colMAX: Int = MAX_COL): Int {
        if (sheet == null) {
            return -1
        }

        var lastRowNum = sheet.lastRowNum

        var maxColIdx = 0
        for (i in 0..lastRowNum) {
            val row = sheet.getRow(i)

            for (j in 0..colMAX) {
                val cell = row?.getCell(j)

                if (isCellNotEmpty(cell) && j > maxColIdx) {
                    maxColIdx = j
                }
            }
        }

        return maxColIdx
    }

    fun isEmptyRow(row: XSSFRow?, colMAX: Int = MAX_COL): Boolean {
        if (row == null) {
            return true
        }
        return (0..colMAX)
                .map { row.getCell(it) }
                .none { isCellNotEmpty(it) }
    }

    fun isCellNotEmpty(cell: XSSFCell?): Boolean {
        val cellValue = getCellValue(cell)
        return cellValue?.isNotEmpty() ?: false
    }


    fun getCellValue(row:  XSSFRow?,idx:IntRange): MutableList<String> {
        val ret = mutableListOf<String>()
        idx.forEach {
            val cellValue = getCellValue(row, it)
            if (StringUtils.isNotBlank(cellValue)) {
                ret.add(cellValue!!)
            }
        }
        return ret
    }

    fun getCellValue(row:  XSSFRow?,idx:Int): String? {
      return ExcelUtil.getCellValue(row?.getCell(idx))
    }

    fun getCellValue(cell: XSSFCell?): String? {
        if (cell == null) {
            return null
        }
        val ret: String?
        when (cell.cellType) {
            XSSFCell.CELL_TYPE_BLANK -> ret = ""
            XSSFCell.CELL_TYPE_BOOLEAN -> ret = cell.booleanCellValue.toString()
            XSSFCell.CELL_TYPE_ERROR -> ret = null
            XSSFCell.CELL_TYPE_FORMULA -> {
                val wb = cell.sheet.workbook
                val crateHelper = wb.creationHelper
                val evaluator = crateHelper.createFormulaEvaluator()
                ret = getCellValue(evaluator.evaluateInCell(cell) as XSSFCell)
            }
            XSSFCell.CELL_TYPE_NUMERIC -> if (DateUtil.isCellDateFormatted(cell)) {
                val theDate = cell.dateCellValue
                val sdf = SimpleDateFormat("HH:mm:ss")
                ret = sdf.format(theDate)
            } else {
                ret = NumberToTextConverter.toText(cell.numericCellValue)
            }
            XSSFCell.CELL_TYPE_STRING -> ret = cell.richStringCellValue.string
            else -> ret = null
        }
        return ret
    }

    fun getCellValueByTitle(sheet: XSSFSheet,row:XSSFRow?, title: String): String? {
        return ExcelUtil.getCellValue(row?.getCell(getIdxByRowOneTitle(sheet, title)))
    }

    fun setCellValueByTitle(sheet: XSSFSheet,row:XSSFRow?, title: String, value: Any) {
        val idx = getIdxByRowOneTitle(sheet, title)
        var cell = row?.getCell(idx)
        if (cell == null) {
            cell = row?.createCell(idx)
        }

        when (value) {
            is Number -> cell!!.setCellValue(value.toString().toDouble())
            else -> cell!!.setCellValue(value.toString())
        }
    }

    fun getIdxByRowOneTitle(sheet: XSSFSheet, title: String): Int {
        val rowFirst = sheet.getRow(0)
        (0..255).forEach {
            if (title.equals(ExcelUtil.getCellValue(rowFirst?.getCell(it))?.trim())) {
                return it
            }
        }
        return -1
    }

    //一个行范围，列序号sCol中不同的值
    fun colValueSet(sheet1: XSSFSheet, rowRange: IntRange, sCol: Int): MutableSet<String> {
        val splitSet = mutableSetOf<String>()
        (rowRange)
                .map { sheet1.getRow(it) }
                .forEach { row ->
                    ExcelUtil.getCellValue(row?.getCell(sCol))?.let {
                        splitSet.add(it)
                    }
                }
        return splitSet
    }

}

object ExcelOldUtil {
    val MAX_COL = 255

    fun process(inputSrc:String, sheetIdx:Int=0, fc:(file: File, workbook: HSSFWorkbook, sheet: HSSFSheet)->Unit) {
        val files = FUtil.files(inputSrc)

        files.filter { it.name.endsWith(".xls") }.forEach {
            val fileInputStream = FileInputStream(it)
            val workbook = HSSFWorkbook(fileInputStream)
            val sheet1 = workbook.getSheetAt(sheetIdx)

            fc(it,workbook,sheet1)

            try {
                fileInputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}

fun main(args: Array<String>) {
//    println(lastRowNum)
//    println(lastColNum)
//    println(sRow)

//    println(InterruptException(ExCode.NO_SUCH_FILE,"啊啊").getMsg())
//    println(listOf(1, 2, 3, (arrayOf(4, 5, 6))))
//    println(listOf(1, 2, 3, *(arrayOf(4, 5, 6))))
//    ExcelUtil.process("C:\\Users\\DELL-13\\Desktop\\课程购买.xlsx"){
//        it, workbook, sheet ->  println(sheet)
//    }
//
//    ExcelUtil.processRowCell(PathUtil.desktop("课程购买.xlsx"),0,1){
//        file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row, cellIdx, cell, cellValue ->
//        println("$rowIdx x $cellIdx ,$cellValue")
//    }

    ExcelOldUtil.process(PathUtil.desktop("06原始表 - 副本.xls")){
        file: File, workbook: HSSFWorkbook, sheet: HSSFSheet ->
        println(sheet)
    }
}


// [3,4,2] -> [3->3,4->4,2->2]
fun <T> iterToPair(list:Iterable<T>): List<Pair< T, T>> {
    val ret = mutableListOf<Pair< T, T>>()
    list.forEach {
        ret.add(it to it)
    }
    return ret
}

fun extractSetFromMap(list:List<MutableMap<String, Any>>,k:String):Set<Any> {
    val ret = mutableSetOf<Any>()

    list.forEach {
        mp->mp.forEach { t, u ->
        if (k == t) {
            ret.add(u)
        }
    }
    }

    return ret
}

fun extractMapListKeys( list:List<MutableMap<String, Any>>):Set<String> {
    val ret = mutableSetOf<String>()
    list.forEach {
        mp->mp.forEach { t, u ->
        ret.add(t)
    }
    }
    return ret
}

fun writeMapToExcel(ouputFile: String, mergeMapList: List<MutableMap<String, Any>>,processer:(workbook: XSSFWorkbook, page1: XSSFSheet?, rowIdx: Int, curRow: XSSFRow?, cellIdx: Int, cell: XSSFCell, columnName:String,cellValue: String?,db: MutableMap<String, Any>)->Unit ? ={ workbook: XSSFWorkbook, page1: XSSFSheet?, rowIdx: Int, curRow: XSSFRow?, cellIdx: Int, cell: XSSFCell, columnName:String,cellValue: String?,db: MutableMap<String, Any> ->
    cell.setCellValue(cellValue)
}) {
    val config = arrayListOf<Pair<String, String>>()
    if (mergeMapList.size>0) {
        mergeMapList[0].forEach {entry ->
            config.add(entry.key to entry.key)
        }
    }
    writeMapToExcel(ouputFile,config,mergeMapList,processer)
}



fun writeMapToExcel(ouputFile: String, exconfig: ArrayList<Pair<String, String>>, mergeMapList: List<MutableMap<String, Any>>,processer:(workbook: XSSFWorkbook, page1: XSSFSheet?, rowIdx: Int, curRow: XSSFRow?, cellIdx: Int, cell: XSSFCell, columnName:String,cellValue: String?,db: MutableMap<String, Any>)->Unit ? ={ workbook: XSSFWorkbook, page1: XSSFSheet?, rowIdx: Int, curRow: XSSFRow?, cellIdx: Int, cell: XSSFCell, columnName:String,cellValue: String?,db: MutableMap<String, Any> ->
    cell.setCellValue(cellValue)

//    try {
////        尝试使用数字格式
//        cell.setCellValue(java.lang.Double.parseDouble(cellValue))
//        println(cellValue)
//    } catch (e:java.lang.Exception) {
//        cell.setCellValue(cellValue)
//    }

}) {
    val file = File(ouputFile)
    if (!file.exists()) {
        file.createNewFile()
    }

    val workbook = XSSFWorkbook()
    val page1 = workbook.createSheet()
    //    冻结首行
    page1.createFreezePane(0, 1, 0, 1)

    val row1 = page1.createRow(0)
    //标题行应用行宽
    row1.height=500
    //    val row1Style = workbook.createCellStyle()
    //    row1Style.setFillPattern(FillPatternType.BIG_SPOTS )
    //    row1Style.setFillBackgroundColor(IndexedColors.PINK.index)
    //    row1Style.setFillForegroundColor(IndexedColors.PINK.index)
    //    row1Style.fillForegroundColor= XSSFCellStyle.
    var rowIdx = 1
    val nameToIdx = mutableMapOf<String, Int>()
    exconfig.forEachIndexed { index, pair ->
        val c = row1.createCell(index)
        val newStyle = workbook.createCellStyle()

        newStyle.setWrapText(true)//自动换行
        newStyle.setVerticalAlignment(VerticalAlignment.CENTER)//垂直居中
        newStyle.setAlignment(HorizontalAlignment.LEFT)//水平居左

        //设置新填充样式
        newStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        newStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index)

        //冻结首行
        c.setCellStyle(newStyle)

        c.setCellValue(pair.second)

        nameToIdx.put(pair.first, index)
    }

    //计算列宽度
    val columnMaxWidth = mutableMapOf<String, Int>()
    mergeMapList.forEach { mp ->
        mp.forEach { entry ->
            val cellValLen = calcColumnWidth(entry?.value?.toString())
            val cellTitleLen = calcColumnWidth(entry?.key)

            var length = Math.max(cellValLen, cellTitleLen)
            length  = Math.min(10000,length)
            columnMaxWidth.merge(entry.key,length,{ t, u ->
                Math.max(t, u)
            })
        }
    }

    //应用列宽
    columnMaxWidth.forEach {entry ->
        if (nameToIdx[entry.key]!=null) {
            page1.setColumnWidth(nameToIdx[entry.key]!!,entry.value)
        }
    }

    mergeMapList.forEach { mp ->
        val curRow = page1.createRow(rowIdx)
        mp.forEach { entry ->
            val columnName = entry.key
            if (nameToIdx[columnName] != null) {
                val cellIdx = nameToIdx[columnName]!!
                val cell = curRow.createCell(cellIdx)
                val cellValue = entry.value?.toString()

                processer(workbook,page1,rowIdx,curRow,cellIdx,cell,columnName,cellValue,mp)

            }
        }
        rowIdx++
    }

    workbook.write(FileOutputStream(file))
}

fun calcColumnWidth(str:String?):Int {
    val charWidthOfCn = 600 //中文
    val charWidthOfOther = 300 //非中文
    val (cnNum, otherNum) = analyzeStringLen(str)
    return cnNum * charWidthOfCn + otherNum * charWidthOfOther
}

fun analyzeStringLen(str:String?):StrCount {
    val strCount = StrCount(0,0)
    str?.forEach { c ->
        //        println("$c ${c.toInt()}")

        if (c.toInt() < 10000) {
            strCount.otherNum++
        }else {
            strCount.cnNum++
        }
    }
    return strCount
}

data class StrCount(var cnNum:Int,var otherNum:Int){}

//用pair对补齐（没有key时put） list-map，
fun makeupMapList(mergeMapList: List<MutableMap<String, Any>>, vararg pair: Pair<String, Any>) {
    mergeMapList.forEach {
        mp->
        pair.forEach {
            if (mp[it.first] == null ) {
                mp.put(it.first,it.second)
            }
        }
    }
}

//通过键s合并list-map  返回合并后的
fun mergeMapList(s: String,vararg lists:  List<MutableMap<String, Any>>):List<MutableMap<String, Any>> {
    val ret = mutableListOf<MutableMap<String, Any>>()
    lists.forEach {
        it.forEach {
            mp ->
            if(mp[s]!=null){
                val fmp  = findOrCreate(ret,s,mp[s]!!)
                fmp.putAll(mp)
            }
        }
    }
    return ret
}

//list-map中寻找键为s 值为 any的的map，没有则新建一个放到list-map里
fun findOrCreate(ret: MutableList<MutableMap<String, Any>>, s: String, any: Any): MutableMap<String, Any> {
    ret.forEach {
        mp->
        if (mp[s]!=null && mp[s] == any) {
            return mp
        }
    }
    var mp = mutableMapOf<String, Any>()
    mp.put(s,any)
    ret.add(mp)
    return mp

}

//设置cell超链接格式
fun setHrefWhenNeed(cellValue: String?, workbook: XSSFWorkbook, cell: XSSFCell) {
    if (cellValue!=null&& cellValue.startsWith("http") && cellValue.contains("://")) {
        val helper = workbook.creationHelper

        val cellStyle = workbook.createCellStyle()

        val font = workbook.createFont()
        font.underline = Font.U_SINGLE
        font.color = IndexedColors.BLUE.getIndex()

        cellStyle.setFont(font)

        val hyperlink = helper.createHyperlink(HyperlinkType.URL)
        hyperlink.address = cellValue
        cell.hyperlink = hyperlink
        cell.cellStyle = cellStyle


    } else {

    }
}

fun queryOneField(code_jdbc: JdbcTemplate, sql: String, field: String): String {
    var ret = ""
    val query = code_jdbc.query(sql.trimIndent(), ColumnMapRowMapper())
    if (query.isNotEmpty()) {
        val any = query[0][field]
        if (any != null) {
            ret = any.toString()
        }
    }
    return ret
}
fun checkExists(code_jdbc: JdbcTemplate, sql: String): Boolean {
    val query = code_jdbc.query(sql.trimIndent(), ColumnMapRowMapper())
    return query.isNotEmpty()
}

fun replaceKey(map: MutableMap<String,Any>, org:String, dest:String):Unit {
    val v = map[org]
    map.remove(org)
    map.put(dest, v ?: "")
}