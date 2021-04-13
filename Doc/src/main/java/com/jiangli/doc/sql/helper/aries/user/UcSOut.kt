package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * #查不在会员企业的人员
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val outputpath = PathUtil.desktop("""不在组织架构.xls""")


    val questions = jdbc.query("""
SELECT u.NAME as '姓名(必填)',u.AREA_CODE as '国际区号',u.MOBILE as '手机号(必填)',NULL as '性别',NULL as '职位',NULL as '部门',NULL as '邮箱',NULL as '备注'
FROM
    db_aries_user.TBL_USER_COMPANY uc
LEFT JOIN db_aries_user.TBL_USER_SAAS_COMPANY usc on usc.USER_COMPANY_ID = uc.ID AND usc.IS_DELETED=0
LEFT JOIN db_aries_user.TBL_USER u on uc.USER_ID = u.ID
WHERE
    uc.IS_GUEST=0
AND uc.IS_DELETED=0
AND usc.ID IS NULL
AND uc.COMPANY_ID=34;
    """.trimIndent(), ColumnMapRowMapper())


//    questions.forEach {
//        val ID = it["ID"]
//        val mp = it
//
//        val courses = jdbc.query("""
//SELECT  DISTINCT (cse.COURSE_NAME) as 'NAME' FROM `db_aries_run`.`TBL_CLASS` cls
//LEFT JOIN `db_aries_course`.`TBL_COURSE` cse on cls.COURSE_ID = cse.COURSE_ID
//WHERE
//cls.IS_DELETE=0
//AND cls.COMPANY_ID=$ID;
//    """.trimIndent(), ColumnMapRowMapper())
//
//        var cCount = 1
//
//        courses.forEach {
//            mp.put("课程${cCount++}",it["NAME"])
//        }
//    }
//    println(questions)

    writeMapToExcel(outputpath, questions)
}