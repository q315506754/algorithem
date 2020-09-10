package com.jiangli.doc.sql.helper.aries.common

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * 教学企业数量 分布数据
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val outputpath = PathUtil.desktop("""code_map\out.xlsx""")


    val questions = jdbc.query("""
SELECT c.ID,c.NAME as '企业名' ,c.IS_SAAS as '是否会员',c.TYPE as '企业类型',cci.NAME as '规模',ct0.NAME as '省份',ct1.NAME as '城市'
,(SELECT  COUNT(*)  FROM `db_aries_user`.`TBL_USER_COMPANY` uc WHERE  uc.IS_DELETED=0 AND uc.COMPANY_ID=c.ID) as '企业导入人数'
FROM `db_aries_company`.TBL_COMPANY c
LEFT JOIN `db_aries_operation`.TBL_COMMON_CATEGORY_ITEMS cci on c.CATE_TYPE_3_ID = cci.ID
LEFT JOIN `db_aries_base`.TBL_CITY ct0 on c.CITY_ID_0 = ct0.ID
LEFT JOIN `db_aries_base`.TBL_CITY ct1 on c.CITY_ID_1 = ct1.ID
where
      c.IS_TEACH_CLASS = 1
AND c.IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper())


    questions.forEach {
        val ID = it["ID"]
        val mp = it

        val courses = jdbc.query("""
SELECT  DISTINCT (cse.COURSE_NAME) as 'NAME' FROM `db_aries_run`.`TBL_CLASS` cls
LEFT JOIN `db_aries_course`.`TBL_COURSE` cse on cls.COURSE_ID = cse.COURSE_ID
WHERE
cls.IS_DELETE=0
AND cls.COMPANY_ID=$ID;
    """.trimIndent(), ColumnMapRowMapper())

        var cCount = 1

        courses.forEach {
            mp.put("课程${cCount++}",it["NAME"])
        }
    }

    println(questions)

    writeMapToExcel(outputpath, questions)
}