package com.jiangli.doc.json

import com.jiangli.common.utils.HttpPostUtil
import net.sf.json.JSONArray
import net.sf.json.JSONObject


/**
 *

1. 用户管理
1. 注册用户
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/user/getToken
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
userId	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
name	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
avatar	String	是	用户头像 URI，最大长度 1024 字节
返回结果 20
详细请参考 通用接口返回结果
返回值25	返回类型	说明
oSSFileDto	Object	对象，见【OSSFileDto】

OSSFileDto说明
返回值25	返回类型	说明
path	String	文件路径
code	String	0：成功 其他：失败
domainName	String	域名
message	String	消息说明


{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "userId": "传入的userId",
        "token": "G867OgA1LB7A4"
    },
    "currentTime": 1597919394427,
    "successful": true
}
 */
fun main(args: Array<String>) {
//    http://{{apipath}}/web/doc/document/saveOrUpdate

//    预发
    var url = "http://120.92.138.210:20050/web/doc/document/saveOrUpdate"

//    var url = "http://120.92.138.210:20010/web/doc/document/saveOrUpdate"

    var content = getContent()

    val split = content.split("\n".toRegex())


    var status = 0
    var hTitle = ""
    val apis = mutableListOf<OpenInf>()
    var one:OpenInf? = null

    var statusMap = mutableMapOf(
            "功能描述" to 3
            ,"实现方法" to 4
            ,"请求地址" to 5
            ,"请求方法" to 6
            ,"调用频率" to 7
            ,"签名规则" to 8
            ,"参数说明" to 9
            ,"输入参数" to 10
            ,"参数\t" to 15
            ,"返回结果" to 20
            ,"返回值\t" to 25
            ,"{" to 30
    )

    split.forEach {
        val orgline = it
        val line = it.trim()

        if (line.isBlank()) {
            return@forEach
        }

        var n = isHeadLine(line)
        if (n>0) {
           var tt =  line.substring(line.indexOf(".")+1).trim()
            if (status == 1) {
                status = 2
                hTitle = one!!.title!!

                one!!.title = tt
                one!!.docPath = hTitle + "-" +one!!.title!!
            } else {
                status = 1
                one = OpenInf("")
                apis.add(one!!)
                one!!.title = tt
                one!!.docPath = hTitle + "-" +one!!.title!!
            }
            return@forEach
        }

        var isStatusLine  = false
        statusMap.forEach { t, u ->
            if (!isStatusLine && line.startsWith(t) && u>=status) {
                status = u
                isStatusLine = true
            }
        }

//
        if (status == 3 && !isStatusLine) {
            one!!.func=appendLine(one?.func,line)
        } else if (status == 5 && isStatusLine) {
            one!!.url=line.substring(line.indexOf("：")+1).trim()
        }else if (status == 6 && isStatusLine) {
            one!!.method=line.substring(line.indexOf("：")+1).trim()
        }else if (status == 7 && isStatusLine) {
            one!!.freq=line.substring(line.indexOf("：")+1).trim()
        }else if (status == 8 && isStatusLine) {
            one!!.signRule=line.substring(line.indexOf("：")+1).trim()
        }else if (status == 15 && isStatusLine) {
            val split1 = line.split("\t")
            split1.forEach {
                one!!.paramBody.input.heads.add(it)
            }
        }else if (status == 15 && !isStatusLine) {
            val split1 = line.split("\t")
            one!!.paramBody.input.params.add(convertMuList(split1))
        } else if (status == 20 && !isStatusLine) {
            one!!.paramBody.outputs.add(Params(line))
        } else if (status == 25 && isStatusLine) {
            val split1 = line.split("\t")
            split1.forEach {
                one!!.paramBody.getLastOutput().heads.add(it)
            }
        } else if (status == 25 && !isStatusLine) {
            if (line.contains("\t")) {
                val split1 = line.split("\t")
                one!!.paramBody.getLastOutput().params.add(convertMuList(split1))
            } else {
//                20
                one!!.paramBody.outputs.add(Params(line))
            }
        } else if (status == 30) {
            one!!.paramBody.exp= appendLine(one!!.paramBody.exp,orgline)!!
        }

    }

//    request

    apis.forEach {
        println("======="+it.docPath)
//        println(it.getBody())

        val jsonObject = JSONObject()
        val arr = JSONArray()

        jsonObject.put("name",it.docPath)
        jsonObject.put("author","蒋礼")
        jsonObject.put("desc","inf v1")
        jsonObject.put("status","1")
        jsonObject.put("remark","init")


        val para = JSONObject()
        para.put("title",it.title)
        para.put("contentType","1")
        para.put("content",it.getBody())
        para.put("displayContent",it.getBody())
        arr.add(para)
        jsonObject.put("paras",arr)

        val rs = HttpPostUtil.postUrl("""
$url
                """.trimIndent(), jsonObject)

        println(rs)
//        return
    }
}

fun convertMuList(split1: List<String>): MutableList<String> {
   val ret:MutableList<String> = mutableListOf()
    split1.forEach {
        ret.add(it)
    }
    return ret
}

fun appendLine(func: String?, line: String): String? {
    if (func == null) {
        return line
    }
    return func + "\n"+line
}

private fun isHeadLine(line: String):Int {
    if (line.contains(".")) {
        val substring = line.substring(0, line.indexOf("."))
        try {
            val parseInt = Integer.parseInt(substring)
            return parseInt
        } catch (e: Exception) {
        }
    }
    return -1
}

data class OpenInf(
        var docPath:String
) {
    var title:String? = null
    var func:String? = null
    var url:String? = null
    var method:String? = null
    var freq:String? = null
    var signRule:String? = null
    val paramBody:ParamAndBody = ParamAndBody("")


    fun getBody():String{
        val lv1Title = "####"
        val lv2Title = "#####"

        return """
#${title}#

${lv1Title}功能描述
${func}

${lv1Title}实现方法
${lv2Title}请求地址：${url}
${lv2Title}请求方法：${method}
${lv2Title}调用频率：${freq}
${lv2Title}签名规则：${signRule}

${lv1Title}参数说明
${lv2Title}输入参数
${paramBody?.getInputBody()}

${lv2Title}返回结果
${paramBody?.getOutputBody()}

${lv2Title}返回示例
```
${paramBody?.exp}
```

        """.trimIndent()
    }
}

data class ParamAndBody(
    var exp:String
) {
    val input:Params = Params("")
    val outputs:MutableList<Params> = mutableListOf()

    fun getInputBody():String{
        return """
${input.getBody()}
        """.trimIndent()
    }

    fun getOutputBody():String{
        var ret = ""
        outputs.forEach {
            ret = ret + "${it.getBody()}\n"
        }
        ret+="\n"

        return """
${ret}   
        """.trimIndent()
    }

    fun getLastOutput():Params{
        return outputs.get(outputs.lastIndex)
    }
}

data class Params(
    var desc:String
) {
    val heads:MutableList<String> = mutableListOf()
    val params:MutableList<MutableList<String>> = mutableListOf()

    fun getBody():String{
        var ret = ""
        if (desc.isNotBlank()) {
            ret = "${desc}\n\n"
        }
//        head
        heads.forEach {
            ret = ret + "|${it}"
        }
        ret+="|\n"
        heads.forEach {
            ret = ret + "|:---"
        }
        ret+="|\n"


        params.forEach {
//            line
            it.forEach {
                ret = ret + "|${it}"
            }
            ret+="|\n"
        }

        return """
${ret}
        """.trimIndent()
    }
}

private fun getContent():String{
    return """
        
1. 用户管理
1. 注册用户
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/user/getToken
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
userId	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
name	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
avatar	String	是	用户头像 URI，最大长度 1024 字节
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "userId": "传入的userId",
        "token": "G867OgA1LB7A4"
    },
    "currentTime": 1597919394427,
    "successful": true
}


2. 用户详情 
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/user/detail
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	用户 Token
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
		
		

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "user": {
            "token": "KpYzMjXWWADxb",
            "areaCode": "+86",
            "mobile": "17721385968",
            "name": "金星老师",
            "email": "",
            "avatar": "http://image.zhihuishu.com/zhs/ablecommons/demo/201807/2de2f7ab96ae4a81a91c86c45ab6c2eb.png",
            "createTime": 1517888835000,
            "companyId": null,
            "describe": null,
            "companyLogo": null,
            "signature": "三人行 必有我师",
            "backImage": http://image.zhihuishu.com/zhs/ablecommons/demo/201807/2de2f7ab96ae4a81a91c86c45ab6c2eb.png"
        }
    },
    "currentTime": 1598342175724,
    "successful": true
}

3. 知识教练详情 
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/coach/detail
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	用户 Token
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
		
		
		
		

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "operationAvatar": "operationAvatar",
        "qrCode": "qrCode",
        "signature": "三人行 必有我师",
        "companyName": "测试企业",
        "operationName": "operationName",
        "backImgSquare": "backImgSquare",
        "title": "大神教练",
        "backImgRectangle": "http"
    },
    "currentTime": 1598342511831,
    "successful": true
}

2. 视频课程学习
1. 课程基本信息
功能描述
根据课程cid获取课程基本信息，包括课程名字，课程介绍。
实现方法
请求地址： http://数据中心域名/openPlatform/baseCourse/courseInfo
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
cid	String	是	请求的课程 Id，应用内课程唯一标识。
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
courseIntroduction	Json	课程图文介绍，详见课程图文介绍字段说明
courseName	String	用户 Id，与输入的用户 Id 相同
courseType	int	视频课程类型
courseSlogan	String	推荐词
isSkipChapter	int	是否跳章 (1.跳 0.不跳)
isSkipVideoHead	int	是否跳片花 (1.跳 0.不跳 )
videoHeadTime	int	片头时间 (单位：秒)
cid	String	课程 Id，应用内课程唯一标识

课程图文介绍字段说明
返回值	返回类型	说明
image	String	图片介绍，type=2时赋值
text	String	文字介绍，type=1时赋值
type	int	内容类型，1表示文字介绍，2表示图片介绍
scaleXY	double	图片宽高比，type=2时赋值

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "courseIntroduction": [
            {
                "image": "",
                "text": "课程简介",
                "type": "1",
                "scaleXY": ""
            },
            {
                "image": "http://image.g2s.cn/zhs_yanfa_150820/ariescourse/demo/202008/6529aaec7d104267b5374dc3ac147a7b.png",
                "text": "",
                "type": "2",
                "scaleXY": "1.000"
            }
        ],
        "courseName": "管理者的50堂必修课",
        "courseType": 0,
        "courseSlogan": "1111",
        "isSkipChapter": 0,
        "isSkipVideoHead": 0,
        "videoHeadTime": 1,
        "cid": "8MyPW69eN7JGw"
    },
    "currentTime": 1598248258092,
    "successful": true
}


2. 课程教师列表
功能描述
根据课程cid获取课程教师列表。
实现方法
请求地址： http://数据中心域名/openPlatform/baseCourse/courseTeacherList
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
cid	String	是	请求的课程 Id，应用内课程唯一标识。
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
teacherList	Array	老师列表

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "teacherList": [
            {
                "teacherName": "张心语",
                "teacherIntroduction": [
                    {
                        "image": "",
                        "text": "老师简介",
                        "type": "1",
                        "scaleXY": ""
                    },
                    {
                        "image": "http://image.g2s.cn/zhs_yanfa_150820/ariescourse/demo/202008/0a705b0d1b564ba890297e62041f4600.jpg",
                        "text": "",
                        "type": "2",
                        "scaleXY": "1.778"
                    }
                ],
                "teacherTitle": "副教授",
                "teacherImg": "http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/201804/8398bedd66cf4c31ad5a472ed2faf43d.jpg"
            },
            {
                "teacherName": "胡小月111",
                "teacherIntroduction": [],
                "teacherTitle": "副教授",
                "teacherImg": "http://image.g2s.cn/zhs_yanfa_150820/ariescourse/demo/201803/afe296b9932b470fbe8e251d8047db2f.jpg"
            }
        ]
    },
    "currentTime": 1598336645804,
    "successful": true
}

3. 查课程章节信息
功能描述
查课程章节信息。
实现方法
请求地址： http://数据中心域名/openPlatform/baseCourse/courseChapterInfo
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
cid	String	是	请求的课程 Id，应用内课程唯一标识。
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
courses	Array	课程章节列表
displayChapterNo	int	是否显示章节序号，0 不显示  1显示
videoSec	int	视频总时长
videoNum	int	视频数量

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "courses": [
            {
                "haveFlowerCard": false,
                "lessonVideoId": null,
                "videoWatchNum": 0,
                "audioId": null,
                "lessonId": null,
                "videoId": null,
                "type": 1,
                "title": "西游记",
                "totalVideoSec": null,
                "lessonVideoSort": null,
                "chapterId": "1489",
                "speaker": null,
                "lessonSort": null,
                "chapterSort": 1,
                "id": 1489
            },
            {
                "haveFlowerCard": false,
                "lessonVideoId": null,
                "videoWatchNum": 0,
                "audioId": null,
                "lessonId": null,
                "videoId": null,
                "type": 1,
                "title": "聊斋",
                "totalVideoSec": null,
                "lessonVideoSort": null,
                "chapterId": "1624",
                "speaker": null,
                "lessonSort": null,
                "chapterSort": 2,
                "id": 1624
            }
        "displayChapterNo": 1,
        "videoSec": 24,
        "videoNum": 101
    },
    "currentTime": 1598337712347,
    "successful": true
}



4. 保存章节进度
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/progress/submitProgress
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "userId": "传入的userId",
        "token": "G867OgA1LB7A4"
    },
    "currentTime": 1597919394427,
    "successful": true
}

5. 查询用户章节学习进度
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/progress/queryCourseUserProgress
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "userId": "传入的userId",
        "token": "G867OgA1LB7A4"
    },
    "currentTime": 1597919394427,
    "successful": true
}

6. 查章节花语卡
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/baseCourse/courseFlowerCard
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
lessonId	int	是	课程章节Id
cid	String	是	请求的课程 Id，应用内课程唯一标识。
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
flowerCardUrl	String	花语卡地址
flowerCardText	String	花语
teacherName	String	老师名字
lessonId	int	章节id
shareName	String	分享文字

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
            "flowerCardUrl": "http://image.g2s.cn/zhs_yanfa_150820/ablecommons/demo/202003/5b96b930ef074c62bb1be294ea81d28e.jpg",
            "flowerCardText": "花语,
            "teacherName": "陈春花",
            "lessonId": 1405,
            "shareName": "来自知室课程 「管理者的50堂课」"
    },
    "currentTime": 1598340926639,
    "successful": true
}


7. 查询水印内容
功能描述
查询水印内容。
实现方法
请求地址： http://数据中心域名/openPlatform/baseCourse/courseWatermark
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	否	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	请求的课程 Id，应用内课程唯一标识。
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
data	String	水印文本
watermark_img	String	水印图片

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "data": "用户   8MyPW69eN7JGw",
        "watermark_img": "http://image.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201909/a0691ca0dcb341e89cee4fea09aefc90.png"
    },
    "currentTime": 1598340700427,
    "successful": true
}

8. 查询个人课程学习时长
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/progress/queryCourseUserProgressAll
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "userId": "传入的userId",
        "token": "G867OgA1LB7A4"
    },
    "currentTime": 1597919394427,
    "successful": true
}



3. 同学吧
1. 查课程同学吧列表
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/messagelist
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
pageSize	Int	否	每页大小，范围为1-100，默认为10
pageNum	Int	否	页码，从0开始，代表第一页
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "nextId": 17,
        "totalRecords": 25,
        "maxDiscussShowCount": 3,
        "records": [
            {
                "imgs": [],
                "isSentToHua": 0,
                "isSentToHuaStr": null,
                "isLike": false,
                "createTime": 1589275904000,
                "isAnswered": 1,
                "id": 2658,
                "user": {
                    "realName": "曹鹏C",
                    "avatar": "http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/202004/d887cb975cd84fcfbaa012f9d349f29b.jpg",
                    "title": null,
                    "token": "O0vDqmwg5RDA1"
                },
                "content": null,
                "createTimeStr": "2020-05-12 17:31:44",
                "hasRelatedQuestion": 1,
                "attaches": [
                    {
                        "attachType": 1,
                        "attachBody": {
                            "imgs": [
                                {
                                    "width": 220,
                                    "url": "http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/202005/a58730ee687f4fd6a935ad8090df4b37.png",
                                    "height": 201
                                }
                            ],
                            "createTime": 1589275904000,
                            "isAnswered": 1,
                            "id": 2657,
                            "content": "问题？？？？？？？？？",
                            "createTimeStr": "2020-05-12 17:31:44"
                        },
                        "attachId": 2657
                    }
                ]
            }
        ]
    },
    "currentTime": 1598442692760,
    "successful": true
}

2. 查留言下评论列表
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/discussList
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
topicId	String	是	
pageSize	Int	否	每页大小，范围为1-100，默认为10
pageNum	Int	否	页码，从0开始，代表第一页
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同
{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "totalRecords": 7,
        "nextId": 1,
        "records": [
            {
                "imgs": [],
                "topicId": 2659,
                "discussId": null,
                "isLike": false,
                "createTime": 1589344290000,
                "likeCount": 0,
                "id": 2265,
                "user": {
                    "realName": "曹天利",
                    "avatar": "http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/202005/e4ac57e2616b45db9edf24cbe26f6ccf.png",
                    "title": null,
                    "token": "KpYzMjXKyaDxb"
                },
                "content": "ii",
                "createTimeStr": "2020-05-13 12:31:30"
            }
        ]
    },
    "currentTime": 1598526802565,
    "successful": true
}


3. 查询课程社区配置
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/getConfig
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "hasSendToAuthorCheckBox": false,
        "sendToAuthorDisplay": null,
        "maxWordsOfStudent": 5000,
        "wordsAfterLike": "感谢您的点赞",
        "maxWordsOfQA": 5000
    },
    "currentTime": 1598526588834,
    "successful": true
}
4. 发布留言
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/publishMessage
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	用户名称，最大长度 128 字节。重新获取 Token 传入新的名称后，将覆盖之前的用户名称。
content	String	是	内容，最大长度 5000

imgs	JSONArray	否	
isSentToHua	Int	否	
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "id": 2733
    },
    "currentTime": 1598509492934,
    "successful": true
}
5. 发布评论
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/publishDiscuss
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
topicId	Int	是	
content	String	是	内容，最大长度 5000

imgs	JSONArray	否	
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "id": 2277
    },
    "currentTime": 1598527204025,
    "successful": true
}

6. 删除留言
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/removeMessage
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
topicId	Int	是	
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}

7. 删除评论
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/removeDiscuss
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
discussId	Int	是	
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}

8. 点赞/取消点赞 留言
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/doLikeMessage
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
topicId	Int	是	
isLike	Int	是	是否点赞  0否 1是
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}

9. 点赞/取消点赞 评论
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/doLikeDiscuss
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
discussId	Int	是	
isLike	Int	是	是否点赞  0否 1是
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}


10. 点赞/取消点赞 课程
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/doLikeCourse
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	
isLike	Int	是	是否点赞  0否 1是
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}

11. 判断用户是否点赞课程
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/isLikeCourse
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}


12. 课程互动数量统计
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/txb/courseInteractionStat
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
token	String	是	请求的用户 Id，应用内唯一标识，重复的用户 Id 将被当作为同一用户，支持大小写英文字母、数字、部分特殊符号 + = - _ 的组合方式，最大长度 64 字节。
cid	String	是	
			
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
token	String	用户 Token，可以保存应用内，长度在 256 字节以内，Token 中携带 IM 服务动态导航地址，开发者不需要进行处理，正常使用即可。
userId	String	用户 Id，与输入的用户 Id 相同

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "data": {
            "businessType": 11,
            "businessId": 600143,
            "likeNum": 11,
            "viewNum": 10,
            "shareNum": 20
        }
    },
    "currentTime": 1598529642834,
    "successful": true
}



4. 通用
1. 批量上传图片
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/file/uploadImages
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
imageFiles	MultipartFile[]	是	上传文件
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
oSSFileDtos	Array	对象数组，见【OSSFileDto】

OSSFileDto说明
返回值	返回类型	说明
path	String	文件路径
code	String	0：成功 其他：失败
domainName	String	域名
message	String	消息说明



{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDtos": [
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/fb1a279d1241454cb8a4a7d69dd28fda.jpg",
                "domainName": "http://image.g2s.cn/"
            },
            {
                "code": "0",
                "message": "上传成功！",
                "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/074e75be37754779b9da2caebefe39e4.png",
                "domainName": "http://image.g2s.cn/"
            }
        ]
    },
    "currentTime": 1598438685423,
    "successful": true
}

2. 上传单张图片
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/file/uploadImage
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
imageFile	MultipartFile	是	上传文件
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
oSSFileDto	Object	对象，见【OSSFileDto】

OSSFileDto说明
返回值	返回类型	说明
path	String	文件路径
code	String	0：成功 其他：失败
domainName	String	域名
message	String	消息说明



{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "oSSFileDto": {
            "code": "0",
            "message": "上传成功！",
            "path": "http://image.g2s.cn/zhs_yanfa_150820/apparies/openapi/202008/7bec735858fb46338e9edfd1384412a2.jpg",
            "domainName": "http://image.g2s.cn/"
        }
    },
    "currentTime": 1598580322484,
    "successful": true
}


3. 视频播放
功能描述
生成用户在知室的唯一身份标识 Token。
实现方法
请求地址： http://数据中心域名/openPlatform/video/play
请求方法： POST
调用频率： 无限制
签名规则： 所有请求服务端 API 接口的请求均需要进行规则校验，详细请参考 通用 API 接口签名规则
参数说明
输入参数
参数	类型	必传	说明
videoId	String	是	视频id
返回结果 
详细请参考 通用接口返回结果
返回值	返回类型	说明
url	String	使用Base64加密后的m3u8地址，使用前需要decode出http连接

{
    "status": "SUCCESS",
    "message": null,
    "rt": {
        "url": "Ly9hcmllcy12aWRlby5nMnMuY24venMvbTN1OC8yMDIwLTA4LTIxL0EzRDk3M1h4RjJlbVVzMXRDUjRrM0NBUDlVdDJ2d2pILzg1OGJhY2ZmM2IxNjQ3NDViMTcwMTQ5MWVkYTM0MjY4Lm0zdTg/TXRzSGxzVXJpVG9rZW49SUczemtGaG5ZdWdpVlI4aVFFNUFuMjRXSkJRSmczVmgxMlJHUVdSamxlR2JXS1BJZDUyem1TYVhmVFdjWVFHU3duU3JnT1VPTWtwM2RLdXUlMjUyQjNFU01mOFRqaE4xZDlQSTNFdzJqM0RMJTI1MkJFYUFnWVJrWXczRFVGR09QejBITzYzdHFwYVl4aktzUFRKUHNHYmVEajljSWs4MHNMTXI4U1JjYUhPTndjc0ltWmclMjUzRA=="
    },
    "currentTime": 1598342883183,
    "successful": true
}

    """.trimIndent()
}