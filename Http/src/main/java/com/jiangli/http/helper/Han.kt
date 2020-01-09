package com.jiangli.http.helper

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.common.utils.RecurUtil
import net.sf.json.JSONArray
import net.sf.json.JSONObject

/**
 *
 *
 * @author Jiangli
 * @date 2019/12/27 21:26
 */
data class HanDataRs(val data:JSONArray)

fun main(args: Array<String>) {
    val dirPrefix = "E:\\videos\\list"
//    val dirPrefix = "C:\\Users\\Jiangli\\Videos\\list"

    val startPage = 1
    val maxPage = 1
    val size = 4

    val listUrl = "http://66api.pihoda.cn:8688//User/GetMyCollection"
    val detailUrl = "http://66api.pihoda.cn:8688//Video/GetClient"
    val downloadCover = true
//    val downloadCover = false
    val downloadM3U8 = true
//    val downloadM3U8 = false

    val interceptor = object: HttpPostUtil.ReqInterceptor<HanLoginRs> {
        var hanLoginCached:HanLoginRs? = null

        override fun interceptBeforeReq(params: MutableMap<Any?, Any?>?, headers: MutableMap<String, String>?, token: HanLoginRs?) {
            headers?.put("token",token?.Token?:"")

            params?.put("UserID",token?.UserID?:"")
            params?.put("userID",token?.UserID?:"")
        }

        override fun judgeResultError(data: String?): Boolean {
            val recursiveJSON = RecurUtil.recursiveJSON(data, "data")
            val message = RecurUtil.recursiveJSON(data, "message")
            return recursiveJSON.isBlank() || recursiveJSON == "null" || message=="登陆超时，请重新登陆！"
//            return !(data?.contains("data")?:false)
        }

        override fun getCachedToken(params: MutableMap<Any?, Any?>?, headers: MutableMap<String, String>?): HanLoginRs? {
            if (hanLoginCached == null) {
                hanLoginCached = cachedLogin()
            }
            return hanLoginCached
        }

        override fun refreshToken(params: MutableMap<Any?, Any?>?, headers: MutableMap<String, String>?): HanLoginRs? {
            hanLoginCached = hanLogin()
            return hanLoginCached
        }
    }

    (startPage..maxPage).forEach {
        var page = it
        val outdir = "$dirPrefix$page"

        val st = HttpPostUtil.postUrl(listUrl,  mutableMapOf(
                "length" to size
                ,"start" to (page-1)*size
//                ,"UserID" to UserID
        ),null,interceptor)

        println("--page:$page "+st)

        val arr = RecurUtil.recursiveJSON(st,"", HanDataRs::class.java).data


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

                if (downloadCover) {
                    FileUtil.downloadImage(CoverImgUrl,oneDir)
                }

                //play
                val detail = HttpPostUtil.postUrl(detailUrl, mutableMapOf(
                        "videoID" to VideoID.toString()
//                        ,"userID" to UserID
                ),null,interceptor)

//                println("detail:"+detail)

                val m3u8Url = RecurUtil.recursiveJSON(detail,"data.Url")
                println(m3u8Url)

                if (downloadM3U8) {
                    FileUtil.downloadM3U8(m3u8Url,oneDir)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                System.err.print("[error]record.. $page $index / ${arr.size}")
            }
        }
    }


}