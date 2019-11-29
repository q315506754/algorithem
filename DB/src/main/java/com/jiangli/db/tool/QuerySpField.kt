package com.jiangli.db.tool

/**
 * 找库找表
 * 找字段
 *
 * @author Jiangli
 * @date 2019/5/17 11:19
 */
fun main(args: Array<String>) {
    //aries
    val DB_URL = "jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava"

    val sqls = mutableListOf<String>()
    val QUERY_FIELD = "COMPANY_ID"
    val oldValue = 111
    val newValue = 222

    DbBaseTool.iterateRepoTableField(DB_URL){
        metaData, databaseName, tableName, fullName, columnName ->

        if (columnName.contains(QUERY_FIELD)) {
            println("$fullName 拥有字段-${columnName}")

            sqls.add("update $fullName set ${columnName}=${newValue} where ${columnName}=${oldValue};")
            //            break
        }
    }

    sqls.forEach(::println)

}