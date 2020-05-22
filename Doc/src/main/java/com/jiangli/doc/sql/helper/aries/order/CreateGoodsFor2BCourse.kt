package com.jiangli.doc.sql.helper.aries.order

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate

/**
 *
 *
 * @author Jiangli
 * @date 2020/5/11 17:25
 */
fun main(args: Array<String>) {
    //    val env = Env.YANFA
    val env = Env.YUFA
//    val env = Env.WAIWANG

    val jdbc = Ariesutil.getJDBC(env)

    val itemType = 8

    //课id - 班id
    val mp = mutableMapOf(
            "600270" to "1813"
    )


    mp.forEach {
        val courseId = it.key.trim()
        val classId = it.value.trim()

        //  查找班级列表
        val classlist = jdbc.query("""
SELECT c.CLASS_ID,cc.CLASS_CONTENT_ID,CLASS_NAME,c.START_TIME,ADDDATE( c.START_TIME, c.STUDY_VALIDITY_DAY ) as END_TIME,com.ID,com.NAME as COMPANY_NAME FROM
`db_aries_run`.`TBL_CLASS` c
LEFT JOIN  `db_aries_company`.`TBL_COMPANY` com on c.COMPANY_ID = com.ID
LEFT JOIN  `db_aries_run`.`TBL_CLASS_CONTENT` cc on c.CLASS_ID = cc.CLASS_ID
WHERE
           c.`COURSE_ID` = '$courseId'
           or (cc.RELATION_TYPE=0 and cc.RELATION_ID='$courseId')
  AND c.`IS_DELETE` = 0;
        """.trimIndent(), ColumnMapRowMapper())

        val classIds = classlist.map { it["CLASS_ID"].toString() }.toList()
        if (!classIds.contains(classId)) {
            System.err.println("错误的 classId:$classId for $courseId, list:${classlist.size}")
            classlist.forEach {
                println(it)
            }
            return
        }


        val list = jdbc.query("""
SELECT * FROM `db_aries_pay_goods`.`goods` where   `type` = ${itemType} and    `b_item_id` = ${courseId} and is_delete = 0;
        """.trimIndent(), ColumnMapRowMapper())

//        println(list)
        println("#courseId $courseId , classId $classId")

        val androidGoods = list.filter { it["platform"] == 1 }.firstOrNull()
        if (androidGoods!= null) {
            println("#安卓商品已存在")
        } else {
            println(createGoodsSql(jdbc,1,itemType,courseId,classId))
        }

        val iosGoods = list.filter { it["platform"] == 2 }.firstOrNull()
        if (iosGoods != null) {
            println("#ios商品已存在")
        } else {
            println(createGoodsSql(jdbc,2,itemType,courseId,classId))
        }
    }
}

fun createGoodsSql(jdbc: JdbcTemplate, platform:Int, itemType:Int, courseId:String, classId:String): String {

    val list = jdbc.query("""
SELECT * FROM `db_aries_course`.`TBL_COURSE` where   `COURSE_ID` = ${courseId} and   IS_DELETED = 0;
        """.trimIndent(), ColumnMapRowMapper())

    val course = list.first()


    val order_subject = "课程-" + course["COURSE_NAME"]
    val order_body = "课程-" + course["COURSE_NAME"]
    val cover_img =  course["VERTICAL_COURSE_IMAGE"]

    val goodsSql = """
INSERT INTO `db_aries_pay_goods`.`goods`( `platform`, `b_item_id`, `type`, `amount`, `discount_price`, `order_subject`, `order_body`, `cover_img`, `service_url`, `return_url`, `describe`, `on_shelf`, `is_delete`, `create_time`, `update_time`) VALUES ( $platform, $courseId, $itemType, 1, NULL, '$order_subject', '$order_body', '$cover_img', '', NULL, '', 1, 0, '2020-02-22 22:22:22', NULL);
    """.trimIndent()

    val goodsDataSql = """
INSERT INTO `db_aries_pay_goods`.`GOODS_DATA`(`KEY`, `VALUE`, `GOODS_ID`, `CREATE_PERSON`) select  'classId', '$classId', last_insert_id(), NULL;
    """.trimIndent()

    return """
$goodsSql

$goodsDataSql        
    """
}