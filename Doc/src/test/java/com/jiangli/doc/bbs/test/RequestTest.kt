package com.jiangli.doc.bbs.test

import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import com.jiangli.doc.sql.helper.zhihuishu.bbs.requestQuestion
import org.junit.Test

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/30 9:57
 */
class RequestTest {

    @Test
    fun objTest() {
        val env = Env.WAIWANG_ALL
        val jdbc = Zhsutil.getJDBC(env)
        val domain= "http://114.55.26.161:9080/courseqa/student/qa"

//        requestQuestion(jdbc,domain,461305,"170224035")
//        requestQuestion(jdbc,domain,594858,"188225701")
        requestQuestion(jdbc,domain,589740,"187583023")
//        requestQuestion(jdbc,domain,629518,"162347707")
//        requestAnswer(jdbc,domain,136229,"162347707")
//        requestComment(jdbc,domain,136229,"162347707")
    }

}