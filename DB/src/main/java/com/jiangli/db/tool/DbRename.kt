package com.jiangli.db.tool

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 15:22
 */
fun main(args: Array<String>) {
    //aries
    val DB_URL = "jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava"

    DbBaseTool.iterateRepoTable(DB_URL){ metaData, databaseName, tableName, fullName ->
        if (databaseName.startsWith("jeecg") && tableName.startsWith("SYS_")) {
            println("rename table `${databaseName}`.`${tableName}` to `${databaseName}`.`${tableName.toLowerCase()}`;")
        }
    }


}