package com.jiangli.doc.sql.helper.aries.video

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import net.sf.json.JSONObject
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 视频转码
 *
 * @author Jiangli
 * @date 2018/12/21 14:14
 */
fun main(args: Array<String>) {
//    val env = Env.WAIWANG
    val env = Env.YUFA
    val jdbc = Ariesutil.getJDBC(env)
//    val mongo = Ariesutil.getMongo(env, Ariesutil.MongoDbCol.ARIES_LOGIN)

    val query = jdbc.query("""
SELECT DISTINCT (VIDEO_ID)
FROM db_aries_base.TBL_VIDEO_TASK WHERE SPEC IS NOT  NULL  AND STATUS = 0
ORDER BY VIDEO_ID DESC ;
    """.trimIndent(), ColumnMapRowMapper())

    query.forEach {
        val VIDEO_ID = it["VIDEO_ID"] .toString()
        println(VIDEO_ID)

        val jsonObject = JSONObject()
        jsonObject.put("videoId",VIDEO_ID)
        jsonObject.put("pwd","ablejava")
        val rs = HttpPostUtil.getUrlRequest("""
http://yf-base-video.g2s.cn/synTranscodeInfo
                """.trimIndent(), jsonObject)

        println(rs)
    }

}