package com.jiangli.doc.txt.wenda

import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.Redis
import com.jiangli.doc.txt.execute
import com.jiangli.doc.txt.importdata.delKeysPage
import com.jiangli.doc.txt.importdata.queryDistinct
import org.springframework.jdbc.core.JdbcTemplate

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {

    val pool = Redis.getYanfaPool()
//    val pool = Redis.getYufaPool()
    val jdbc = DB.getJDBCForYanFa()
//    val jdbc = DB.getWendaJDBCForWaiWang()
//    val query = jdbc.query("select distinct CREATE_USER from ZHS_BBS.QA_QUESTION", ColumnMapRowMapper())


// update QA_QUESTION set IS_DELETED = 1 where IS_DELETED=0;
// update QA_ANSWER set IS_DELETED = 1 where IS_DELETED=0;
// update QA_COMMENT set IS_DELETED = 1 where IS_DELETED=0;
// update QA_OPERATION set IS_DELETED = 1 where IS_DELETED=0 AND OPERATION_TYPE=2;

    val ANCESTOR_COURSE_ID = queryDistinct(jdbc, " ZHS_BBS.QA_QUESTION", "ANCESTOR_COURSE_ID")
    println(ANCESTOR_COURSE_ID)
    val RECRUIT_ID = queryDistinct(jdbc, " ZHS_BBS.QA_QUESTION", "RECRUIT_ID")
    println(RECRUIT_ID)
    val userSet = getTotalUserId(jdbc)
    println(userSet)

    val keys = arrayListOf<String>()


    ANCESTOR_COURSE_ID.forEach {
        it?.let {
            keys.add("qa:ancestorsCourse:questionIds:$it")   //祖先回答时间倒序
            keys.add("qa:ancestorsCourse:hot:questionIds:$it")  //热门
        }
    }

    userSet.forEach {
        it?.let {
            keys.add("qa:my:questionIds:$it")   //我的提问
            keys.add("qa:answer:myAnser:$it")  //我的回答
            keys.add("qa:operation:myQuestioninfo:$it:2")  //我的围观
            keys.add("qa:comment:myComment:$it")  //我的评论

//            keys.add("qa:$it")  //
        }
    }

    delKeysPage(keys)


    pool.execute {
        val redis = it
        keys.forEach {
            redis.del(it)
        }
    }
}


private fun getTotalUserId(jdbc: JdbcTemplate): HashSet<String?> {
    val list1 = queryDistinct(jdbc, " ZHS_BBS.QA_QUESTION", "CREATE_USER")
    val list2 = queryDistinct(jdbc, " ZHS_BBS.QA_ANSWER", "A_USER_ID")
    val list3 = queryDistinct(jdbc, " ZHS_BBS.QA_COMMENT", "COMMENT_USER_ID")
    val list4 = queryDistinct(jdbc, " ZHS_BBS.QA_OPERATION", "OPERATION_PERSON")
    val set = hashSetOf<String?>()
    set.addAll(list1)
    set.addAll(list2)
    set.addAll(list3)
    set.addAll(list4)
    return set
}
