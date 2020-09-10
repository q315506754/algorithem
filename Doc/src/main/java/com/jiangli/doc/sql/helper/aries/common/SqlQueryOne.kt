package com.jiangli.doc.sql.helper.aries.common

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.net.URLEncoder

/**
 * 教学企业数量 分布数据
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val videoDomain = "http://video.g2s.cn/"
    val videoEncDomain = "http://aries-video.g2s.cn/"

    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val one = jdbc.query("""
SELECT *  FROM db_aries_operation.tbl_2c_wonder_video
WHERE ID = 280;
    """.trimIndent(), ColumnMapRowMapper())[0]


    val videoId = one["VIDEO_ID"]
    println(one)
    println("${one["COVER"]}")

    val videoone = jdbc.query("""
SELECT *  FROM db_aries_base.TBL_VIDEO
WHERE ID = $videoId;
    """.trimIndent(), ColumnMapRowMapper())[0]
    println(videoone)
    val storage = videoone["STORAGE"]!!.toString()
    val m3u8storage = videoone["M3U8_STORAGE"]!!.toString()
    val storagePath = storage.substring(0,storage.lastIndexOf("/")+1)
    val m3u8storagePath = m3u8storage.substring(0,m3u8storage.lastIndexOf("/")+1)
    println("${videoDomain}$storage")
    println("https://oss.console.aliyun.com/bucket/oss-cn-hangzhou/aries-video/object?path=${URLEncoder.encode(storage)}")
    println("${videoEncDomain}${m3u8storage}")
    println("https://oss.console.aliyun.com/bucket/oss-cn-hangzhou/aries-video-encryption/object?path=${URLEncoder.encode(m3u8storagePath)}")
}