package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate

/**
 * 标签同步器
 *
 * @author Jiangli
 * @date 2019/11/11 15:03
 */
fun main(args: Array<String>) {
//        val env = Env.YANFA
//        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val inputpath = """C:\工作\项目\aries改版191104\线上企业数据_1.xlsx"""


    var course = getExcelDistinctColData(inputpath, 2, 3)
    println("$course")
    var db_course = queryNamesOfType(jdbc,4)
    println("$db_course")
    analyseList(course,db_course,::analyseConvertXuexi)
    printSplit()

    var xingzhi = getExcelDistinctColData(inputpath, 2, 4)
    println("$xingzhi")
    var db_xingzhi = queryNamesOfType(jdbc,2)
    println("$db_xingzhi")
    analyseList(xingzhi,db_xingzhi,::analyseConvertXingzhi)
    printSplit()

    var hangye = getExcelDistinctColData(inputpath, 2, 5)
    println("$hangye")
    var db_hangye = queryNamesOfType(jdbc,1)
    println("$db_hangye")
    analyseList(hangye,db_hangye,::analyseConvertHangye)
    printSplit()

    var guimo = getExcelDistinctColData(inputpath, 2, 6)
    println("$guimo")
    var db_guimo = queryNamesOfType(jdbc,3)
    println("$db_guimo")
    analyseList(guimo,db_guimo,::analyseConvertGuimo)
    printSplit()


}

fun analyseList(input: MutableSet<String>, db_course: List<String>,func:(List<String>,String)->String?) {
    input.forEach {
        if (func(db_course,it) != null) {
            return@forEach
        }

        if (!db_course.contains(it)) {
            val analyseSimilarity = analyseSimilarity(db_course, it)
            System.err.println("$it 相似度匹配结果:$analyseSimilarity")
        }
    }
}

data class MatchResult(val str:String,val p:Double) :Comparable<MatchResult>{
    override fun compareTo(other: MatchResult): Int {
        return p.compareTo(other.p)
    }
}
fun analyseSimilarity(db_course: List<String>, toBe: String): MutableList<MatchResult> {
    val list = mutableListOf<MatchResult>()
    db_course.forEach {
        val rs = analyseSimilarity(it, toBe)
        list.add(rs)
    }
    list.sort()
    list.reverse()
    return list
}

fun analyseSimilarity(one: String, toBe: String): MatchResult {
    val maxLength = maxOf(one.length, toBe.length)
    var matched = 0
    one.forEach {
        val oit = it
        toBe.forEach {
            if(oit == it)
                matched++
        }
    }
    return MatchResult(one,matched*1.0/maxLength)
}

fun analyseConvertXuexi(db_course: List<String>,str:String): String? {
    val mp = mutableMapOf<String,String>(
        "" to ""
    )
    if (db_course.contains(mp[str])) {
        return mp[str]
    }
    return null
}
fun analyseConvertXingzhi(db_course: List<String>,str:String): String? {
    val mp = mutableMapOf<String,String>(
        "国有企业" to "国企"
    )
    if (db_course.contains(mp[str])) {
        return mp[str]
    }
    return null
}
fun analyseConvertHangye(db_course: List<String>,str:String): String? {
    val mp = mutableMapOf<String,String>(
        "教育服务" to "教育"
        ,"建筑机械" to "建筑"
    )
    if (db_course.contains(mp[str])) {
        return mp[str]
    }
    return null
}
fun analyseConvertGuimo(db_course: List<String>,str:String): String? {
    val list = mutableListOf<IntRange>()
    db_course.forEach {
        val db_each = it
        val numbersOfString = findNumbersOfString(db_each)
        if (numbersOfString.size == 1) {
            if (db_each.contains("以内")) {
                list.add(0..numbersOfString[0].toInt())
            } else {
                list.add(numbersOfString[0].toInt()..Int.MAX_VALUE)
            }
        } else {
            list.add(numbersOfString[0].toInt()..numbersOfString[1].toInt())
        }
    }

    val toInt = str.toInt()

    list.forEachIndexed { index, it ->
        if (toInt>= it.first && toInt<it.last) {
//            println("$str -> ${db_course[index]}")
            return db_course[index]
        }
    }
    return null
}

fun findNumbersOfString(str:String): List<String> {
    val ret = mutableListOf<String>()
    val groupValues = Regex("\\d+").findAll(str)
    groupValues.forEach {
        ret.add(it.groupValues[0])
//        println(it.groupValues[0])
    }
    return ret
}

private fun printSplit() {
    println("------------------------")
}

internal fun queryNamesOfType(jdbc: JdbcTemplate,type:Int): List<String> {
    val list = jdbc.query("""
SELECT DISTINCT(NAME) FROM db_aries_operation.TBL_COMMON_CATEGORY_ITEMS WHERE TYPE=$type AND IS_DELETED = 0;
            """.trimIndent(), ColumnMapRowMapper())
    val map = list.map { it["NAME"].toString() }
    return map
}
internal fun queryNamesIdMap(jdbc: JdbcTemplate,type:Int): MutableMap<String, String> {
    val list = jdbc.query("""
SELECT NAME,ID FROM db_aries_operation.TBL_COMMON_CATEGORY_ITEMS WHERE TYPE=$type AND IS_DELETED = 0;
            """.trimIndent(), ColumnMapRowMapper())
    val map = mutableMapOf<String,String>()
    list.forEach {
        map.put( it["NAME"].toString(), it["ID"].toString())
    }
    return map
}

private fun getExcelDistinctColData(inputpath: String, startRow: Int, colIdx: Int): MutableSet<String> {
    var names = mutableSetOf<String>()

    ExcelUtil.processRow(inputpath, 0, startRow) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        val cnName = ExcelUtil.getCellValue(row, colIdx)!!.trim()
        names.add(cnName)
    }
    return names
}