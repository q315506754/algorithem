package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.replaceKey
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import com.mongodb.BasicDBObject
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

/**
 *
 *
 * @author Jiangli
 * @date 2019/3/15 17:36
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG

    val jdbc = Ariesutil.getJDBC(env)
    val mongo = Ariesutil.getMongo(env, Ariesutil.MongoDbCol.ARIES_LOGIN)

    val inputFile = """C:\Users\Jiangli\Desktop\华大、国泰、海盟学员学习信息统计.xlsx"""
    val outputDir = """C:\Users\Jiangli\Desktop\统计结果2019"""

    val lastList = mutableListOf<String>()
    val duplicateByMobileList = mutableListOf<String>()

    FileUtil.deleteUnderDir(outputDir)
//    return

    (0..2).forEach {
        ExcelUtil.processRow(inputFile,it,2){
            file: File, workbook: XSSFWorkbook, sheet: XSSFSheet, lastRowIdx: Int, lastColIdx: Int, rowIdx: Int, row: XSSFRow? ->

            val USER_ID = ExcelUtil.getCellValueByTitle(sheet,row, "USER_ID")
            val name = ExcelUtil.getCellValueByTitle(sheet,row, "姓名")

            println("${sheet.sheetName} $USER_ID $name")

            val login_dir = File(outputDir,"${sheet.sheetName}_登录详情")
            if(!login_dir.exists()){
                login_dir.mkdirs()
            }


            val list = mutableListOf<MutableMap<String,Any>>()
            val query = BasicDBObject()
            query.put("user_id",USER_ID!!.toString().toInt())
//            query.put("appType","ARIES_PC")
            val sort = BasicDBObject()
            sort.put("loginTime",-1)

            val dbCursor = mongo.find(query).sort(sort)
            var count = 0
            while (dbCursor.hasNext()) {
                val next = dbCursor.next()
                //        println(next)

                val one = mutableMapOf<String,Any>()
                one.putAll(next.toMap() as Map<out String, Any>)
                one.remove("_id")
                one.remove("_class")

                val ts = one.get("loginTime") as java.util.Date
                //        println(ts?.javaClass)
                one.put("loginTime", DateUtil.getDate_yyyyMMddHHmmss(ts.time))


                replaceKey(one,"loginTime","登录时间")
                replaceKey(one,"appPlatform","平台")


                list.add(one)
                count++
            }

            val ouputName = if(count>0) "$name-($count).xlsx" else "$name-无登录记录.xlsx"
            val ouputFile = PathUtil.buildPath(login_dir.absolutePath,false,ouputName)
            writeMapToExcel(ouputFile,list)
        }
    }


}