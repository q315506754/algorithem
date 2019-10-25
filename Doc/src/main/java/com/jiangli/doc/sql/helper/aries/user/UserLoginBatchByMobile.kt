package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import com.mongodb.BasicDBObject
import org.apache.commons.lang.StringUtils
import java.util.regex.Pattern

/**
 *
 *
 * @author Jiangli
 * @date 2018/12/21 14:14
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val mongo = Ariesutil.getMongo(env, Ariesutil.MongoDbCol.ARIES_LOGIN)

    val ouputDir = PathUtil.desktop("""登录日志_1""")

//    val MOBILES = "13661749570"
    val MOBILES = """
宛若故里：金杜 18520702478
天鑫洋：吴亚凝 15210825215
嘉和一品：吕洁 13910815779
国泰：周鑫 18810608187
      余晖 13718503749
温氏：晏培华13600237372
中新力合：俞晨浩 13867451876
腾讯：孟宏强18627922244
广铝：吴晓莹 18902221630
路迪斯达：周彤 15919376076
新和：张玥 13756676643
常青藤：文华 18820016096
中建北京：杨国玲 18610199959
电声：翁秀华 13802432928
德拓：陈默 18621603855
前沿化工：王婷 13813819581
新价值：汪晓霞 13911530909
国显科技：薛松 15919879855
华大基因：张莹光 18658890389
朴道水汇：戴梦静 13817200446
三生药业：陈寒华 15650577193
TCL：张美燕 13809662172
中航：齐学梅 18666916918
      张立贤 15919197236
知室木棉：黎杰锐 13316025563
凯森保险：郭珊 13507313978
          张娜 18802951127
诺亚：罗国凯 luoguokai@noahwm.com
新潮：曾翼 13548024273
雪中飞：许正勇 18651189321
凯盛科技：褚德南 18910961876
安锐供应链：谢捷丹 13757174487
轻轻：范奇峰 13386060310
中交一公：赵成升 13331093300
西安中科：李妍 18629261213
顾家：周海洪 13777449699
老娘舅：高洁 13867257339
清华紫光：李秋玲 13810504790
奥鹏：刘广新 13311180528
白象：邵建国 18973046586
蓝帆医疗：于清瑞 15550317858
无锡灵山：曹慧 18651573706
金彭车业：魏丕玉 15062173987
华兴：戚惠敏hm.qi@ghbank.com.cn
    """.trimIndent()

    MOBILES.split("\n").filter {StringUtils.isNotBlank(it)}.forEach {
//        val mobile = """\d{11}""".toRegex().find(it)
//        println(mobile!!.next())
        val line = it.trim()
        println("开始执行 $line")

        val message = Pattern.compile("""\d{11}""").matcher(line)
        var userId:Int?=0
        var mt:String?=null

        if ( message.find()) {
            mt = message.group()
            userId = Ariesutil.getUserId(jdbc, "", mt).toInt()
        } else {
            val eReg = Pattern.compile("""[a-zA-Z0-9\.]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+${'$'}""").matcher(line)
            if (eReg.find()) {
                mt = eReg.group()
                userId = Ariesutil.getUserId(jdbc, "", "",mt).toInt()
            } else {
                println("unknown $line")
                System.exit(1)
            }
        }

        val name= line.substring(0,line.length-mt!!.length)
//        println(mobile)
        println("$name $mt")
//        println(name)

        val list = mutableListOf<MutableMap<String,Any>>()
        val query = BasicDBObject()
        query.put("user_id",userId)
        query.put("appType","ARIES_PC")
        val sort = BasicDBObject()
        sort.put("loginTime",-1)

        val dbCursor = mongo.find(query).sort(sort)
        var count = 0
        while (dbCursor.hasNext()) {
            val next = dbCursor.next()
            //        println(next)

            val one = mutableMapOf<String,Any>()
            one.putAll(next.toMap() as Map<out String, Any>)
            one.remove("_id")
            one.remove("_class")

            val ts = one.get("loginTime") as java.util.Date
            //        println(ts?.javaClass)
            one.put("loginTime", DateUtil.getDate_yyyyMMddHHmmss(ts.time))

            list.add(one)
            count++
        }
        val ouputName = if(count>0) "$name-($count).xlsx" else "$name-无学习中心登录记录.xlsx"
        val ouputFile = PathUtil.buildPath(ouputDir,false,ouputName)
        writeMapToExcel(ouputFile,list)
    }

//
//    println("userId:$userId uuid:$uuid")



}