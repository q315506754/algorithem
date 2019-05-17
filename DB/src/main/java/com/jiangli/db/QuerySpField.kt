package com.jiangli.db

import java.sql.DriverManager
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2019/5/17 11:19
 */
fun main(args: Array<String>) {
    //aries
    val DB_URL = "jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava"
    val QUERY_FIELD = "COMPANY_ID"
    val oldValue = 111
    val newValue = 222

    val sqls = mutableListOf<String>()
    //fixed
    Class.forName("com.mysql.jdbc.Driver")

    val props = Properties()
//    props.setProperty("user", username);
//    props.setProperty("password", password);
    props.setProperty("remarks", "true"); //设置可以获取remarks信息
    props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息

    val connection = DriverManager.getConnection(DB_URL,props)

//    println(connection.schema)
    val metaData = connection.metaData
    val schemas = metaData.schemas

    while (schemas.next()) {
        println(schemas.getObject(1))
    }


    val queryDbSt = connection.createStatement()
    val queryDBRs = queryDbSt.executeQuery("show databases;")
    while (queryDBRs.next()) {
        val DATABASE = queryDBRs.getString("database")
//        println("DATABASE:"+DATABASE)

        val queryTblSt = connection.createStatement()
        val queryTblRs = queryTblSt.executeQuery("show tables from $DATABASE;")
        while (queryTblRs.next()) {
            val TBL_NAME = queryTblRs.getString(1)
//            println("   TBL_NAME:"+TBL_NAME)

            val colRs = metaData.getColumns(DATABASE, "%", TBL_NAME, "%")
            while (colRs.next()) {
                val columnName = colRs.getString("COLUMN_NAME")
                val columnType = colRs.getString("TYPE_NAME")
                val datasize = colRs.getInt("COLUMN_SIZE")
                val digits = colRs.getInt("DECIMAL_DIGITS")
                val nullable = colRs.getInt("NULLABLE") //1:可
                val message = colRs.getString("REMARKS")
                val columnDef  = colRs.getString("COLUMN_DEF") // 该列的默认值 当值在单引号内时应被解释为一个字符串
                //        println("$columnName $columnType $datasize $digits $nullable $columnDef")
//                println(columnName)

                if (columnName.contains(QUERY_FIELD)) {
                    println("${DATABASE}.${TBL_NAME} 拥有字段-${columnName}")

                    sqls.add("update ${DATABASE}.${TBL_NAME} set ${columnName}=${newValue} where ${columnName}=${oldValue};")
                    break
                }
                //        println(colRs.getString("IS_AUTOINCREMENT"))
            }
        }
    }


    sqls.forEach(::println)

}