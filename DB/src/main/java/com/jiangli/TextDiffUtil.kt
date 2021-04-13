package com.jiangli

import com.alibaba.fastjson.JSONObject

/**
 *
 *
 * @author Jiangli
 * @date 2020/12/18 15:37
 */
fun main(args: Array<String>) {
    var from = """
        {
  "createPerson": null,
  "companyName": "上海知到知室数字科技 助理教练",
  "jurisdiction": 1,
  "showText": null,
  "shareType": 1,
  "showId": 134636,
  "creatTime": 1608271028915,
  "showType": 4,
  "imageWidth": null,
  "images": [
    "http://image.g2s.cn/zhs_yanfa_150820/ariescourse/demo/202009/c3da7d3f829943078845bf5d78c3ad55.jpg"
  ],
  "thumbnailImages": [
    "http://image.g2s.cn/zhs_yanfa_150820/ariescourse/demo/202009/c3da7d3f829943078845bf5d78c3ad55_s1.jpg"
  ],
  "posterType": 1,
  "imageHeight": null,
  "posterAvator": "http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/202012/e2cbe760a3b745d3aa07f6285392535b.png",
  "companyId": 47,
  "posterName": "柚子  测试工程师",
  "posterUid": "dgr8qbqy",
  "roleName": null,
  "isZhishiCoachSay": 0,
  "zhishiCoachSayStr": null,
  "courseType": null,
  "courseId": null,
  "relateTopicId": null,
  "showCourseTitle": null,
  "h5Url": "https://ac.g2s.cn/appH5/wonderfulVideo/wonderfulVideo.html?wonderVideoId=508",
  "videoId": null,
  "videoUId": null,
  "videoImg": null,
  "videoSrc": null,
  "describe": "仨"
}
    """.trimIndent()

    var to = """
        {
  "articleImages": "http://image.g2s.cn/zhs_yanfa_150820/ariescourse/demo/202009/c3da7d3f829943078845bf5d78c3ad55.jpg",
  "companyName": "上海知到知室数字科技",
  "jurisdiction": 1,
  "showText": null,
  "videoSrc": null,
  "videoId": null,
  "shareType": 0,
  "showId": 134636,
  "creatTime": 1608271028915,
  "thumbnailImages": null,
  "videoUId": null,
  "showType": 4,
  "imageWidth": null,
  "images": null,
  "posterType": 1,
  "videoImg": null,
  "imageHeight": null,
  "posterAvator": "http://image.g2s.cn/zhs_yanfa_150820/apparies/userAvatar/202012/e2cbe760a3b745d3aa07f6285392535b.png",
  "h5Url": "https://ac.g2s.cn/appH5/wonderfulVideo/wonderfulVideo.html?wonderVideoId=508",
  "companyId": 47,
  "posterName": "柚子  测试工程师",
  "posterUid": "dgr8qbqy",
  "roleName": null,
  "describe": "仨"
}
    """.trimIndent()

    val j1 = JSONObject.parse(from) as JSONObject
    val j2 = JSONObject.parse(to) as JSONObject

    println("$j1")

    j1.forEach { t, u ->
        var v2 = j2.get(t)

        if ( u != v2) {
            println("diff $t -> $u")
            println("diff $t -> $v2 \n")
        }
    }

    j2.forEach { t, u ->
        var v2 = j1.get(t)

        if ( u != v2) {
            println("diff $t -> $v2")
            println("diff $t -> $u \n")
        }

    }
}