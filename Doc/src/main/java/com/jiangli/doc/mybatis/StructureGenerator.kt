package com.jiangli.doc.mybatis

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import java.io.File

data class Column(val cnName: String) {
    lateinit var enName: String
    lateinit var type: String
    lateinit var remark: String
}


fun geneSql(dbtable:String): String {
    val sql = """CREATE TABLE `$dbtable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IS_DELETED` tinyint(4) NOT NULL DEFAULT '0',
  `CREATE_TIME` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `CREATE_PERSON` int(11) DEFAULT NULL,
  `DELETE_PERSON` int(11) DEFAULT NULL,
  `SORT` smallint(6) NOT NULL DEFAULT '1' COMMENT '序号 从1开始',
  `TITLE` varchar(500) DEFAULT NULL COMMENT '标题',
  `LAYER` tinyint(4) NOT NULL COMMENT '层次类型 1章 2节 3小节',
  `IS_PUBLISHED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否发布 0未发布 1已发布',
  `COURSE_ID` int(11) DEFAULT '-1',
  `VIDEO_ID` int(11) DEFAULT '-1',
  `AUDIO_ID` int(11) DEFAULT '-1',
  `PARENT_ID` int(11) DEFAULT '-1',
  `IS_TAIL_NODE` int(11) NOT NULL DEFAULT '0' COMMENT '是否末结点 0否 1是',
  PRIMARY KEY (`ID`),
  KEY `COURSE_ID` (`COURSE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8 COMMENT='章节小节表';"""

    return sql
}

/**
 *
 *
 * @author Jiangli
 * @date 2018/3/1 10:23
 */
fun main(args: Array<String>) {
    val classPathFile = PathUtil.getSRCFileRelative(Column::class.java, "exceldb.txt")
    println(classPathFile)

    var dbtable: String?=null
    var line = 0
    val list = mutableListOf<Column>()
    FileUtil.processVisit(File(classPathFile)) {
        line++

        if (line == 1) {
            dbtable = it
        } else if (line == 3) {
//            dbtable = it

        }

        println(it)
        null
    }

    println(geneSql(dbtable!!))
}