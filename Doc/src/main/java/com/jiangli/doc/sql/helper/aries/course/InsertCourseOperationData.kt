package com.jiangli.doc.sql.helper.aries.course

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


    val inputpath = PathUtil.desktop("免费.xlsx")

    val list = mutableListOf<String>()

    var itemId = 100
    var classId = 670
    ExcelUtil.processRow(inputpath, 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
//            val tagName = ExcelUtil.getCellValueByTitle(sheet, row, "板块")
            val fitGroup = ExcelUtil.getCellValueByTitle(sheet, row, "板块")
            val courseName = ExcelUtil.getCellValueByTitle(sheet, row, "课程名称")
            val courseId = ExcelUtil.getCellValueByTitle(sheet, row, "ID")
            val img = ExcelUtil.getCellValueByTitle(sheet, row, "图片链接")

            println("##$fitGroup $courseName $courseId $img ")
            val time = "2020-02-02 02:02:02"
            println("INSERT INTO `db_aries_course`.`TBL_ITEM_COURSE`(`ITEM_ID`, `COURSE_ID`, `IOS_GOOD_ID`, `Android_GOOD_ID`, `CLASS_ID`, `APPLY_URL`, `FITGROUP`, `IS_OPEN`, `ENTERPRISE_PURCHASE_STATUS`, `BACKGROUND_IMG`, `CREATE_TIME`, `UPDATE_TIME`, `IS_UPPER`, `IS_DELETE`, `REMARK`, `COURSE_TYPE`, `IS_FREE`) VALUES ($itemId, $courseId, NULL, NULL, $classId, 'https://ac.g2s.cn/morePaeg/H5/effa.html#/effa/83/294/0', $fitGroup, 0, '', '', '$time', '$time', 1, 0, NULL, 0, 1);")
            println("INSERT INTO `db_aries_course`.`TBL_COURSE_LABLE`(`LABEL_ID`, `ITEM_ID`, `CREATE_TIME`, `UPDATE_TIME`, `IS_DELETE`, `REMARK`, `RECOMMEND`) VALUES (62, $itemId, '$time', '$time', 0, NULL, 0);")
            println("INSERT INTO `db_aries_course`.`TBL_COURSE_CONTENT`( `ITEM_ID`, `COURSE_CONTENT_TYPE`, `SORT`, `CREATE_TIME`, `UPDATE_TIME`, `IS_DELETE`, `REMARK`, `COURSE_CONTEN`) VALUES ( $itemId, 0, 1, '$time', '$time', 0, '图片', '$img');")

            println("INSERT INTO `db_aries_run`.`tbl_class`(`CLASS_ID`,`CLASS_NAME`, `COMPANY_ID`, `RECRUIT_ID`, `COURSE_ID`, `CREATOR_ID`, `DELETER_ID`, `IS_DELETE`, `CREATE_TIME`, `UPDATE_TIME`, `START_TIME`, `STUDY_VALIDITY_DAY`, `TYPE`, `CREATE_COMPANY_ID`, `LIMIT_STU_NUM`, `LEARNING_MODE`, `IS_PUBLISH`) VALUES ($classId, '$courseName', -1, -1, -1, 100007204, NULL, 0, '2020-06-04 23:58:34', NULL, '2020-06-04 00:00:00', 60, 2, 0, -1, 1, 1);")
            println("################")

            itemId++
            classId++
        }

//        select last_insert_id();
//        INSERT INTO `db_aries_run`.`COACH_CLASS`( `USER_ID`, `COURSE_ID`, `CLASS_ID`, `DELI_TRAN_ID`, `DELI_PLAN_ID`, `REMARK`, `IS_DELETED`, `CREATE_TIME`, `UPDATE_TIME`, `CREATE_PERSON`, `DELETE_PERSON`) VALUES ( 100009609, 600143, 35, NULL, NULL, NULL, 0, '2019-05-07 17:36:11', '2019-05-08 10:45:24', -1, NULL);
//        INSERT INTO `db_aries_user`.`ZHISHI_COACH`(`OPERATION_NAME`, `OPERATION_AVATAR`, `BACK_IMG_SQUARE`, `BACK_IMG_RECTANGLE`, `QR_CODE`, `AUTH_TYPE`, `USER_ID`, `TITLE`, `STATUS`, `IS_DELETED`, `CREATE_TIME`, `UPDATE_TIME`, `CREATE_PERSON`, `DELETE_PERSON`) VALUES ('operationName', 'operationAvatar', 'backImgSquare', 'backImgRectangle', 'qrCode', 0, 100000062, '大神教练', 0, 0, '2020-04-01 11:02:56', '2020-05-28 19:14:27', NULL, NULL);


    }

    list.forEach {
        println(it)
    }

}