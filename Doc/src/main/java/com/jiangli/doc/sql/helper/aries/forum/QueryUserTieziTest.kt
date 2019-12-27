package com.jiangli.doc.sql.helper.aries.forum

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 企业
 * 企业班级
 * 企业班级成员
 *
 * 查用户发帖
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */
fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val ret = mutableListOf<MutableMap<String, Any>>()

    //    查询企业
    val companyDto = Ariesutil.getCompany(jdbc,"英幼儿园")
//    val companyDto = Ariesutil.getCompany(jdbc,"",47) //知室
    println(companyDto)

    val COMPANY_ID = companyDto["ID"]
    val COMPANY_NAME = companyDto["NAME"]
    val outputpath= PathUtil.desktop("${COMPANY_NAME}_${DateUtil.getCurrentDate_YMDHmsSS_f()}.xlsx")

    Ariesutil.processCompanyClassStudents(jdbc,companyDto){
        companyclass, student ->
        val CLASS_NAME = companyclass["CLASS_NAME"]!!
        val CLASS_ID = companyclass["CLASS_ID"]
        val USER_NAME = student["NAME"]?:""
        val USER_ID = student["ID"]?:""


        //            查发帖量
        val queryL1 = jdbc.queryForObject("""
select count(*) as c from db_aries_forum.L1_TOPIC c
LEFT JOIN  db_aries_forum.RELA_MODULE_TOPIC rt on c.ID=rt.TOPIC_ID
where  c.IS_DELETED=0
AND rt.IS_DELETED=0
AND c.CREATE_PERSON=${USER_ID}
AND rt.MODULE_ID in (
    select MODULE_ID from db_aries_forum.RELA_COMPANY_MODULE where
    COMPANY_ID=${COMPANY_ID} AND IS_DELETED=0
)
;
""".trimIndent(), ColumnMapRowMapper())["c"].toString().toInt()

        //            查发帖量
        val queryL2 = jdbc.queryForObject("""
select count(*) as c  from db_aries_forum.L2_DISCUSS l
where  l.IS_DELETED=0
AND l.CREATE_PERSON=${USER_ID}
AND l.TOPIC_ID in (
    select c.ID from db_aries_forum.L1_TOPIC c
LEFT JOIN  db_aries_forum.RELA_MODULE_TOPIC rt on c.ID=rt.TOPIC_ID
    where  c.IS_DELETED=0
      AND rt.IS_DELETED=0
      AND rt.MODULE_ID in (
        select MODULE_ID from db_aries_forum.RELA_COMPANY_MODULE where
                COMPANY_ID=${COMPANY_ID} AND IS_DELETED=0
    )
)
;
""".trimIndent(), ColumnMapRowMapper())["c"].toString().toInt()

        //            查发帖量
        val queryL3 = jdbc.queryForObject("""
select count(*) as c  from db_aries_forum.L3_COMMENT l
where  l.IS_DELETED=0
AND l.CREATE_PERSON=${USER_ID}
AND l.TOPIC_ID in (
    select c.ID from db_aries_forum.L1_TOPIC c
LEFT JOIN  db_aries_forum.RELA_MODULE_TOPIC rt on c.ID=rt.TOPIC_ID
    where  c.IS_DELETED=0
      AND rt.IS_DELETED=0
      AND rt.MODULE_ID in (
        select MODULE_ID from db_aries_forum.RELA_COMPANY_MODULE where
                COMPANY_ID=${COMPANY_ID} AND IS_DELETED=0
    )
)
;
""".trimIndent(), ColumnMapRowMapper())["c"].toString().toInt()


        val mp = mutableMapOf<String, Any>()
        mp.put("所在班级", CLASS_NAME)
        mp.put("姓名", USER_NAME)
        mp.put("发帖量", queryL1)
        mp.put("回帖量", queryL2+queryL3)
        ret.add(mp)
    }


    writeMapToExcel(outputpath,ret)
}