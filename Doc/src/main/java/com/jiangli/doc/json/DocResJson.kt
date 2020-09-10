package com.jiangli.doc.json

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject


/**
 *
 *
 * @author Jiangli
 * @date 2020/8/26 17:10
 */
fun main(args: Array<String>) {
    var res = """
        
        {
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}



    """.trimIndent()

//    res = res.replace("\n", "")
    res = res.replace(" ", "")
//    println(res)


    val fromObject = JSON.parseObject(res.toString())
    println(fromObject)

    val rt = fromObject.getJSONObject("rt")

    describeDoc(rt)

}
fun getSingleName(name:String): String {
    var ret = name[0].toUpperCase() + name.substring(1)

    if (ret.endsWith("s",true)) {
        ret = ret.subSequence(0,ret.length-1).toString()
    }

    return ret
}

fun getValType(v:Any?): String {
    var tp = "String"

    var vCls = v?.javaClass
    if (vCls!=null) {
        tp = vCls.simpleName
    }
    if (v!=null) {
        if (v is JSONArray) {
            tp = "Array"
        } else if (v is JSONObject) {
            tp = "Object"
        }else {
            try {
                if (v.toString().contains(".")) {
                    val toLong = v.toString().toDouble()
                    tp = "Double"
                } else {
                    val toLong = v.toString().toLong()
                    tp = "Int"
                }
            } catch (e: Exception) {
            }
        }
    }
    return tp
}

fun geRemark(name:String,remark:String): String {
    if (remark.isBlank()) {
        val mp = mutableMapOf<String,String>()
        mp.put("token", "用户Token")
        mp.put("cid", "请求的课程 Id，应用内课程唯一标识。")
        mp.put("pageSize", "每页大小，范围为1-100，默认为10")
        mp.put("pageNum", "页码，从0开始，代表第一页")
        mp.put("code", "0:成功 其他:失败")
        mp.put("message", "响应消息")
        mp.put("name", "名称")
        mp.put("avatar", "用户头像url地址")
        mp.put("path", "url地址")

        val get = mp.get(name)

        if (get != null ) {
            return get
        }
    }
    return remark;
}

fun describeDoc(rt: JSONObject){
    println("-------------------------")
    val  recur = mutableListOf<JSONObject>()
    val prefix = "__"
    val _name = "${prefix}name"

    if (rt.containsKey(_name)) {
        println("${rt[_name]}说明")
    }

    rt.entries.forEach { t: MutableMap.MutableEntry<String, Any>? ->
        val mp = t!!
        var key = mp.key
        if (key.startsWith(prefix)) {
            return@forEach
        }

        var singleKey = getSingleName(key)

        var v = mp.value
        var remark = ""

        var tp = getValType(v)

        if (v!=null) {
            if (v is JSONArray) {
                remark = "对象数组，见【${singleKey}】"

                if (v.size >= 0) {
                    val element = v.getJSONObject(0)
                    element.put(_name,singleKey)
                    recur.add(element)
                }
            } else if (v is JSONObject) {
                remark = "对象，见【${singleKey}】"
                v.put(_name,singleKey)
                recur.add(v)
            }
            else {
            }
        }

        println("${key} ${tp} ${geRemark(key,remark)}")
    }

    recur.forEach {
        describeDoc(it)
    }
}