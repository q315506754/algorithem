package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import net.sf.json.JSONObject
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 查用户
 * @author Jiangli
 * @date 2018/8/3 16:42
 */

fun main(args: Array<String>) {
    val DOMAIN = "api.g2s.cn"

    //    val env = Env.YANFA
//        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    val userId = Ariesutil.convertUUID("yGAJXoxK") //ge
    val userId = Ariesutil.convertUUID("yxvY8rXK") //cao peng
//    val userId = Ariesutil.convertUUID("K607rEay") //cao peng
//    val userId = 100003545 //w 曹文君
//    val userId = 100066833 //w
//    val userId = 100003653 //w ge xin
//    val userId = 100003731//w chen chun h
//    val userId = 100007071//w xiao ping
//    val userId = 100001239//w wo
//    val userId = 100002318 // yu hui
//    val userId = Ariesutil.convertUUID("nwXAZZZy") // yu hui

//    val userId = Ariesutil.convertUUID("dgr8qa7y")
    val uid = Ariesutil.convertUUID(userId)
    println(uid)

    println("userId:$userId")
    println("uid:$uid")

    var collect = mutableListOf<String>()

    println("用户-----")
    val tbluser = jdbc.query("""
SELECT * FROm db_aries_user.TBL_USER where ID=$userId AND IS_DELETED=0;
            """.trimIndent(), ColumnMapRowMapper())
    pp(tbluser)
//    pp(tbluser[0][""])

    println("用户角色-----")
    val teacherlist = jdbc.query("""
SELECT * FROm db_aries_user.TBL_USER_ROLE where USER_ID=$userId AND IS_DELETED=0;
            """.trimIndent(), ColumnMapRowMapper())
    pp(teacherlist)

    println("用户关联课程主讲人-----")
    val coureseteacherlist = jdbc.query("""
SELECT
       c.COURSE_ID,c.COURSE_NAME,cs.*
FROm db_aries_course.TBL_COURSE_SPEAKER  cs
LEFT JOIN db_aries_course.TBL_COURSE c ON c.COURSE_ID = cs.COURSE_ID
where
        RELATE_USER_ID=$userId
  AND  c.IS_DELETED=0
  AND cs.IS_DELETED=0;
            """.trimIndent(), ColumnMapRowMapper())
    pp(coureseteacherlist)

    println("用户辅导班级-----")
    val fudaoclasslist = jdbc.query("""
SELECT
       c.CLASS_ID,c.CLASS_NAME,tcc.RELATION_TYPE,tcc.RELATION_ID,cc.*
FROm db_aries_run.COACH_CLASS  cc
LEFT JOIN db_aries_run.TBL_CLASS c ON c.CLASS_ID = cc.CLASS_ID
LEFT JOIN db_aries_run.TBL_CLASS_CONTENT tcc ON tcc.CLASS_ID = c.CLASS_ID AND tcc.IS_DELETED = 0
where
        cc.USER_ID=$userId
  AND  c.IS_DELETE=0
  AND cc.IS_DELETED=0;
            """.trimIndent(), ColumnMapRowMapper())
    pp(fudaoclasslist)

    println("用户超管企业-----")
    val superlist = jdbc.query("""
SELECT
    u.NAME,u.COMPANY_NAME as USER_COMPANYNAME,c.ID,c.NAME,c.IS_SAAS,c.STAFF_SHOW_COMPANY_NAME,c.SAAS_APP_VIEWER
FROM
    `db_aries_user`.`TBL_USER` u
LEFT JOIN  `db_aries_user`.TBL_USER_SAAS_COMPANY_ADMIN uc ON u.ID = uc.USER_ID
LEFT JOIN  `db_aries_company`.TBL_COMPANY c ON c.ID = uc.COMPANY_ID
WHERE
u.ID = $userId
AND u.IS_DELETED=0
AND uc.IS_DELETED=0
AND c.IS_DELETED=0
ORDER BY c.IS_SAAS DESC,FIELD(uc.COMPANY_ID,47) DESC,uc.ID DESC;
            """.trimIndent(), ColumnMapRowMapper())

    pp(superlist)

//    #查用户企业
    println("用户企业-----")
    val list = jdbc.query("""
SELECT uc.USER_ID,uc.COMPANY_ID,uc.IS_GUEST,c.IS_SAAS,c.NAME,c.STAFF_SHOW_COMPANY_NAME,c.IS_PLACE
FROM db_aries_user.TBL_USER_COMPANY  uc
         LEFT JOIN  db_aries_company.TBL_COMPANY c on uc.COMPANY_ID = c.ID
         LEFT JOIN  db_aries_user.TBL_USER_SAAS_COMPANY usc on usc.USER_COMPANY_ID = uc.ID
WHERE
        uc.IS_DELETED = 0
  AND c.IS_DELETED = 0
  AND c.IS_SAAS >0
  AND usc.IS_DELETED = 0
  AND uc.USER_ID = $userId
ORDER BY c.IS_SAAS DESC,FIELD(COMPANY_ID,47) DESC,uc.ID DESC;
            """.trimIndent(), ColumnMapRowMapper())

    pp(list)

    val param = JSONObject()
    param.put("uid","$uid")


    var user_rs = HttpPostUtil.postUrl("http://$DOMAIN/getUserInfo", param)
    println("用户信息:${user_rs}")

//    var classCoachrs = HttpPostUtil.postUrl("http://$DOMAIN/coach/coachCourseList", param)
//    println("知识教练所辅导课程列表:${classCoachrs}")

    var webclassCoachrs = HttpPostUtil.postUrl("http://$DOMAIN/userrole/queryTeacherCourseList", param)
    println("web知识教练所辅导课程列表:${webclassCoachrs}")

    var classCoachrs = HttpPostUtil.postUrl("http://$DOMAIN/userrole/queryTeacherCourseListForApp", param)
    println("知识教练所辅导课程列表:${classCoachrs}")

    var classLearnrs = HttpPostUtil.postUrl("http://$DOMAIN//studyData/courseListForApp", param)
    println("学习课程书包列表:${classLearnrs}")


}

private fun pp(superlist: MutableList<MutableMap<String, Any>>) {
    superlist.forEach {
        println(it)
    }
}

