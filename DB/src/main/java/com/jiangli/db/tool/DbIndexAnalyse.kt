package com.jiangli.db.tool

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.apache.poi.xssf.usermodel.XSSFClientAnchor
import org.apache.poi.xssf.usermodel.XSSFDrawing
import org.apache.poi.xssf.usermodel.XSSFRichTextString
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.sql.DriverManager
import java.util.*







/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 15:22
 */
fun main(args: Array<String>) {
    val excludeTables = """
EX
TBL_USER_STUDY_TIME
    """.trimIndent()

    val env = Env.YANFA
//    val env = Env.WAIWANG

    val jdbc = Ariesutil.getJDBC(env)
    val ds = Ariesutil.getDS(env)

    //aries预发
    val DB_URL ="${ds.url}&user=${env.username}&password=${env.pwd}"


    val ouputFile = PathUtil.desktop("""数据库分析结果.xlsx""")
    val list = mutableListOf<MutableMap<String,Any>>()

    //fixed
    Class.forName("com.mysql.jdbc.Driver")
    val props = Properties()
    props.setProperty("remarks", "true"); //设置可以获取remarks信息
    props.setProperty("useInformationSchema", "true"); //设置可以获取tables remarks信息
    val connection = DriverManager.getConnection(DB_URL, props)

    DbBaseTool.iterateRepoTable(DB_URL){ metaData, databaseName, tableName, fullName ->
        if (databaseName.startsWith("db_")) {
            try {
                if (tableName.toLowerCase().startsWith("qrtz_")) {
                    return@iterateRepoTable
                }

                //表主键
                val pkRs = metaData.getPrimaryKeys(databaseName, null, tableName)
                pkRs.next()
                val pkColName = pkRs.getString("COLUMN_NAME")

//                val num = jdbc.queryForObject("""
//    SELECT COUNT(1) as c FROM  $fullName;
//""".trimIndent(), ColumnMapRowMapper())["c"]!!

                val num = jdbc.queryForObject("""
select table_rows as c from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = '$databaseName' AND TABLE_NAME = '$tableName';
""".trimIndent(), ColumnMapRowMapper())["c"]!!

                val createSql = queryCreateTableSqlByFullTblName(connection, fullName)
                var tbl:Tbl = parseTbl(createSql!!)
                val fieldsNotIncludePk = tbl.fields.map { dto-> dto.name}.filter { it!= pkColName}
                val fieldsIdLc = fieldsNotIncludePk.map { dto-> dto.toLowerCase()}.filter { it.contains("_id")}
                val idxLc = tbl.indexes.map { dto-> dto.fullString.toLowerCase()}
                val idFieldNotInIdx = mutableListOf<String>()
                for (idF in fieldsIdLc) {
                    if (idxLc.none { it.contains(idF) }) {
                        idFieldNotInIdx.add(idF)
                    }
                }

                val one = mutableMapOf<String,Any>()
                one.put("库名",databaseName)
                one.put("表名",tableName)
                one.put("数据行数",num)
                one.put("总字段数",tbl.fields.size)
                one.put("XX_ID字段数",fieldsIdLc.size)
                one.put("总索引数",tbl.indexes.size)
                one.put("XX_ID字段无索引数",idFieldNotInIdx.size)
                one.put("XX_ID字段无索引明细",idFieldNotInIdx.joinToString(","))
                list.add(one)
            } catch (e: Exception) {
                e.printStackTrace()
                System.err.println(fullName)
            }
        }
    }

    list.sortByDescending { it["数据行数"].toString().toInt() }

    var p: XSSFDrawing? = null

    writeMapToExcel(ouputFile,list) { workbook, page1, rowIdx, curRow, cellIdx, cell, columnName, cellValue, db ->

        try {
//        尝试使用数字格式
            val parseDouble = java.lang.Double.parseDouble(cellValue)
            cell.setCellValue(parseDouble)
//            println(cellValue)
            //创建绘图对象
            if (p == null) {
                p = page1?.createDrawingPatriarch()
            }

            //前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
            val comment = p?.createCellComment(XSSFClientAnchor(0, 0, 0, 0, rowIdx, cellIdx,5, 6))

            //输入批注信息
            val text = XSSFRichTextString("数字：$parseDouble")
            comment?.setString(text)
            comment?.setAuthor("jl")

            cell.setCellComment(comment)
        } catch (e:java.lang.Exception) {
            e.printStackTrace()
            cell.setCellValue(cellValue)
        }
    }
}