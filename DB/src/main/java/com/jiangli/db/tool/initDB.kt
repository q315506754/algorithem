package com.jiangli.db.tool

/**
 *
 *
 * @author Jiangli
 * @date 2020/5/13 19:14
 */
fun main(args: Array<String>) {
    val DEST_DB_URL = "jdbc:mysql://120.92.138.210:3306?user=root&password=ablejava"

    DbBaseTool.iterateRepo(DEST_DB_URL,{ metaData, databaseName ->
        if (databaseName.startsWith("db_")) {
//            println(databaseName)
            println("""
CREATE DATABASE `$databaseName` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin';
            """.trimIndent())
        }
    })

//    db_aries_discuss
//
//    CREATE DATABASE `db_aries_discuss` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin';
}