package com.jiangli.db.tool

import queryFieldList


/**
 *
 * db插入模板生成
 *
 */
fun main(args: Array<String>) {
    //aries
    val DB_URL = "jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava"

    val tables = """

TBL_MENU

    """.trimIndent()

    val excludeFields = setOf("ID","IS_DELETED","IS_DELETE","CREATE_TIME","UPDATE_TIME","CREATE_PERSON","DELETE_PERSON")

    val list = mutableSetOf<String>()
    tables.split("\n").forEach {
        var one = it.toUpperCase().trim()
        one = one.replace("`", "")
        if (one.isBlank()) {
            return@forEach
        }


        DbBaseTool.iterateRepoTable(DB_URL){ metaData, databaseName, tableName, fullName ->
            if (tableName.toUpperCase()==one) {
                println("$fullName")

                val queryFieldList = queryFieldList(DB_URL, databaseName, tableName)
                val filter = queryFieldList.filter { !it.isPk }.filter { !excludeFields.contains(it.columnName) }
                println(filter)

                var col_body  = ""
                filter.forEachIndexed { index, javaField ->
                    col_body+= javaField.columnName
                    if (index< filter.lastIndex) {
                        col_body+=","
                    }
                }

                var val_body  = ""
                filter.forEachIndexed { index, javaField ->
                    if (javaField.defaultValue!=null) {
                        var v = javaField.defaultValue
                        if (v ==null || v.isBlank()) {
                            v =  "'xxx'"
                        }
                        val_body+= javaField.defaultValue
                    } else {
                        val_body+= when (javaField.fieldCls) {
                             "String" -> "'xxx'"
                             "Date" -> "'2019-11-16 12:38:23'"
                             "Long" -> "1000062"
                             "Integer" -> "1"
                            else -> "?"
                        }
                    }

                    if (index< filter.lastIndex) {
                        val_body+=","
                    }
                     }

                //表主键
                println(queryFieldList)

                println("""
UPDATE `$databaseName`.`$tableName` (
    $col_body
) values (
    $val_body
);
                """.trimIndent())
            }
        }
    }


}
