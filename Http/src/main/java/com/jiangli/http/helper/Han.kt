package com.jiangli.http.helper

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.common.utils.PathUtil
import net.sf.json.JSONObject

/**
 *
 *
 * @author Jiangli
 * @date 2019/12/27 21:26
 */

fun main(args: Array<String>) {


    val listUrl = "http://66api.pihoda.cn:8688//User/GetMyCollection"
    val detailUrl = "http://66api.pihoda.cn:8688//Video/GetClient"

    val UserID = 10606107
    val token = """
                """.trim()
    val startPage = 1
    val maxPage = 1
    val size = 1

    (startPage..maxPage).forEach {
        var page = it
        val outdir = "E:\\videos\\list$page"

        val p = JSONObject()
        p.put("length",size)
        p.put("start",(page-1)*size)
        p.put("UserID", UserID)
        val st = HttpPostUtil.postUrl(listUrl, p, mapOf(
                "token" to token
        ))
        println("--page:$page "+st)
        val fromObject = JSONObject.fromObject(st)
        val arr = fromObject.getJSONArray("data")

        if (arr.isEmpty) {
            println("page over..$page")
            return
        }

        arr.forEachIndexed { index, it ->
            try {
                println("page:$page $index / ${arr.size}")
                val one = it as JSONObject
                val VideoID = one.getInt("VideoID")
                val CoverImgUrl = one.getString("CoverImgUrl")
                val Name = one.getString("Name")

                val oneDir = PathUtil.buildPath(outdir, true, VideoID.toString())

                FileUtil.downloadImage(CoverImgUrl,oneDir)

                val p2 = JSONObject()
                p2.put("videoID",VideoID.toString())
                p2.put("userID", UserID)
                val st = HttpPostUtil.postUrl(detailUrl, p2, mapOf(
                        "token" to token
                ))
                println("one:"+st)
                val fromObject = JSONObject.fromObject(st)
                val data = fromObject.getJSONObject("data")
                val m3u8Url = data.getString("Url")
                println(m3u8Url)

                FileUtil.downloadM3U8(m3u8Url,oneDir)
            } catch (e: Exception) {
                e.printStackTrace()
                System.err.print("[error]record.. $page $index / ${arr.size}")
            }
        }
    }


}