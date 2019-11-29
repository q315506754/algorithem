package com.jiangli.db.tool

import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 15:07
 */
object DbBaseTool{
    /**
     * 迭代库里的所有表
     */
    fun iterateRepoTable(DB_URL:String,fc:(metaData: DatabaseMetaData, databaseName: String, tableName: String, fullName: String)->Unit) {
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

        val queryDbSt = connection.createStatement()
        val queryDBRs = queryDbSt.executeQuery("show databases;")
        while (queryDBRs.next()) {
            val DATABASE = queryDBRs.getString("database")
            //        println("DATABASE:"+DATABASE)

            val queryTblSt = connection.createStatement()
            try {
                val queryTblRs = queryTblSt.executeQuery("show tables from `$DATABASE`;")
                while (queryTblRs.next()) {
                    val TBL_NAME = queryTblRs.getString(1)
                    //            println("   TBL_NAME:"+TBL_NAME)

                    val fullName = "${DATABASE}.${TBL_NAME}"
                    fc(metaData,DATABASE,TBL_NAME,fullName)
                }
            } catch (e: Exception) {
                println("$DATABASE error...")
                e.printStackTrace()
            }
        }
    }


    /**
     * 迭代库里的所有表的所有字段
     */
    fun iterateRepoTableField(DB_URL:String,fc:(metaData: DatabaseMetaData, databaseName: String?, tableName: String?, fullName: String, columnName: String)->Unit) {

        iterateRepoTable(DB_URL){
            metaData, databaseName, tableName, fullName ->

            val colRs = metaData.getColumns(databaseName, "%", tableName, "%")
            while (colRs.next()) {
                val columnName = colRs.getString("COLUMN_NAME")
                val columnType = colRs.getString("TYPE_NAME")
                val datasize = colRs.getInt("COLUMN_SIZE")
                val digits = colRs.getInt("DECIMAL_DIGITS")
                val nullable = colRs.getInt("NULLABLE") //1:可
                val message = colRs.getString("REMARKS")
                val columnDef  = colRs.getString("COLUMN_DEF") // 该列的默认值 当值在单引号内时应被解释为一个字符串

                fc(metaData,databaseName,tableName,fullName,columnName)
            }
        }
    }

}