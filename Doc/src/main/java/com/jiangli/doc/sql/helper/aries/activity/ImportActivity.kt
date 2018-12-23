package com.jiangli.doc.sql.helper.aries.activity

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.checkExists
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env

/**
 * 导入活动
 *
 * @author Jiangli
 * @date 2018/11/1 11:19
 */
fun main(args: Array<String>) {
//        val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val ouputFile = """C:\Users\Jiangli\Desktop\导入数据.xlsx"""

    val ACTIVITY_TYPE = 21

    val lastList = mutableListOf<String>()
    val duplicateByMobileList = mutableListOf<String>()

    ExcelUtil.processRow(ouputFile,0,1){
        file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx>=1) {
            val name = ExcelUtil.getCellValueByTitle(sheet,row, "姓名")
            val mobile = ExcelUtil.getCellValueByTitle(sheet,row, "手机")
            val email = ExcelUtil.getCellValueByTitle(sheet,row, "邮箱")
            val company = ExcelUtil.getCellValueByTitle(sheet,row, "公司")
            val duty = ExcelUtil.getCellValueByTitle(sheet,row, "职务")
            val source = ExcelUtil.getCellValueByTitle(sheet,row, "客户来源")

//            val email = ExcelUtil.getCellValueByTitle(sheet,row, "专员")
//            println(userId+" "+email)

            val sql="""
INSERT INTO `db_aries_run`.`TBL_REGIS_ACTIV`( `ACTIVITY_TYPE`, `USER_NAME`, `USER_MOBILE`, `COMPANY_NAME`, `USER_TITLE`,  `INVOICE_ADDRESS`, `DITCH_ID`,`CREATE_TIME`) VALUES ( $ACTIVITY_TYPE, '$name', '$mobile', '$company', '$duty',  '$email', $source,'2018-11-20 11:20:00');
            """.trimIndent()
//            println(sql)

//            手机去重
            if (mobile!=null && mobile.isNotBlank()) {
                val exists = checkExists(jdbc,"""
    SELECT REGISTRATION_ID FROM `db_aries_run`.`TBL_REGIS_ACTIV` WHERE ACTIVITY_TYPE=$ACTIVITY_TYPE AND USER_MOBILE='$mobile';
                """.trimIndent())

                if(exists) {
                    duplicateByMobileList.add(sql)

                    return@processRow
                }
            }

            lastList.add(sql)
        }

        if (rowIdx == lastRowIdx) {
//            workbook.write(FileOutputStream(ouputFile))
        }

    }

//    println(lastList)
    lastList.forEach {
        println(it)
    }

    duplicateByMobileList.forEach {
        System.err.println(it)
    }

//    println(duplicateByMobileList)
}



private fun cloneMap(dataOneProto: MutableMap<String, Any>): MutableMap<String, Any> {
    val dataOne = mutableMapOf<String, Any>()
    dataOne.putAll(dataOneProto)
    return dataOne
}