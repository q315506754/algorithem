package com.jiangli.doc.txt.excel

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.Ariesutil
import com.jiangli.doc.sql.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/16 10:08
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    var i = 0

    ExcelUtil.processRow("C:\\Users\\Jiangli\\Desktop\\095f9b0f4c5760da.xlsx",0,1){
        file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx>=2) {
            val name = ExcelUtil.getCellValue(row?.getCell(1))
            val email = ExcelUtil.getCellValue(row?.getCell(2))

            val sql = """SELECT * FROM db_aries_user.TBL_USER WHERE EMAIL='$email' and  IS_DELETED = 0"""
            val userInfo = jdbc.queryForObject(sql, ColumnMapRowMapper()) //保证唯一

            val pwd = userInfo["PASSWORD"]
            val MOBILE = userInfo["MOBILE"]
            val ID = userInfo["ID"]
            val NAME = userInfo["NAME"]
            println("$name ,$email,$pwd,$MOBILE,$ID,$NAME")
//            println(userInfo)
//            println("UPDATE db_aries_user.TBL_USER set PASSWORD ='1aa14247ac5623ca07a25a099cd66527d7c39705e29a225e5e0f2a6a6216b53e' WHERE ID=$ID ;")
            i++
        }

    }

    println("total :$i")
}