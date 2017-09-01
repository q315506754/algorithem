package com.jiangli.doc.txt.zhs

import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.Redis
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {
//    println(System.currentTimeMillis())
//    return
//    val pool = Redis.getYanfaPool()
//    val jdbc = DB.getJDBCForYanFa()

    val pool = Redis.getYufaPool()
    val jdbc = DB.getJDBCForYuFa()


    var  list = jdbc.query("""select c.NAME as COURSENAME,c.COURSE_ID ,e.EXAM_ID,e.CHAPTER as EXAM_CHAPTER,e.CHAPTER_ID,e.IS_OBJECT,e.IS_DELETE as  EXAM_ISDELETE ,ca.NAME as CHAPTER,ca.IS_DETELED as CHAPTER_ISDELETE from db_G2S_OnlineSchool.EXAM e,db_G2S_OnlineSchool.CC_CHAPTER ca,db_G2S_OnlineSchool.TBL_COURSE c WHERE e.IS_DELETE=0 and e.CHAPTER_ID = ca.Id  and ca.COURSE_ID = c.COURSE_ID and CHAPTER_ID in(
  select ca.ID as CHAPTER_ID from db_G2S_OnlineSchool.CC_CHAPTER ca,db_G2S_OnlineSchool.TBL_COURSE c WHERE c.TYPE=4 and ca.IS_DETELED = 1 and ca.COURSE_ID = c.COURSE_ID
)""", ColumnMapRowMapper())
    println(list.size)
    println(list)


//    pool.execute{
//        val jedis = it
//
//        courseIds.forEach {
//            jedis.executeDel("cc:course:$it:treenity")
//            jedis.executeDel("cc:course:$it")
//        }
//    }


}