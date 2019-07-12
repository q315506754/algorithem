package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.math.BigDecimal


/**
 *
 *
 * @author Jiangli
 * @date 2019/1/19 15:17
 */
fun main(args: Array<String>) {
//    val env = Env.YANFA
    //    val env = Env.YUFA
        val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val fromEndDate = "2019-02-19 06:30:00"
    val toEndDate = "2019-06-17 13:30:00"

    val ret = mutableListOf<MutableMap<String,Any>>()


    val MP_SEX = linkedMapOf("0" to "男", "1" to "女")
    val MP_EDUCATION = linkedMapOf(
            "1" to "中专"
            ,"2" to "大专"
            ,"3" to "本科"
            ,"4" to "硕士"
            ,"5" to "MBA"
            ,"6" to "EMBA"
            ,"7" to "博士及以上"
    )
    val MP_WORK_EXPERIENCE = linkedMapOf(
            "1" to "1-3年"
            ,"2" to "3-5年"
            ,"3" to "5-10年"
            ,"4" to "10-20年"
            ,"5" to "20年以上"
    )
     val MP_JOB_CATEGORY = linkedMapOf ("1" to "基层", "2" to "中层", "3" to "高层")
    val MP_COMPANY_SCALE = linkedMapOf(
            "1" to "少于50人"
            ,"2" to "50-150人"
            ,"3" to "150-500人"
            ,"4" to "500-1000人"
            ,"5" to "1000-5000人"
            ,"6" to "5000-10000人"
            ,"7" to "10000人以上"
    )
    val MP_SECTORS_CATEGORY = linkedMapOf(
            "1" to "O2O"
            ,"2" to "旅游"
            ,"3" to "分类信息"
            ,"4" to "音乐/视频/阅读"
            ,"5" to "在线教育"
            ,"6" to "社交网络"
            ,"7" to "人力资源服务"
            ,"8" to "企业服务"
            ,"9" to "信息安全"
            ,"10" to "智能硬件"
            ,"11" to "移动互联网"
            ,"12" to "互联网"
            ,"13" to "计算机软件"
            ,"14" to "通信/网络设备"
            ,"15" to "数据服务"
            ,"16" to "电子商务"
            ,"17" to "广告/公关/会展"
            ,"18" to "金融"
            ,"19" to "物流/仓储"
            ,"20" to "贸易/进出口"
            ,"21" to "咨询"
            ,"22" to "工程施工"
            ,"23" to "汽车生产"
            ,"24" to "区块链"
            ,"25" to "人工智能"
            ,"26" to "游戏"
            ,"27" to "医疗健康"
            ,"28" to "生活服务"
            ,"29" to "其他行业"
    )

    val userInfos  = jdbc.query("""
SELECT  aua.ID as ID ,ua.SEX , TIMESTAMPDIFF(YEAR, ua.BIRTHDAY, CURDATE()) as USER_AGE  ,ua.EDUCATION,ua.JOB,ua.WORK_EXPERIENCE ,ua.JOB_CATEGORY,ua.COMPANY_SCALE,ua.SECTORS_CATEGORY from db_aries_questionnaire.DIM_USER_APPLY ua
LEFT JOIN db_aries_questionnaire.TBL_ANSWER_USER_APPLY aua ON ua.ID=aua.USER_ID
where ua.CREATE_TIME > '$fromEndDate' ANd ua.CREATE_TIME < '$toEndDate'
  AND ua.SECTORS_CATEGORY in (21,22)
  AND ua.IS_DELETED=0;
    """.trimIndent(),
            ColumnMapRowMapper())

    val scoreDimMeta = linkedMapOf(
            "美感度" to "4"
            ,"开放度" to "5"
            ,"内定力" to "3"
            ,"同理心" to "1"
            ,"思辨力" to "2"
    )
    val dimScoreData = mutableMapOf<String,Map<String,Any>>()

    scoreDimMeta.forEach { t, u ->
        println("query dim($u) scoreMap...")
        val scoreMap  = jdbc.query("""
        SELECT tr.DIMENSION_ID,tr.QUESTIONNAIRE_APPLY_ID,AVG(tr.WEIGHT_SCORE) as WEIGHT_SCORE
        FROM db_aries_questionnaire.DIM_TRAN_RESULT tr
        WHERE tr.CREATE_TIME > '2019-02-19 06:30:00' ANd tr.IS_DELETED = 0
        AND tr.DIMENSION_ID = $u
        GROUP BY tr.DIMENSION_ID,tr.QUESTIONNAIRE_APPLY_ID;
    """.trimIndent(),ColumnMapRowMapper())


//        scoreMap.reduce { acc:java.lang.Object, mutableMap:Map<String,Any> ->  acc }
        


        val flatMap = scoreMap.flatMap { listOf(it["DIMENSION_ID"]!!) }

        val userToScoreMap = mutableMapOf<String,Any>()
        scoreMap.forEach {
            userToScoreMap.put(it["QUESTIONNAIRE_APPLY_ID"].toString(),it["WEIGHT_SCORE"]!!)
        }


//        1 : { 1000:2333}
        dimScoreData.put(u,userToScoreMap)
//        scoreMap.stream().collect(Collectors.toMap({ t -> t["QUESTIONNAIRE_APPLY_ID"] },{ t -> t["WEIGHT_SCORE"] }))
    }

    var cout =0
    userInfos.forEach {
        cout++
        println("${cout}/${userInfos.size}")

        val auaId  = it["ID"]!!.toString()

        val one = mutableMapOf<String,Any>()
//        one.put("序号",cout)
        one.put("性别",MP_SEX[it["SEX"].toString()]!!)
        one.put("年龄",it["USER_AGE"]!!)
        one.put("学历",MP_EDUCATION[it["EDUCATION"].toString()]!!)
        one.put("职业",it["JOB"]!!)
        one.put("工作年限",MP_WORK_EXPERIENCE[it["WORK_EXPERIENCE"].toString()]!!)
        one.put("职位类别",MP_JOB_CATEGORY[it["JOB_CATEGORY"].toString()]!!)
        one.put("公司规模",MP_COMPANY_SCALE[it["COMPANY_SCALE"].toString()]!!)
        one.put("行业类别",MP_SECTORS_CATEGORY[it["SECTORS_CATEGORY"].toString()]!!)

//        五维度分别得分
        var sum =0.0
        scoreDimMeta.forEach { t, u ->
            val value = dimScoreData[u]!![auaId]?:"0.000000"
            one.put(t, value)

            sum+= value.toString().toDouble()
        }

        one.put("领导力总得分", BigDecimal(sum).setScale(6,BigDecimal.ROUND_UP).toString())

        if(sum > 0) {
            ret.add(one)
        }
    }

    writeMapToExcel(PathUtil.desktop("行业数据.xlsx"),ret){
        workbook: XSSFWorkbook, page1: XSSFSheet?, rowIdx: Int, curRow: XSSFRow?, cellIdx: Int, cell: XSSFCell, columnName: String, cellValue: String?, db: MutableMap<String, Any> ->
        try {
            //        尝试使用数字格式
            cell.setCellValue(java.lang.Double.parseDouble(cellValue))
//            println(cellValue)
        } catch (e:java.lang.Exception) {
            cell.setCellValue(cellValue)
        }
    }


}
