package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 加入班级
 *
 * @author Jiangli
 * @date 2019/5/9 17:24
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val USERID = 100003653
    val list = jdbc.query("""
        SELECT u.NAME,uc.COURSE_ID,c.COURSE_NAME,uc.COMPANY_ID,com.NAME as COMPANY_NAME,uc.CLASS_ID,cls.CLASS_NAME FROM db_aries_study.tbl_user_course uc
LEFT JOIN db_aries_course.tbl_course c on uc.COURSE_ID = c.COURSE_ID
LEFT JOIN db_aries_company.tbl_company com on uc.COMPANY_ID = com.ID
LEFT JOIN db_aries_run.tbl_class cls on uc.CLASS_ID = cls.CLASS_ID
LEFT JOIN db_aries_user.tbl_user u on uc.USER_ID = u.ID
WHERE  uc.USER_ID = '$USERID' AND uc.IS_DELETE = 0
;
    """.trimIndent(), ColumnMapRowMapper())

    list.forEach {
        val sql = """INSERT INTO `db_aries_run`.`COACH_CLASS`( `USER_ID`, `COURSE_ID`, `CLASS_ID`) VALUES ($USERID, ${it["COURSE_ID"]}, ${it["CLASS_ID"]});"""
        println(sql)
    }
}