package com.jiangli.doc.sql.helper.aries.git

/**
 *
 *
 * @author Jiangli
 * @date 2020/11/2 11:26
 */
fun main(args: Array<String>) {
    var str = """

#订单价格报错问题
aries-app-server

#物流单没有查询到领取物流问题
aries-app-server
aries
aries-pay-core

#用户不在组也能建投票
#投票接口无法发布问题
aries-classtools

#企业大学相关接口开发
org-app-server
aries-app-server
aries-server

#企业头条管理接口提供
org-app-server

#查话题没有图片bug
aries-forum-server

#微信扫码支付接口 & 支付宝扫码支付接口
aries-pay-core
aries-pay-gateway
aries-pay-app-server

#知识教练没有设置运营名字
aries-app-server
aries-server

#企业头条
org-server

#企业支付接口换路径
aries-pay-app-server

#知识教练
aries

#控制企业是否能登录数字大学逻辑
aries
aries-server
org-app-server

#首页搜索出来的文章链接打开分享后 标语和里面的APP下载标识都是知室的
aries-web-h5
aries-app-server：
aries:
aries-server：
aries-base-message：
aries-base-video：
aries-classtools：
aries-erp-server：
org-app-server：
org-server：


    """.trimIndent()

    var projects = mutableListOf<String>()
    val comments = mutableSetOf<String>()

    str.split("\n").forEach {
        val project = it.trim()
        if (project.isBlank()) {
            return@forEach
        }
        if (project.startsWith("#")) {
            comments.add(project)
            return@forEach
        }

        if (!projects.contains(project)) {
            projects.add(project)
//            println(project)
        }
    }

    for (project in comments) {
        println(project)
    }

    projects.sortedWith(Comparator { o1, o2 ->
//        o1.length.compareTo(o2.length)
        o1.compareTo(o2)
    }).forEach {
        println(it)
    }

    println(projects.size)

}