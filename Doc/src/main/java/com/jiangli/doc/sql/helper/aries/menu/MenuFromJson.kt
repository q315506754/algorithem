package com.jiangli.doc.sql.helper.aries.menu

/**
 *
 *
 * @author Jiangli
 * @date 2020/12/25 10:31
 */
fun main(args: Array<String>) {

//    #补2级菜单
    val sqlLv2 = """
title:  我的团队   code: myTeam
title:  我的业绩   code: myAchievement
title:  我的机会   code: myChance
title:  我的客户   code: myCustomer
title:  客户档案   code: customerProfile
title:  客户公海   code: customerSea
title:  样板客户   code: modelCustomer
title:  产品管理   code: productManage
    """.trimIndent()

    sqlLv2.split("\n").forEach {
        var line = it
        val split = line.split("   ")
        if (split.size < 1) {
            return@forEach
        }
        val cn = split[0].split(": ")[1].trim()
        val code = split[1].split(": ")[1].trim()
//        println("$cn $code ")
        println(""",MENU_P27_CM_${com.jiangli.common.utils.StringHelper.camelToUnderscore(code, true)}(Perm_1.MENU_P27_CUS_MANAGE, "$cn", "$code")""")
    }

}