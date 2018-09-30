package com.jiangli.doc.sql.helper.zhihuishu.bbs

import com.jiangli.common.utils.MD5
import org.apache.commons.lang.StringUtils
import java.net.URI
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/29 14:50
 */
val numberSet = mutableSetOf<Int>()

data class FindPos(val range:IntProgression,val count:Int)

object ContentAnalyser{
    val conNum = 8
    val mobileRegex = """1\d{10}""".toRegex(RegexOption.MULTILINE)
    val contiRegex = """\d{$conNum}""".toRegex(RegexOption.MULTILINE)
    val FIELD_SIGN = "secretStr"

    init {
        numberSet.addAll(to2Int('0')..to2Int('9'))//
        numberSet.addAll(to2Int('o','O'))//
        numberSet.addAll(to2Int('零','一','二','三','四','五','六','七','八','九','十'))//
        numberSet.addAll(to2Int("壹贰叁肆伍陆柒捌玖玖"))//

        numberSet.addAll(9451..9471) // ⓫⓴⓵⓾⓿
        numberSet.addAll(9352..9371) // ⒈ ⒛
        numberSet.addAll(9332..9351) // ⑴⒇
        numberSet.addAll(9322..9331) //  ⑪⑳
        numberSet.addAll(9312..9331) // ① ⑩
        numberSet.addAll(10122..10131) // ➊ ➓
        numberSet.addAll(10112..10121) // ➀➉
        numberSet.addAll(10102..10111) // ❶ ❿
    }
    fun isNum(char:Char):Boolean {
        return numberSet.contains(char.toInt())
    }
    fun to2Int(vararg chars:Char):Iterable<Int> {
        val ret =  mutableListOf<Int>()
        chars.forEach {
            ret.add(to2Int(it))
        }
        return ret
    }
    fun to2Int(char:Char):Int {
        return char.toInt()
    }
    fun to2Int(char:String): Iterable<Int> {
        return to2Int(*char.toCharArray())
    }

    fun containsMobile(str:String):Boolean {
        return str.contains(mobileRegex)
    }

    fun getNumberPos(str:String):List<Int> {
        val ret = mutableListOf<Int>()
        str.toCharArray().forEachIndexed({ index: Int, c: Char ->
            if (isNum(c)) {
                ret.add(index)
            }
        })
        return ret
    }

    fun isNumberPosInRange(list:List<Int>,contiN:Int,dist:Int):Boolean {
        var count = 0

        if (list.size > contiN) {
            var i:Int?=null
            list.forEach {
                if (i != null) {
                    if (it - i!! <= dist) {
                        count ++

                        if(count >= contiN)
                            return true
                    } else {
                        count = 0
                    }
                }

                i = it
            }
        }

        return count >= contiN
    }


    fun getNumberRange(list:List<Int>,contiN:Int,dist:Int):List<FindPos> {
        var count = 1
        var rt = mutableListOf<FindPos>()

        if (list.size >= contiN) {
            var prev:Int?=null
            var prevInitial:Int?=null
            list.forEachIndexed{ idx,it ->
                if (prevInitial == null) {
                    prevInitial = it
                }

                if (prev != null) {
                    if (it - prev!! <= dist) {
                        count ++
                    } else {
                        if(count >= contiN) {
                            rt.add(FindPos(prevInitial!!..list[idx-1],count))
                        }

                        count = 1
                        prevInitial = it
                    }
                }

                prev = it
            }

            if(count >= contiN) {
                rt.add(FindPos(prevInitial!!..list[list.lastIndex],count))
            }
        }

        return rt
    }

    fun getDiffs(str:String):Set<Int> {
        val ret = mutableSetOf<Int>()
        str.toCharArray().forEach {
            ret.add(it.toInt())
        }
        return ret
    }

    fun analyse(str:String):AnaRs {
//        if(str.contains(contiRegex)){
//            return AnaRs.CONTINUOUS_NUMBER
//        }

//        有误杀可能
//        if(isNumberPosInRange(getNumberPos(str),conNum,2)){

        val numberRange = getNumberRange(getNumberPos(str), conNum, 2).filter {
            it.count in 8..12
        }

        val mustContainedChars = "日月年号:-元.分秒班年级度，。#".toCharArray().toSet()
        if(numberRange.isNotEmpty()){
            //        有误杀可能  09-10 09:22  2018-09-29 18:59:17 9月10日

//            长文不删
            if (str.length> 200) {
                return AnaRs.OK
            }


//            必须包含关键字 否则删除
            numberRange.forEach {
                val strOne = str.substring(it.range.first,it.range.last+1)
//                println(strOne)

                //危险模式
                if (mustContainedChars.intersect(strOne.toCharArray().toSet()).isEmpty()) {

                    //55555555
                    //666666666
                    if (getDiffs(strOne).size>3) {
                        //            关键字判断
                        return AnaRs.UNICODE_NUMBER
                    }

                }
            }
//
            return AnaRs.OK
        }

//        println(getNumberPos(str))
        return AnaRs.OK
    }

    fun signUrl(url: String): String? {
        var url = url
        url = url.replace(" ".toRegex(), "%20")
        try {

            val uri = URI(url)
            val query = uri.query

//            val split = split(query)
//
//            val sortedMap = getSortedMap(split)
//
//            val dictionaryOrder = SortUtils.getTreeMapValues(sortedMap)
//
//
//            val md5Str = MD5.getMD5Str(dictionaryOrder)
//
//            val signP = WrapperDESUtils.encrypt(md5Str)

            return signParam(query)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun signParam(param: String): String? {
        try {
            val split = split(param)

            val sortedMap = getSortedMap(split)

            val dictionaryOrder = SortUtils.getTreeMapValues(sortedMap)

            val md5Str = MD5.getMD5Str(dictionaryOrder).toLowerCase()

            val signP = WrapperDESUtils.encrypt(md5Str)

            return "&"+FIELD_SIGN + "=" + signP
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun getSortedMap(parameterMap: Map<*, *>): SortedMap<String, String> {
        val sortMap = TreeMap<String, String>()
        for (key in parameterMap.keys) {
            //加密串 不参与签名
            if (FIELD_SIGN == key) {
                continue
            }

            val keyName = key.toString()
            val valObj = parameterMap[keyName]
            if (valObj != null && StringUtils.isNotEmpty(valObj.toString())) {
                sortMap.put(keyName, valObj.toString())
            }
        }
        return sortMap
    }

    fun split(urlparam: String): Map<String, String> {
        val map = HashMap<String, String>()
        val param = urlparam.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (keyvalue in param) {
            val pair = keyvalue.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (pair.size == 2) {
                map.put(pair[0], pair[1])
            }
        }
        return map
    }
}

enum class AnaRs(val s: String) {
    OK("ok")
    ,CONTINUOUS_NUMBER("包含连续数字")
    ,SENSITIVE_WORD("敏感词")
    ,UNICODE_NUMBER("unicode检测到连续数字")
}


fun main(args: Array<String>) {
    println(ContentAnalyser.containsMobile(""))
    println(ContentAnalyser.containsMobile("为什么我看完了心里健康教育视频不是100%而是98.8%。"))
    println(ContentAnalyser.containsMobile("分享一个知到答案免费共享交流群给大家。群号：一八八二五一零六一（群号是大写的，小写的平台会检测违规。）"))
    println(ContentAnalyser.containsMobile("%\uD83D\uDE01\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDE13(｡･∀･)ﾉﾞヾ让你知道在人任性(･ω･。)77-"))
    println(ContentAnalyser.containsMobile("三年来专为学生网课服务 高效质量好，各种课玳做，详情=【扣⑧⑦零④⑨零③⑤】可送全套答案"))
    println(ContentAnalyser.containsMobile("321008552"))
    println(ContentAnalyser.containsMobile("不想看的，了解一下，1373313870，"))
    println(ContentAnalyser.containsMobile("不想看的，了解一下，1373313870"))
    println(ContentAnalyser.containsMobile("13733138701"))
    println(ContentAnalyser.containsMobile("137331\r\n" +
            "38701"))
    println(ContentAnalyser.containsMobile(" 13733138701 "))

    println(ContentAnalyser.analyse(" 13733138701 "))
    println(ContentAnalyser.analyse("不想看的，了解一下，1373313870 "))
}