package com.jiangli.db.tool

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

/**
 *
 * db结构同步器
 *
 * 可自动定位所在库，多个库有相同表时有红色错误提示
 *
 */
fun main(args: Array<String>) {
//    db_org_course.AAA

    val tables = """
    TRANSMIT_ITEM
	TRANSMIT_MAIN
	RELA_COURSE_MODULE
	DATA_ATTACHES
	FLOW_META_PROCESS
	FLOW_NODE
	FLOW_NODE_RULE
	FLOW_PROCESS
    FLOW_PROCESS_DATA
	FLOW_PROCESS_TRANSIT_LOG
	ZHISHI_COACH
	ZHISHI_COACH_RELA
    """.trimIndent()

    //aries 研发
    val SRC_DB_URL = "jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava"

//    外网
//    val DEST_DB_URL = "jdbc:mysql://rm-bp1yjg70fe47ml9gueo.mysql.rds.aliyuncs.com:3306?user=yuyang&password=yuyang@180418"

//    预发
    val DEST_DB_URL = "jdbc:mysql://120.92.138.210:3306?user=root&password=ablejava"

    val outputPath = PathUtil.desktop(""" 同步语句123.txt""")

    //fixed
    Class.forName("com.mysql.jdbc.Driver")
    val props = Properties()
    props.setProperty("remarks", "true"); //设置可以获取remarks信息
    props.setProperty("useInformationSchema", "true"); //设置可以获取tables remarks信息
    val connection = DriverManager.getConnection(SRC_DB_URL, props)
    val connectionDest = DriverManager.getConnection(DEST_DB_URL, props)
    val metaData = connection.metaData


    val list = mutableSetOf<String>()
    tables.split("\n").forEach {
        var one = it.trim()
        one = one.replace("`","")
        if (one.isBlank()) {
            return@forEach
        }
        //        println(one)

//        不包含库名
        if (!one.contains(".")) {
            //            查找库
            val ls: List<String> = queryTableAndReposByTblName(connection, one)
            if (ls.size > 1 || ls.size == 0) {
                System.err.println("can not locate table ${one} from ${ls}")
            } else {
                println("find table ${one} -> ${ls[0]}")
                list.add(ls[0])
            }
        } else {
            list.add(one)
        }
    }

    var sb = StringBuilder()
    //    建表语句
    list.forEach {
        val fromSql = queryCreateTableSqlByFullTblName(connection, it)
        val destSql = queryCreateTableSqlByFullTblName(connectionDest, it)

        if (fromSql == null) {
            System.err.println("不存在表  ${it} ,故无法找到建表sql")
        } else {

//            同步到目标库中

//            需要更新表
            if (destSql!=null) {
                val syncSql = getSyncSql(it, fromSql, destSql)
                if (syncSql.isNotBlank()) {
                    println("Update $it")
                    sb.append(syncSql)
                    sb.append(";\n")
                    sb.append("\n")
                } else {
                    println("已为最新结构 $it")
                }
//                println("update $it $destSql")
            } else {
                println("New $it")
//                新建表
                sb.append(fromSql)
                sb.append(";\n")
                sb.append("\n")
            }
        }
    }

    //    生成
    val body = sb.toString()
    generateFile(body, outputPath)

    println("已生成文件,字数:${body.length},行数:${body.split("\n").size}")

    FileUtil.openFile(File(outputPath))
}

private fun generateFile(body:String, vararg path:String): File {
    val absPath = concatPath(*path)

    PathUtil.ensureFilePath(absPath)

    val fileOutputStream = FileOutputStream(absPath)
    IOUtils.write(body, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()

    return File(absPath)
}

private fun concatPath(vararg path:String): String {
    val sb = StringBuilder()
    path.forEach {
        sb.append(it)
        sb.append(System.getProperty("file.separator"))
    }
    sb.deleteCharAt(sb.lastIndex)
    val absPath = sb.toString()
    return absPath
}

fun getSyncSql(it: String, fromSql: String, destSql: String): String {
    var from:Tbl = parseTbl(fromSql)
    var dest:Tbl = parseTbl(destSql)

    val addField = mutableListOf<Field>()
    val modifyField = mutableListOf<Field>()
    val addIndex= mutableListOf<Index>()

    for (field in from.fields) {
        val dF = dest.findField(field)
//        找到同样的字段
        if (dF!=null) {
//            有更新
            if (dF.fullString != field.fullString) {
                field.remark = "源:${field.fullString} 目标:${dF.fullString}"
                modifyField.add(field)
            }
        }else {
            addField.add(field)
        }
    }

    for (index in from.indexes) {
        val dF = dest.findIndex(index)
//        找到同样的字段
        if (dF!=null) {
//            有更新
//            if (dF.fullString != index.fullString) {
//                index.remark = "源:${index.fullString} 目标:${dF.fullString}"
//                modifyField.add(index)
//            }
        }else {
            addIndex.add(index)
        }
    }

    val sb = StringBuilder()

    var hasPrev = false

    if (addField.isNotEmpty()) {
        for (field in addField) {
            if (hasPrev) {
                sb.append(",")
            }
            sb.append("ADD COLUMN ${field.fullString}\n")
            hasPrev = true
        }
    }

    if (modifyField.isNotEmpty()) {
        for (field in modifyField) {
            if (hasPrev) {
                sb.append(",")
            }
            sb.append("MODIFY COLUMN ${field.fullString}\n")
            hasPrev = true
        }
    }

    if (addIndex.isNotEmpty()) {
        for (index in addIndex) {
            if (hasPrev) {
                sb.append(",")
            }
            sb.append("ADD INDEX${index.name}\n")
            hasPrev = true
        }
    }

    val orgString = sb.toString()

    if (orgString.isNotBlank()) {
        return """
ALTER TABLE ${it}
${orgString}
        """.trimIndent()
    }

    return orgString
}

fun parseTbl(fromSql: String): Tbl {
    val  ret = Tbl()

    fromSql.split("\n").forEach {
        var line = it.trim()
        if (line.isBlank()) {
            return@forEach
        }

//        去掉结束的符号
        if (line.endsWith(",")) {
            line = line.substring(0,line.length-1)
        }

        val leftIdx = line.indexOf("`")
        val rightIdx = line.lastIndexOf("`")
//        列
        if (leftIdx == 0 && rightIdx > leftIdx) {
            val colName = line.substring(leftIdx+1,rightIdx).toUpperCase()
            ret.fields.add(Field(line,colName))
        }

//        索引
        if (line.startsWith("KEY")) {
            val leftIdx = line.indexOf("(")
            val rightIdx = line.lastIndexOf(")")
            val indexName = line.substring(leftIdx,rightIdx+1).toUpperCase()
            ret.indexes.add(Index(line,indexName))
        }
    }

    return ret
}

data class Tbl(val fields:MutableList<Field> = mutableListOf(),val indexes:MutableList<Index> = mutableListOf()) {
    fun containField(field: Field): Boolean {
        return findField(field) != null
    }
    fun findField(field: Field): Field? {
        for (one in fields) {
            if (one.name == field.name) {
                return one
            }
        }
        return null
    }
    fun findIndex(field: Index): Index? {
        for (one in indexes) {
            if (one.name == field.name) {
                return one
            }
        }
        return null
    }
}

data class Field(var fullString:String,var name:String,var remark:String="")
data class Index(var fullString:String,var name:String,var remark:String="")

fun queryCreateTableSqlByFullTblName(connection: Connection, fullName: String): String? {
    try {
        val statement = connection.createStatement()
        val rs = statement.executeQuery("show create table ${fullName};")

        val repo = fullName.substring(0, fullName.indexOf(".")).replace("`","")
        val tbl = fullName.substring(fullName.indexOf(".") + 1).replace("`","")

        while (rs.next()) {
            var sql = rs.getString(2)
            sql = sql.replace("CREATE TABLE `${tbl}`", "CREATE TABLE `${repo}`.`${tbl}`")
            sql = sql.replace("CREATE TABLE ${tbl}", "CREATE TABLE `${repo}`.`${tbl}`")
            //            println(sql)
            return sql
        }
    } catch (e: Exception) {
    }
    return null
}

fun queryTableAndReposByTblName(connection: Connection, one: String): List<String> {
    val ret = mutableListOf<String>()
    val queryDbSt = connection.createStatement()
    val queryDBRs = queryDbSt.executeQuery("show databases;")
    while (queryDBRs.next()) {
        val DATABASE = queryDBRs.getString("database")

        val queryTblSt = connection.createStatement()
        val queryTblRs = queryTblSt.executeQuery("show tables from `$DATABASE`;")
        while (queryTblRs.next()) {
            val TBL_NAME = queryTblRs.getString(1)
            if (TBL_NAME.equals(one)) {
                ret.add("`${DATABASE}`.`${TBL_NAME}`")
            }
        }
    }
    return ret
}
