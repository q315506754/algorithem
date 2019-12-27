package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import net.sf.json.JSONObject
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * 批量下载企业logo
 *
 * @author Jiangli
 * @date 2019/11/18 19:46
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val inputpath = PathUtil.desktop("企业logo")
    println("$inputpath")

//    FileUtil.acceptDragFile()
    PathUtil.ensurePath(inputpath)
    FileUtil.deleteUnderDir(inputpath)

    FileUtil.openDirectory(inputpath)

    val param = JSONObject()
//    param.put("isHome",0)
//    param.put("size",100)
//    val postUrl = HttpPostUtil.postUrl("https://aries-app.g2s.cn/knowledgeSquareData/getCompanyRank?isHome=0&size=100", param)
    val postUrl = HttpPostUtil.postUrl("http://aries-app.g2s.cn/knowledgeSquareData/getCompanyRank?isHome=false&pageNum=0&size=100", param)
    println(postUrl)

    val json = JSONObject.fromObject(postUrl)
    val jsonArray = json.getJSONObject("rt").getJSONArray("listMap")

    jsonArray.forEach {
        val one = it as JSONObject
        val COMPANY_ID = one.getInt("companyiId")
        val COMPANY_LOGO = one.getString("companyLog")
        println(COMPANY_ID)
        println(COMPANY_LOGO)

        val openConnection = URL(COMPANY_LOGO).openConnection()
        val inputStream = openConnection.getInputStream()
        val fileOutputStream = FileOutputStream(File(inputpath, "$COMPANY_ID.png"))
        IOUtils.copy(inputStream,fileOutputStream)
    }

    return
}