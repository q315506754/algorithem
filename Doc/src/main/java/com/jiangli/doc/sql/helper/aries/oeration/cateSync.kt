package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

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

    val inputpath = """C:\工作\项目\aries改版191104\分类数据.xlsx"""

//    var types = 1..1
    var types = 1..4

    types.forEach {
        var type = it
        var sort = 1
        var names = mutableSetOf<String>()

        ExcelUtil.processRow(inputpath, type-1, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
            val cnName = ExcelUtil.getCellValue(row, 0)!!.trim()

            if (cnName.isBlank()) {
                return@processRow
            }

            names.add(cnName)
            val associateId = ExcelUtil.getCellValue(row, 1)
//            println(cnName)
//            println(associateId)

            val list = jdbc.query("""
SELECT * FROM db_aries_operation.TBL_COMMON_CATEGORY_ITEMS WHERE TYPE=$type AND IS_DELETED = 0 AND NAME = '$cnName';
            """.trimIndent(), ColumnMapRowMapper())

            if (list.isEmpty()) {
                println("""INSERT INTO `db_aries_operation`.`TBL_COMMON_CATEGORY_ITEMS`( `TYPE`, `ASSOCIATION_ID`, `NAME`, `SORT`) VALUES ($type, $associateId, '$cnName', $sort);""")
            } else {
                val db = list[0]
                println("""UPDATE `db_aries_operation`.`TBL_COMMON_CATEGORY_ITEMS` SET SORT = ${sort} ,ASSOCIATION_ID = $associateId  WHERE ID = ${db["ID"]} ;""")
            }

            sort++
        }

        val joinToString = names.joinToString(){ s -> "'$s'" }
        val querylist = jdbc.query("""
SELECT * FROM db_aries_operation.TBL_COMMON_CATEGORY_ITEMS WHERE TYPE=$type AND IS_DELETED = 0 AND NAME NOT IN ($joinToString);
            """.trimIndent(), ColumnMapRowMapper())
        if (querylist.isNotEmpty()) {
            querylist.forEach {
                System.err.println("#类型不在excel type:$type ${it["ID"]}:${it["NAME"]} ${it["ASSOCIATION_ID"]}")
            }
        }
    }

}