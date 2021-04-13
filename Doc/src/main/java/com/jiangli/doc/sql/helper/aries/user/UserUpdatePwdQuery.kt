package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.EncryptHelper
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * 教学企业数量 分布数据
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val encryptPassword = EncryptHelper.encryptPassword("123456")


    var str = """
13901386266
13911992600
18600011169
13901034503
13901072469
13901144787
13911990982
13901306404
13331071777
13920318655
13811295415
13601399136
13701285108
13501057228
13910733969
16620220800
13701332086
13810933307
13972577187
16620220868
13963982785
15210302689
13603024427
18500055668
13911992512
18601359958
18910170271
13717869229
13910577885
18618156456
18621812980
18690189660
18121588777
13910820937
13911109610
13922951810
13929260516
18101130379
15000222016
15011598065
13430980167
13309536666
13957101199
13031175733
13326789100
15601089899
18600163860
16620220838
13810336836
15141327771
13810606179
13651340195
17743534261
18001288975
13601254590
13215915866
13661233659
13681194461
18001649555
13661749570
13581802259
13501214862
13802163546
15600385544
15810336302
18810642186
13818194077
15011503701
13215925866
13911256815
13810385361
15201030013
13911373167
18600052249
13693188990
18615519799
18618458154
18521517594

    """.trimIndent()

    str.split("\n").forEach {
        var line = it.trim()
        if (line.isBlank()) {
            return@forEach
        }


        val map = jdbc.queryForObject("""
SELECT * FROM db_aries_user.TBL_USER WHERE MOBILE='$line' AND IS_DELETED = 0 AND AREA_CODE='+86';   
 """.trimIndent(), ColumnMapRowMapper())

                val userId = map["ID"]
                val PWD = map["PASSWORD"]

        if (PWD == null) {
            println("""
            UPDATE db_aries_user.TBL_USER set PASSWORD ='$encryptPassword' WHERE ID='$userId' ;
        """.trimIndent())


        } else {
//            println("#have pwd $line")
        }


        val DOMAIN = "api.g2s.cn"
        val REDIS_DOMAIN = "api-pay.g2s.cn/aries-pay-app-server"

        val delSaasCache = "del aries:ser:USER_V2:d:$userId"

        var aa = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",delSaasCache))
        println("#$delSaasCache:${aa}")
//        println("$line:")

    }

}