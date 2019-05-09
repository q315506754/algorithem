package com.jiangli.doc.sql.helper.aries.questionaire

import java.lang.StringBuilder

/**
 *
 *
 * @author Jiangli
 * @date 2019/2/19 20:25
 */
fun main(args: Array<String>) {
    var sb = StringBuilder()

    var str = "1.O2O 2.旅游 3.分类信息 4.音乐/视频/阅读 5.在线教育 6.社交网络\\r\\n7.人力资源服务 8.企业服务 9.信息安全 10.智能硬件 11.移动互联网 12.互联网 13.计算机软件\\r\\n14.通信/网络设备 15.数据服务 16.电子商务 17.广告/公关/会展 18.金融 19.物流/仓储\\r\\n20.贸易/进出口 21.咨询 22.工程施工 23.汽车生产 24.区块链 25.人工智能 26.游戏\\r\\n27.医疗健康 28.生活服务 29.其他行业"

    str =str.replace("\\r\\n"," ")
    str =str.replace("  ","")
    println(str)

    val split = str.split(" ")
    println(split.size)

    println("----------------------------------")

    split.forEachIndexed { idx, it ->
        println(it)

        val split1 = it.split(".")
        val n = split1[0]
        val dis = split1[1]

        var str = if(idx == 0) "" else "\r\n,"
        sb.append("$str\"$n\" to \"$dis\"")
    }

    println(sb)
}