package com.jiangli.doc.bbs.test

import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import com.jiangli.doc.sql.helper.zhihuishu.bbs.ContentAnalyser
import org.junit.Test
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/30 9:57
 */
class BBSDbTest {

    @Test
    fun objTest() {
//        val env = Env.WAIWANG_ALL
        val env = Env.WAIWANG_BBS
        val jdbc = Zhsutil.getJDBC(env)
        val qId = 630152
        val query = jdbc.query("SELECT * from ZHS_BBS.QA_QUESTION WHERE QUESTION_ID = $qId;", ColumnMapRowMapper())
        val content = query[0]["CONTENT"].toString()
        println(content)
        content.toCharArray().forEach {
            val element = it.toInt()
            System.out.println("!!!!!!!!!!!$it $element ${ContentAnalyser.isNum(it)}")
        }
        val message = ContentAnalyser.getNumberPos(content)
        println(message)
        message.forEach {
            System.out.println("========$it ${content[it]}")
        }
        println(ContentAnalyser.getNumberRange(message, ContentAnalyser.conNum_least, 2))
        println(ContentAnalyser.getWeight(content))
        println(ContentAnalyser.getPinyinWeight(content))
        println(ContentAnalyser.analyse(content))
//        requestAnswer(jdbc,domain,136229,"162347707")
//        requestComment(jdbc,domain,136229,"162347707")
    }

}