package com.jiangli.doc.sql.helper.aries

import com.jiangli.doc.sql.DataQueryer
import com.jiangli.doc.sql.NamedSimpleCachedTableQueryer

//aries
class UserIdQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})详情",arrayOf("userId","CREATOR_ID","CREATE_USER","CREATE_PERSON"),"db_aries_user.TBL_USER","ID,NAME,MOBILE,EMAIL","ID=${'$'}{params} AND IS_DELETED=0 ")

class UsersRolesQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})拥有角色",arrayOf("userId"),"db_aries_user.TBL_USER_ROLE","ROLE,TITLE,INTRO","USER_ID=${'$'}{params} AND IS_DELETED=0 ")
class UsersCompanyQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})所属企业",arrayOf("userId"),"db_aries_user.TBL_USER_COMPANY","COMPANY_ID as companyId","USER_ID=${'$'}{params} AND IS_DELETED=0 ")
class Users2BCourseQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})加入学习的2b课程",arrayOf("userId"),"db_aries_study.TBL_USER_COURSE","COURSE_ID as courseId2B,COMPANY_ID,CLASS_ID,RECRUIT_ID,CREATOR_ID,CREATE_TIME,UPDATE_TIME","USER_ID=${'$'}{params} AND IS_DELETE=0 ")
class Users2CCourseQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})购买的2c课程",arrayOf("userId"),"db_aries_2c_course.TM_USER_COURSE","COURSE_ID as courseId2C,CREATE_TIME,UPDATE_TIME,USER_TYPE,BUY_TYPE","USER_ID=${'$'}{params} AND IS_DELETED=0 ")
class Users2CCourseStudyQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})加入学习的2c课程",arrayOf("userId"),"db_aries_2c_course.TM_USER_STUDY_COURSE","COURSE_ID as courseId2C,STUDY_TYPE,USER_TYPE,CREATE_TIME,UPDATE_TIME,USER_TYPE,STUDY_TYPE","USER_ID=${'$'}{params} AND IS_DELETED=0 ")

class UsersCreatedGroupQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})创建的群组",arrayOf("userId"),"db_aries_class_tools.GROUP","GROUP_ID as groupId,GROUP_NAME,RULES,CREATE_TIME","GROUP_USER_ID=${'$'}{params} AND IS_DELETE=0 ")
class UsersJoinedGroupQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})加入的群组",arrayOf("userId"),"db_aries_class_tools.USER_GROUP","GROUP_ID ,case ROLE when 1 then '成员' when 2 then '群主'when 3 then '管理员' ELSE ROLE END as ROLE ,CREATE_TIME","USER_ID=${'$'}{params} AND IS_DELETE=0 ")

class UsersOrderQueryer : NamedSimpleCachedTableQueryer("用户(${'$'}{params})的订单",arrayOf("userId"),"db_aries_pay_core.TBL_ORDER o ","case o.STATUS\n" +
        "  when 0 then '未支付'\n" +
        "  when 1 then '支付成功'\n" +
        "  when 2 then '支付失败'\n" +
        "  ELSE '未知订单状态'\n" +
        "  END as '订单状态',o.CREATE_TIME,o.ORDER_SUBJECT,o.AMOUNT,o.ITEM_ID as GOODS_ID,o.PLATFORM","USER_ID=${'$'}{params} AND IS_DELETE=0 and STATUS=1")


class CompanyIdQueryer : NamedSimpleCachedTableQueryer("企业(${'$'}{params})详情",arrayOf("companyId","COMPANY_ID"),"db_aries_company.TBL_COMPANY","ID,NAME","ID=${'$'}{params} AND IS_DELETED=0 ")
class CourseId2BQueryer : NamedSimpleCachedTableQueryer("2b课程(${'$'}{params})详情",arrayOf("courseId2B"),"db_aries_course.TBL_COURSE"," COURSE_NAME,CREATE_TIME,CREATE_PERSON,ORIGINAL_PRICE,CURRENT_PRICE","COURSE_ID=${'$'}{params} AND IS_DELETED=0 ")
class CourseId2CQueryer : NamedSimpleCachedTableQueryer("2c课程(${'$'}{params})详情",arrayOf("courseId2C"),"db_aries_2c_course.TM_COURSE"," COURSE_NAME,COURSE_CREATED_AT,CREATE_USER,PRICE_YUAN,PRICE_ZHIGUO","COURSE_ID=${'$'}{params} AND IS_DELETED=0 ")
class CourseName2CQueryer : NamedSimpleCachedTableQueryer("2c课程(${'$'}{params})详情",arrayOf("courseName2C"),"db_aries_2c_course.TM_COURSE"," COURSE_ID,COURSE_NAME,COURSE_CREATED_AT,CREATE_USER,PRICE_YUAN,PRICE_ZHIGUO","COURSE_NAME=${'$'}{params} AND IS_DELETED=0 ")
class Recruit2BQueryer : NamedSimpleCachedTableQueryer("招生(${'$'}{params})详情",arrayOf("RECRUIT_ID"),"db_aries_run.TBL_RECRUIT"," RECRUIT_ID as recruitId,START_TIME,START_TIME,END_TIME,VALID_TIME,CREATE_PERSON","RECRUIT_ID=${'$'}{params} AND IS_DELETED=0 ")
//class Class2BQueryer : NamedSimpleCachedTableQueryer("班级(${'$'}{params})详情",arrayOf("CLASS_ID"),"db_aries_run.TBL_CLASS"," CLASS_ID as classId,CLASS_NAME,COMPANY_ID,RECRUIT_ID,COURSE_ID as courseId2B,CREATE_TIME,CREATOR_ID","CLASS_ID=${'$'}{params} AND IS_DELETE=0 ")
class Class2BQueryer : NamedSimpleCachedTableQueryer("班级(${'$'}{params})详情",arrayOf("CLASS_ID"),"db_aries_run.TBL_CLASS"," CLASS_ID as classId,CLASS_NAME,CREATE_TIME,CREATOR_ID","CLASS_ID=${'$'}{params} AND IS_DELETE=0 ")
class GroupQueryer : NamedSimpleCachedTableQueryer("群组(${'$'}{params})详情",arrayOf("GROUP_ID"),"db_aries_class_tools.GROUP","GROUP_ID as groupId,GROUP_NAME,RULES,CREATE_TIME","GROUP_ID=${'$'}{params} AND IS_DELETE=0 ")
class GoodsQueryer : NamedSimpleCachedTableQueryer("商品(${'$'}{params})详情",arrayOf("GOODS_ID"),"db_aries_pay_goods.goods","type,platform,b_item_id,amount,`describe`,is_delete","id=${'$'}{params} ")

val allAriesQueryer = arrayOf<DataQueryer>(UserIdQueryer()

        , UsersCompanyQueryer()
        , UsersRolesQueryer()
        , Users2BCourseQueryer()
        , Users2CCourseQueryer()
        , Users2CCourseStudyQueryer()
        , UsersCreatedGroupQueryer()
        , UsersJoinedGroupQueryer()
        , UsersOrderQueryer()

        , CourseId2BQueryer()
        , CourseId2CQueryer()
        , CompanyIdQueryer()
        , Recruit2BQueryer()
        , Class2BQueryer()
        , GroupQueryer()
        , GoodsQueryer()
)