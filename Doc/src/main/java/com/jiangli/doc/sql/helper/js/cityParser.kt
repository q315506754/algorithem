package com.jiangli.doc.sql.helper.js

import jdk.nashorn.api.scripting.ScriptObjectMirror
import javax.script.ScriptEngineManager

/**
 *
 *
 * @author Jiangli
 * @date 2019/12/4 11:32
 */
fun main(args: Array<String>) {
    val str = """

var geoCoordMap = {
         '新疆': [86.61 , 40.79],
         '西藏':[89.13 , 30.66],
         '黑龙江':[128.34 , 47.05],
         '吉林':[126.32 , 43.38],
         '辽宁':[123.42 , 41.29],
         '内蒙古':[112.17 , 42.81],
         '北京':[116.40 , 40.40 ],
         '宁夏':[106.27 , 36.76],
         '山西':[111.95,37.65],
         '河北':[115.21 , 38.44],
         '天津':[117.04 , 39.52],
         '青海':[97.07 , 35.62],
         '甘肃':[103.82 , 36.05],
         '山东':[118.01 , 36.37],
         '陕西':[108.94 , 34.46],
         '河南':[113.46 , 34.25],
         '安徽':[117.28 , 31.86],
         '江苏':[120.26 , 32.54],
         '上海':[121.46 , 31.28],
         '四川':[103.36 , 30.65],
         '湖北':[112.29 , 30.98],
         '浙江':[120.15 , 29.28],
         '重庆':[107.51 , 29.63],
         '湖南':[112.08 , 27.79],
         '江西':[115.89 , 27.97],
         '贵州':[106.91 , 26.67],
         '福建':[118.31 , 26.07],
         '云南':[101.71 , 24.84],
         '台湾':[121.01 , 23.54],
         '广西':[108.67 , 23.68],
         '广东':[113.98 , 22.82],
         '海南':[110.03 , 19.33],
         '澳门':[113.54 , 22.19],
         '香港':[114.17 , 22.32],
      };

    """.trimIndent()

    val engine = ScriptEngineManager().getEngineByName("nashorn")
    engine.eval(str)
//    val invocable = engine as Invocable
    val prop = engine.get("geoCoordMap")
    println(prop)

    val scriptObjectMirror = prop as ScriptObjectMirror
    println(prop.javaClass)
    println(scriptObjectMirror)

    for (mutableEntry in scriptObjectMirror) {
//        println(mutableEntry)

        val key = mutableEntry.key
        val value = mutableEntry.value as ScriptObjectMirror
//        println("$key -> ${value.javaClass}")
        println("$key -> ${value.values}")
    }
}