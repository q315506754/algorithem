package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/11/16 14:09
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val inputpath = """C:\工作\项目\aries改版191104\线上企业数据_1.xlsx"""

    var db_course = queryNamesOfType(jdbc,4)
    var idx_course = queryNamesIdMap(jdbc,4)

    var db_xingzhi = queryNamesOfType(jdbc,2)
    var idx_xingzhi = queryNamesIdMap(jdbc,2)

    var db_hangye = queryNamesOfType(jdbc,1)
    var idx_hangye = queryNamesIdMap(jdbc,1)

    var db_guimo = queryNamesOfType(jdbc,3)
    var idx_guimo = queryNamesIdMap(jdbc,3)

    var sort = 1
    ExcelUtil.processRow(inputpath, 0, 2) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        val companyId = ExcelUtil.getCellValue(row, 0)!!
        val title = ExcelUtil.getCellValue(row, 1)!!
        val desc = ExcelUtil.getCellValue(row, 2)!!

        val xingzhi = ExcelUtil.getCellValue(row, 4)!!
        val hangye = ExcelUtil.getCellValue(row, 5)!!
        val guimo = ExcelUtil.getCellValue(row, 6)!!

        val xingzhiId =getIdOfTitle(xingzhi,::analyseConvertXingzhi,db_xingzhi,idx_xingzhi)
        val hangyeId =getIdOfTitle(hangye,::analyseConvertHangye,db_hangye,idx_hangye)
        val guimoId =getIdOfTitle(guimo,::analyseConvertGuimo,db_guimo,idx_guimo)

//        println("$companyId $xingzhiId $hangyeId $guimoId")

//        修改企业基础信息
//        println("UPDATE `db_aries_company`.`TBL_COMPANY` SET CATE_TYPE_1_ID=$hangyeId,CATE_TYPE_2_ID=$xingzhiId,CATE_TYPE_3_ID=$guimoId WHERE ID=$companyId;")

//         增加运营企业信息
        val list = jdbc.query("""
SELECT * FROM  `db_aries_run`.`TBL_COMPANY_SHARE_STU` WHERE COMPANY_ID=$companyId AND IS_DELETED = 0;
            """.trimIndent(), ColumnMapRowMapper())

        if (list.isNotEmpty()) {
            val one = list[0]

            //            更新
            println("UPDATE `db_aries_run`.`TBL_COMPANY_SHARE_STU` SET SORT = $sort where COMPANY_SHARE_ID=${one["COMPANY_SHARE_ID"]};")
        } else {
            val company = jdbc.queryForObject("""
SELECT * FROM  `db_aries_company`.`TBL_COMPANY` WHERE ID=$companyId;
            """.trimIndent(), ColumnMapRowMapper())
            val logo = company["LOGO"]

            println("INSERT INTO `db_aries_run`.`TBL_COMPANY_SHARE_STU`( `COMPANY_ID`, `COMPANY_NAME`, `COMPANY_LOGO`, `COMPANY_DESC`, `VIDEO_URL`, `VIDEO_NAME`, `SHARE_URL`, `COMPANY_CONTACT`, `LEADER_POSITION`, `LEADER_PIC`, `IS_SHOW`, `SORT`) VALUES ( $companyId, '$title', '$logo', '$desc', '', '', NULL, '', NULL, '', 1,  $sort);")
        }


        sort++
    }

}

fun getIdOfTitle(xingzhi: String, func: (List<String>,str:String)->String?, db_xingzhi: List<String>, idx_xingzhi: MutableMap<String, String>): String? {
    var db_str = func(db_xingzhi, xingzhi)
    if (db_str == null) {
        if (db_xingzhi.contains(xingzhi)) {
            db_str = xingzhi
        }
    }

    return idx_xingzhi[db_str]
}
